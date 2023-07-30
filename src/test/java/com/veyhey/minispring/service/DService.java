package com.veyhey.minispring.service;

public class DService {

    private CService cService;

    public DService() {
    }

    public DService(CService cService) {
        this.cService = cService;
    }

    public CService getCService() {
        return cService;
    }

    public void setCService(CService cService) {
        this.cService = cService;
    }
}
