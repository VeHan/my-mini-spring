package com.veyhey.minispring.event;

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}