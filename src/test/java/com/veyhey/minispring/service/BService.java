package com.veyhey.minispring.service;

public class BService {
    private AService aService;

    public BService() {
    }

    public BService(AService aService) {
        this.aService = aService;
    }


    public AService getAService() {
        return aService;
    }

    public void setAService(AService aService) {
        this.aService = aService;
    }
}
