package com.example.week3day2broadcastsservices;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class MusicForegroundService extends Service {
    MediaPlayer player;

    public MusicForegroundService(){
    }

    public static void startTheMusic(Context context){
        Intent intent = new Intent(context, MusicForegroundService.class);
        intent.setAction("MUSIC");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @TargetApi(26)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        createNotificationChannel();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Notification notification =
                new Notification.Builder(this)
                        .setContentTitle("Music Player is on.")
                        .setContentText("Click this to turn off music.")
                        .setSmallIcon(R.drawable.ic_music_notification)
                        .setFullScreenIntent(pendingIntent,true)
                        .setWhen(System.currentTimeMillis())
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setContentIntent(pendingIntent)
                        .setTicker("WHAT IS THIS?")
                        .build();
        play();
        notificationManager.notify(1337, notification);
        startForeground(1337, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test";
            String description = "Totally a test";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("TEST", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Not bound service
        throw null;
    }

    public void play(){
        if(player == null){
            player = MediaPlayer.create(this, R.raw.under_pressure);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }

    public void stop(View view){
        stopPlayer();
    }

    private void stopPlayer(){
        if(player != null){
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }
}
