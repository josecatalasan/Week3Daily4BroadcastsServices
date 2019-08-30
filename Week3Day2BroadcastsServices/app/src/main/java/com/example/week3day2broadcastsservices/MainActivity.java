package com.example.week3day2broadcastsservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rvShows)
    RecyclerView rvShows;
    MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myBroadcastReceiver = new MyBroadcastReceiver();
        RandomShowIntentService.startRandomShows(this);
        //start foreground
        //MusicForegroundService.startTheMusic(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("key");
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        filter.addAction("sound_alarm");
        registerReceiver(myBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch(action) {
                case "key":
                    Bundle bundle = intent.getExtras();
                    //populate RecyclerView
                    ArrayList<TelevisionShow> shows = bundle.getParcelableArrayList("shows");
                    RVShowsAdapter adapter = new RVShowsAdapter(shows);
                    rvShows.setAdapter(adapter);
                    rvShows.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                    break;
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    Toast.makeText(context, "AIRPLANE MODE CHANGED", Toast.LENGTH_LONG).show();
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    Toast.makeText(context, "POWER IS DISCONNECTED", Toast.LENGTH_LONG).show();
                    break;
                case Intent.ACTION_BATTERY_LOW:
                    Toast.makeText(context, "BATTERY IS LOW", Toast.LENGTH_LONG).show();
                    break;
                case Intent.ACTION_POWER_CONNECTED:
                    Toast.makeText(context, "POWER IS CONNECTED", Toast.LENGTH_LONG).show();
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    Toast.makeText(context, "HEADSET IN", Toast.LENGTH_LONG).show();
                    break;
                case "sound_alarm":
                    TelevisionShow show = intent.getExtras().getParcelable("show");
                    Toast.makeText(context, "ALARM SOUND OFF", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
