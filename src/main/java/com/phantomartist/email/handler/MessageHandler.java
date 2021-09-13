package com.phantomartist.email.handler;

import java.util.List;

import com.phantomartist.email.wrapper.Message;

public interface MessageHandler {

    List<Message> getUnread();

    void markAsRead(List<Message> messages);
}
