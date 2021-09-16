package com.phantomartist.email.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.graph.requests.MessageCollectionPage;
import com.phantomartist.email.Logger;
import com.phantomartist.email.authentication.AccessProvider;
import com.phantomartist.email.handler.MessageHandler;
import com.phantomartist.email.wrapper.Message;
import com.phantomartist.email.wrapper.impl.MessageImpl;

public class MessageHandlerImpl implements MessageHandler {

    private AccessProvider accessProvider;

    public MessageHandlerImpl(final AccessProvider accessProvider) {
        this.accessProvider = accessProvider;
    }

    @Override
    public List<Message> getUnread() {

        final List<Message> unread = new ArrayList<>();

        final MessageCollectionPage page = 
            accessProvider
            .getServiceClient()
            .me()
            .messages()
            .buildRequest()
            .filter("isRead eq false")
            .get();
        List<com.microsoft.graph.models.Message> messages = 
            page.getCurrentPage();

        int pageCount = 1;
        while (messages != null && messages.size() > 0) {
            for (com.microsoft.graph.models.Message message : messages) {
                if (!message.isRead) {
                    unread.add(new MessageImpl(accessProvider, message));
                }
            }
            Logger.logInfo("Processed unread page " + pageCount);
            messages = page.getNextPage().buildRequest().get().getCurrentPage();
            pageCount++;
        }

        return unread;
    }

    @Override
    public void markAsRead(final List<Message> messages) {

        com.microsoft.graph.models.Message msg = 
                new com.microsoft.graph.models.Message();
        msg.isRead = true;

        messages.forEach(message -> {
            com.microsoft.graph.models.Message response = 
                accessProvider
                .getServiceClient()
                .me()
                .messages(message.getId())
                .buildRequest()
                .select("IsRead")
                .patch(msg);

            if (!response.isRead) {
                Logger.logError("Unable to successfully mark message " + 
                    message.getId() + " as 'read'");
            }
        });
    }

}
