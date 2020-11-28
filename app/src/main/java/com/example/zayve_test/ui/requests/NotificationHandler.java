package com.example.zayve_test.ui.requests;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.zayve_test.R;
import com.example.zayve_test.ZayveActivity;

import java.util.ArrayList;
import java.util.Random;

public class NotificationHandler {

    static ArrayList<String> arrayList = new ArrayList<>();

    private static final int REMINDER_NOTIFICATION_ID = 77;

    private static final int REMINDER_PENDING_INTENT_ID = 88;

    private static final String REMINDER_CHANNEL_ID = "reminder";

    public static void clearNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancelAll();;
    }

    public static void notificationCreator(Context context, String message) {

        if(!arrayList.contains(message))
        {
            arrayList.add(message);
            CharSequence channelName = "Reminder Notification";
            String channelDescription = "Creating Notification for Aims";
            int channelImportance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(REMINDER_CHANNEL_ID,
                    channelName,channelImportance);

            notificationChannel.setDescription(channelDescription);

            if(context != null){
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }


            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,REMINDER_CHANNEL_ID);

            notificationBuilder.setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("ZayvE Alert!")
                    .setContentText("")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(ContextCompat.getColor(context,R.color.authui_colorPrimaryDark))
                    .setLargeIcon(bitmap(context))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(getPendingIntent(context))
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            Random rand = new Random();
            int randomNumber = rand.nextInt(9999 - 1000) + 1000;

            notificationManagerCompat.notify(randomNumber,notificationBuilder.build());
        }
    }

    private static PendingIntent getPendingIntent(Context context)
    {
        Intent notificationPendingIntent = new Intent(context, ZayveActivity.class);

        return PendingIntent.getActivity(context,
                REMINDER_PENDING_INTENT_ID,
                notificationPendingIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap bitmap(Context context)
    {
        Resources resources = context.getResources();
        Bitmap icon = BitmapFactory.decodeResource(resources, R.drawable.appstore);
        return icon;
    }
}