package ru.iteco.accountbank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.iteco.accountbank.model.ExternalInfo;
import ru.iteco.accountbank.model.annotation.CheckRequest;

@Slf4j
@Component
public class ExternalInfoProcess implements Process {

    @Value("${id-not-process}")
    private Integer idNotProcess;

    @CheckRequest
    @Override
    public boolean run(ExternalInfo externalInfo) {
        if (idNotProcess.equals(externalInfo.getId())) {
            log.info("Process not need: {}", externalInfo);
            throw new RuntimeException("Id not process");
        }
        log.info("Process with: {}", externalInfo);
        return true;
    }
}
