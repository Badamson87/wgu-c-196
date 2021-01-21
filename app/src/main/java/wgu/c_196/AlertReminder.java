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
        NotificationCompat.Builder thisBuilder = new NotificationCompat.Builder(context, "dgwChanId")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("message"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(thisPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("message")))
                .setAutoCancel(true);
        notify_id = intent.getIntExtra("notifyId", 747);
        NotificationManagerCompat notifyManager = NotificationManagerCompat.from(context);
        notifyManager.notify(notify_id, thisBuilder.build());
        System.out.println("did you get here?");

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void notifyChannel(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence dgw_channel_name = "DGW Tracker NAME";
            String dgw_channel_description = "DGW Tracker DESC";
            int dgw_channel_importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("dgwChanId", dgw_channel_name, dgw_channel_importance);
            notificationChannel.setDescription(dgw_channel_description);
            NotificationManager notifyManger = context.getSystemService(NotificationManager.class);
            notifyManger.createNotificationChannel(notificationChannel);
            Log.d(LOG_INFO, "Notification Channel Set");
        }
    }

    public static void setAlert(Date start, Date end, String title, String name, Context appContext ){
        Intent sendIntent = new Intent(appContext, AlertReminder.class);
        sendIntent.putExtra("title", title);
        sendIntent.putExtra("message", name);
        PendingIntent thisPendingIntent = PendingIntent.getBroadcast(appContext, notify_id, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, start.getTime(), thisPendingIntent);
        notify_id++;
//        alarmManager.set(AlarmManager.RTC_WAKEUP, end.getTime(), thisPendingIntent);
//        notify_id++;
    }

}

