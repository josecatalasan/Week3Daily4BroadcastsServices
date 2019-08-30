package com.example.week3day2broadcastsservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RVShowsAdapter extends RecyclerView.Adapter<RVShowsAdapter.ViewHolder> {
    private ArrayList<TelevisionShow> shows;

    public RVShowsAdapter(ArrayList<TelevisionShow> shows) {
        this.shows = shows;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item, parent, false);
        return new ViewHolder(inflatedItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TelevisionShow currentShow = shows.get(position);
        holder.tvGenre.setText(currentShow.getGenre());
        holder.tvDuration.setText(currentShow.getDuration());
        holder.tvTheme.setText(currentShow.getTheme());
        holder.ivSetting.setImageResource(currentShow.getSetting());
        holder.setItemTVshow(currentShow);
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tvGenre)
        TextView tvGenre;
        @BindView(R.id.tvDuration)
        TextView tvDuration;
        @BindView(R.id.tvTheme)
        TextView tvTheme;
        @BindView(R.id.ivSetting)
        ImageView ivSetting;
        TelevisionShow itemTVshow;

        private AlarmManager alarmManager;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            tvGenre = itemView.findViewById(R.id.tvGenre);
//            tvDuration = itemView.findViewById(R.id.tvDuration);
//            tvTheme = itemView.findViewById(R.id.tvTheme);
//            ivSetting = itemView.findViewById(R.id.ivSetting);
            itemView.setOnClickListener(this);
        }

        public void setItemTVshow(TelevisionShow itemTVshow) {
            this.itemTVshow = itemTVshow;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent("sound_alarm");
            TelevisionShow show = itemTVshow;
//            Bundle bundle = new Bundle();
//           bundle.putParcelable("show", show);
//           intent.putExtras(bundle);
            intent.putExtra("show", show);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext().getApplicationContext(), 1337, intent, 0);
            AlarmManager.AlarmClockInfo clockInfo = new AlarmManager.AlarmClockInfo(System.currentTimeMillis()+5000, pendingIntent);
            alarmManager.setAlarmClock(clockInfo, pendingIntent);
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis()+500, pendingIntent);
        }
    }
}
