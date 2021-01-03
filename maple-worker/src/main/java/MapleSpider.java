import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.thread.CountableThreadPool;
import us.codecraft.webmagic.utils.UrlUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lyifee
 * on 2021/1/1
 */
public class MapleSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapleSpider.class);

    protected List<Downloader> downloaderList;

    protected List<PageProcessor> processorList;

    protected int tempProcessorIdx;

    protected int tempDownloaderIdx;

    protected ReentrantLock getDownloaderLock = new ReentrantLock();

    protected ReentrantLock getProcessorLock = new ReentrantLock();

    private ReentrantLock newUrlLock = new ReentrantLock();

    private Condition newUrlCondition = newUrlLock.newCondition();

    private List<SpiderListener> spiderListeners;

    private final AtomicLong pageCount = new AtomicLong(0);

    private Date startTime;

    private int emptySleepTime = 30000;

    /**
     * create a spider with pageProcessor.
     * the construct method is private,
     * so user can only use this method to create a maple spider.
     *
     * @param processorList pageProcessor
     * @return new spider
     * @see PageProcessor
     */
    public static MapleSpider create(List<PageProcessor> processorList) {
        return new MapleSpider(processorList);
    }

    /**
     * create a spider with pageProcessor.
     *
     * @param processorList processorList
     */
    private MapleSpider(List<PageProcessor> processorList) {
        super(processorList.get(0));
        this.processorList = processorList;
        this.tempProcessorIdx = processorList.size();
    }

    public MapleSpider downloaderList(List<Downloader> downloaderList) {
        checkIfRunning();
        this.downloader = downloaderList.get(0);
        this.downloaderList = downloaderList;
        this.tempDownloaderIdx = downloaderList.size();
        return this;
    }

    protected void initComponent() {
        if (downloaderList == null || downloaderList.size() == 0) {
            downloader = new HttpClientDownloader();
            downloader.setThread(threadNum);
        }
        if (pipelines.isEmpty()) {
            pipelines.add(new ConsolePipeline());
        }
        if (threadPool == null || threadPool.isShutdown()) {
            if (executorService != null && !executorService.isShutdown()) {
                threadPool = new CountableThreadPool(threadNum, executorService);
            } else {
                threadPool = new CountableThreadPool(threadNum);
            }
        }
        if (startRequests != null) {
            for (Request request : startRequests) {
                addRequest(request);
            }
            startRequests.clear();
        }
        startTime = new Date();
    }

    @Override
    public void run() {
        checkRunningStat();
        initComponent();
        logger.info("Spider {} started!",getUUID());
        while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
            final Request request = scheduler.poll(this);
            if (request == null) {
                if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                    break;
                }
                // wait until new url added
                waitNewUrl();
            } else {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processRequest(request);
                            onSuccess(request);
                        } catch (Exception e) {
                            onError(request);
                            logger.error("process request " + request + " error", e);
                        } finally {
                            pageCount.incrementAndGet();
                            signalNewUrl();
                        }
                    }
                });
            }
        }
        stat.set(STAT_STOPPED);
        // release some resources
        if (destroyWhenExit) {
            close();
        }
        logger.info("Spider {} closed! {} pages downloaded.", getUUID(), pageCount.get());
    }

    private PageProcessor getProcessor() {
        if (processorList == null || processorList.size() == 0) {
            return pageProcessor;
        }
        getProcessorLock.lock();
        try {
            tempProcessorIdx--;
            if (tempProcessorIdx < 0) {
                tempProcessorIdx = processorList.size() - 1;
            }
            return processorList.get(tempProcessorIdx);

        } finally {
            getProcessorLock.unlock();
        }
    }

    private Downloader getDownloader() {
        if (downloaderList == null || downloaderList.size() == 0) {
            return downloader;
        }
        getDownloaderLock.lock();
        try {
            tempDownloaderIdx--;
            if (tempDownloaderIdx < 0) {
                tempDownloaderIdx = downloaderList.size() - 1;
            }
            return downloaderList.get(tempDownloaderIdx);

        } finally {
            getDownloaderLock.unlock();
        }
    }

    private void checkRunningStat() {
        while (true) {
            int statNow = stat.get();
            if (statNow == STAT_RUNNING) {
                throw new IllegalStateException("Spider is already running!");
            }
            if (stat.compareAndSet(statNow, STAT_RUNNING)) {
                break;
            }
        }
    }

    private void destroyEach(Object object) {
        if (object instanceof Closeable) {
            try {
                ((Closeable) object).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(Request request) {
        Downloader downloader = getDownloader();
        if (downloader == null) {
            LOGGER.error("downloader_of_maple_spider_is_null");
            return;
        }
        Page page = downloader.download(request, this);
        if (page.isDownloadSuccess()){
            onDownloadSuccess(request, page);
        } else {
            onDownloaderFail(request);
        }
    }

    private void onDownloadSuccess(Request request, Page page) {
        PageProcessor processor = getProcessor();
        if (processor == null) {
            LOGGER.error("page_processor_of_maple_spider_is_null");
            return;
        }
        if (site.getAcceptStatCode().contains(page.getStatusCode())){
            processor.process(page);
            extractAndAddRequests(page, spawnUrl);
            if (!page.getResultItems().isSkip()) {
                for (Pipeline pipeline : pipelines) {
                    pipeline.process(page.getResultItems(), this);
                }
            }
        } else {
            LOGGER.info("page status code error, page {} , code: {}", request.getUrl(), page.getStatusCode());
        }
        sleep(site.getSleepTime());
    }

    private void onDownloaderFail(Request request) {
        if (site.getCycleRetryTimes() == 0) {
            sleep(site.getSleepTime());
        } else {
            // for cycle retry
            doCycleRetry(request);
        }
    }

    private void doCycleRetry(Request request) {
        Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
        if (cycleTriedTimesObject == null) {
            addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, 1));
        } else {
            int cycleTriedTimes = (Integer) cycleTriedTimesObject;
            cycleTriedTimes++;
            if (cycleTriedTimes < site.getCycleRetryTimes()) {
                addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes));
            }
        }
        sleep(site.getRetrySleepTime());
    }

    private void addRequest(Request request) {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request, this);
    }

    private void waitNewUrl() {
        newUrlLock.lock();
        try {
            //double check
            if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
                return;
            }
            newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.warn("waitNewUrl - interrupted, error %s", e);
        } finally {
            newUrlLock.unlock();
        }
    }

    private void signalNewUrl() {
        try {
            newUrlLock.lock();
            newUrlCondition.signalAll();
        } finally {
            newUrlLock.unlock();
        }
    }

}
