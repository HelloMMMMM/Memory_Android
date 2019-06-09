package com.hellom.memory.eventbus;

public class DeletePhotoEvent implements BaseEvent {

    private String uri;

    public DeletePhotoEvent(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
