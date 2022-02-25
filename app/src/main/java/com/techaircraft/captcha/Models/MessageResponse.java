package com.techaircraft.captcha.Models;

import java.util.ArrayList;

public class MessageResponse {

    private boolean error;
    private ArrayList<Message> messages;

    public MessageResponse(boolean error, ArrayList<Message> messages) {
        this.error = error;
        this.messages = messages;
    }

    public boolean isError() {
        return error;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
