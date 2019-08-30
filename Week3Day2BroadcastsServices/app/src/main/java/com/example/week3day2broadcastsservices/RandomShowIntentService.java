package com.example.week3day2broadcastsservices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RandomShowIntentService extends IntentService {
    private static final String ACTION_RANDOM_SHOWS = "com.example.week3day2broadcastsservices.action.RandomShows";
    public static final String EXTRA_SHOWS = "com.example.week3day2broadcastsservices.extra.Shows";

    public RandomShowIntentService() {
        super("RandomShowIntentService");
    }

    public static void startRandomShows(Context context){
        Intent intent = new Intent(context, RandomShowIntentService.class);
        intent.setAction(ACTION_RANDOM_SHOWS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            ArrayList<TelevisionShow> shows = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                shows.add(new TelevisionShow());
            }
            Intent returnIntent = new Intent("key");
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("shows", shows);
            returnIntent.putExtras(bundle);
            getBaseContext().sendBroadcast(returnIntent);
    }
}
