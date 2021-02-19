package org.webmaple.worker.test;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author lyifee
 * on 2021/1/29
 */
public class TestPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("pipeline_test");
    }
}
