package com.pesados.purplepoint.api.firebase;

import com.google.firebase.messaging.*;
import com.pesados.purplepoint.api.model.firebase.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// push notification without data payload

@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    public void sendMessage(String token, String username, Map<String, String> data)
            throws InterruptedException, ExecutionException {

        PushNotificationRequest request = new PushNotificationRequest( username+ "are on their way to help you", "");

        Message message = getPreconfiguredMessageWithoutData(request, token, data);
        String response = sendAndGetResponse(message);
        logger.info("Sent message to "  + response);
    }


    public void sendMulticastMessageWithoutData(List<String> tokens, Map<String, String> data)
            throws FirebaseMessagingException {
        tokens.add("f2EJYEQeYyYq-v2ubvL7x5:APA91bGdhjEwaHGO6IACJfsiS6wY9vJlvPvddUISv2C8e5Ts6xU88STGBhL7rBpuiPaZeB2D_AdFgmqLuFF2EmhsZErEwyKyF2pRh_SfYNn3I5BkMjXnk6z_rPJU0dV1fq6jWMvjCKB1");
        PushNotificationRequest request = new PushNotificationRequest("Your help is needed", "A person near to you needs your help");
        MulticastMessage multicastmessage = getPreconfiguredMulticatsMessageWithoutData(request, tokens, data);
        int response = sendMulticastAndGetResponse(multicastmessage);
        logger.info("Sent message without data. Total Messages: " + tokens.size()+ ", Sended OK: " + response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private int sendMulticastAndGetResponse(MulticastMessage multicastMessage) {

        try{
            return FirebaseMessaging.getInstance().sendMulticast(multicastMessage).getSuccessCount();

        } catch(FirebaseMessagingException e){
            int mes = 0;
            return mes;

        }
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request, String token, Map<String, String> data){
        return getPreconfiguredMessageBuilder(request,token).putAllData(data).build();
    }

    private MulticastMessage getPreconfiguredMulticatsMessageWithoutData(PushNotificationRequest request, List<String> tokens, Map<String, String> data) {
        return getPreconfiguredMulticastMessageBuilder(request, tokens).putAllData(data).build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request, String token) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage())).setToken(token);
    }

    private MulticastMessage.Builder getPreconfiguredMulticastMessageBuilder(PushNotificationRequest request, List<String> tokens) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return MulticastMessage.builder()
                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage())).addAllTokens(tokens);
    }


}