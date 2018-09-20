package com.mobitechs.pratik.trackuloved.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobitechs.pratik.trackuloved.MainActivity;
import com.mobitechs.pratik.trackuloved.R;
import com.mobitechs.pratik.trackuloved.sessionManager.SessionManager;

import java.util.HashMap;


public class MyFirebaseMesagingService extends FirebaseMessagingService {

    Bitmap bitmap;
    SessionManager sessionManager;
    String userId, userType;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage);
    }



    private void showNotification(RemoteMessage remoteMessage) {
        sessionManager = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);
        userType = typeOfUser.get(SessionManager.KEY_USERTYPE);

        String message = remoteMessage.getData().get("NOTIFICATION_TYPE");
         if (remoteMessage.getData().get("NOTIFICATION_TYPE").trim().equals("OPEN_ACTIVITY_ATTENDANCE_DETAILS")) {
            String notificationIDs = Integer.parseInt(remoteMessage.getData().get("NOTIFICATION_STUDENT_ID")) + "" + Integer.parseInt(remoteMessage.getData().get("NOTIFICATION_ID"));
            int notificationID = Integer.parseInt(notificationIDs);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Title", remoteMessage.getData().get("NOTIFICATION_TITLE"));
            intent.putExtra("Message", remoteMessage.getData().get("NOTIFICATION_MESSAGE"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this);
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_short);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher_short));

            notificationBuilder.setContentTitle(remoteMessage.getData().get("NOTIFICATION_TITLE"));
            notificationBuilder.setContentText(remoteMessage.getData().get("NOTIFICATION_DESCRIPTION"));
            notificationBuilder.setPriority(2);
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("NOTIFICATION_DESCRIPTION")));
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setLights(Color.GREEN, 1000, 1000);
            notificationBuilder.setContentIntent(pendingIntent);

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(notificationID, notificationBuilder.build());
        }
    }
}