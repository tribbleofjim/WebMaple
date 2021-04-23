package org.webmaple.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.webmaple.admin.container.WorkerContainer;
import org.webmaple.admin.mapper.SpiderMapper;
import org.webmaple.common.model.NodeDTO;
import org.webmaple.common.network.RequestSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lyifee
 * on 2021/1/25
 */
@Configuration
@Component
public class BeanConfig {
    @Autowired
    private WorkerContainer workerContainer;

    @Autowired
    private SpiderMapper spiderMapper;

    @Bean
    public RequestSender requestSender(){
        return new RequestSender();
    }

    @Scheduled(cron = "0 0/20 * * * ?")
    public void removeInvalidNodeSpiders() {
        List<NodeDTO> workers = workerContainer.getWorkerList();
        List<String> workerNames = workers.stream().map(NodeDTO::getName).collect(Collectors.toList());
        spiderMapper.removeInvalidNodeSpiders(workerNames);
    }

    @Value("${webmaple.jar}")
    private String jarPath;

    public String getJarPath() {
        return jarPath;
    }
}
