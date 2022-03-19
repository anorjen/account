package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;
import ru.iteco.accountbank.model.annotation.CacheResult;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ExternalServiceImpl implements ExternalService {

    private Map<Integer, ExternalInfo> map = new HashMap<>();

    @Override
    @CacheResult
    public ExternalInfo getExternalInfo(Integer id) {
        ExternalInfo externalInfo = map.get(id);
        if (externalInfo == null) {
            throw new RuntimeException("Not found");
        }
        return externalInfo;
    }

    @PostConstruct
    public void init() {
        map.put(1, new ExternalInfo(1, null));
        map.put(2, new ExternalInfo(2, "hasInfo"));
        map.put(3, new ExternalInfo(3, "info"));
        map.put(4, new ExternalInfo(4, "information"));
    }

    @PreDestroy
    public void destroy() {
        log.info("Map before: {}", map);
        map.clear();
        log.info("Map after: {}", map);
    }
}
