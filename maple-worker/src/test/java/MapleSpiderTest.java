import com.webmaple.worker.MapleSpider;
import com.webmaple.worker.WorkerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/1/3
 */
@SpringBootTest(classes = WorkerApplication.class)
public class MapleSpiderTest {
    @Test
    public void mapleSpiderTest() {
        List<PageProcessor> processorList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            processorList.add(new BaiduBaikePageProcessor());
        }
        List<Downloader> downloaderList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            downloaderList.add(new HttpClientDownloader());
        }
        MapleSpider.create(processorList)
                .downloaderList(downloaderList)
                .addUrl("http://baike.baidu.com/search/word?word=水力发电&pic=1&sug=1&enc=utf8")
                .addPipeline(new ConsolePipeline())
                .thread(1)
                .run();
    }
}
