import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author lyifee
 * on 2021/1/1
 */
public class MapleSpider extends Spider {
    protected ComponentPool<Downloader> downloaderPool;

    protected ComponentPool<PageProcessor> processorPool;


    /**
     * create a spider with pageProcessor.
     *
     * @param pageProcessor pageProcessor
     */
    public MapleSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }
}
