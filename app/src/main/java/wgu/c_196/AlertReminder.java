package wgu.c_196;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class AlertReminder extends BroadcastReceiver {
    public static final String LOG_INFO = "AlertReminder Event";
    static int notify_id;

    @Override
    public void onReceive(Context context, Intent intent) {
        notifyChannel(context);
        Intent receiveIntent = new Intent(context, MainActivity.class);
        PendingIntent thisPendingIntent = PendingIntent.getActivity(context, 0, receiveIntent,0);
        int notifyID = intent.getIntExtra("notifyId", 0);
        NotificationCompat.Builder thisBuilder = new NotificationCompat.Builder(context, "WGUChanId")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(thisPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("message")))
                .setAutoCancel(true);
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(context);
        notifyManager.notify(notifyID, thisBuilder.build());
    }

    private void notifyChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channel_name = "WGU Mobile";
            String channel_description = "WGU Term Tracker";
            int channel_importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("WGUChanId", channel_name, channel_importance);
            notificationChannel.setDescription(channel_description);
            NotificationManager notifyManger = context.getSystemService(NotificationManager.class);
            notifyManger.createNotificationChannel(notificationChannel);
            Log.d(LOG_INFO, "Notification Channel Set");
        }
    }

    public static void setAlert(Date start, String title, String name, Context appContext ){
        Intent sendIntent = new Intent(appContext, AlertReminder.class);
        sendIntent.putExtra("title", title);
        sendIntent.putExtra("message", name);
        sendIntent.putExtra("notifyId", notify_id);
        PendingIntent thisPendingIntent = PendingIntent.getBroadcast(appContext, notify_id, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, start.getTime(), thisPendingIntent);
        notify_id++;

    }

}

