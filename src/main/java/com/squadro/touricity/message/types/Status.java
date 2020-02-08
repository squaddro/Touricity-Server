package com.squadro.touricity.message.types;

import com.squadro.touricity.message.types.data.enumeration.StatusCode;

public class Status implements IMessage {

    private StatusCode status;

    public static Status build(StatusCode status) {
        return new Status(status);
    }

    public Status edit(String message) {
        status.setMessage(message);
        return this;
    }

    private Status(StatusCode status){
        this.status = status;
    }

    public int getCode() {
        return status.getStatus();
    }

    public String getMessage() {
        return status.getMessage();
    }
}
