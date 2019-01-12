package come.manager.direct.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
/*
 Intent notificationIntent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("http://developer.alexanderklimov.ru/android/"));
PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
        notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setContentTitle("Посетите мой сайт")
        .setContentText("http://developer.alexanderklimov.ru/android/")
        .setTicker("Внимание!")
		.setWhen(System.currentTimeMillis())
        .setContentIntent(pendingIntent)
        .setDefaults(Notification.DEFAULT_SOUND)
		.setAutoCancel(true)
        .setSmallIcon(R.mipmap.ic_launcher);*/

        String CHANNEL_ID = "few";
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Title")
                            .setContentText("Notification text");
            notificationManager.notify(NOTIFY_ID, builder.build());
//            NotificationChannel channel = notificationManager.getNotificationChannel(CHANNEL_ID);
//
//            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
//                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
//                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//                startActivity(intent);
//            }
        } else {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Resources res = this.getResources();

            // до версии Android 8.0 API 26
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            builder.setContentIntent(contentIntent)
                    // обязательные настройки
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                    .setContentTitle("Напоминание")
                    //.setContentText(res.getString(R.string.notifytext))
                    .setContentText("Пора покормить кота") // Текст уведомления
                    // необязательные настройки
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_background)) // большая
                    // картинка
                    //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                    .setTicker("Последнее китайское предупреждение!")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true); // автоматически закрыть уведомление после нажатия


            // Альтернативный вариант
            // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
    }
}
