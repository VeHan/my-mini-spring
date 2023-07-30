package com.veyhey.minispring.service;

public class CService {
    private DService dService;

    public CService() {
    }

    public CService(DService dService) {
        this.dService = dService;
    }

    public DService getDService() {
        return dService;
    }

    public void setDService(DService dService) {
        this.dService = dService;
    }
}
