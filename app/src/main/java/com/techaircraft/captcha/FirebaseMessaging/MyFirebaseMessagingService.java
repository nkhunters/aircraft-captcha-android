package com.techaircraft.captcha.FirebaseMessaging;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = null, body = null;

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily
        if(remoteMessage.getData().size() > 0){

            title = remoteMessage.getData().get("str_title");
            body = remoteMessage.getData().get("str_body");
        }

        //getting the title and the body

        String click_action = remoteMessage.getNotification().getClickAction();
        MyNotificationManager.getInstance(this).displayNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), click_action);

        //then here we can use the title and body to build a notification
    }
}
