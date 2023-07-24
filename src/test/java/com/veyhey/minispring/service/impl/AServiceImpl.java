package com.veyhey.minispring.service.impl;

import com.veyhey.minispring.service.AService;

public class AServiceImpl implements AService {
    @Override
    public void hello() {
        System.out.println("Hello AService");
    }
}
