package ru.iteco.accountbank.model;

import lombok.Data;

@Data
public class ExternalInfo {
    private Integer id;
    private String info;

    public ExternalInfo() {
    }

    public ExternalInfo(Integer id, String info) {
        this.id = id;
        this.info = info;
    }
}
