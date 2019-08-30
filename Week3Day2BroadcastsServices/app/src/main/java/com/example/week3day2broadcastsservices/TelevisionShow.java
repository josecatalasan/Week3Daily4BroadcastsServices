package com.example.week3day2broadcastsservices;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class TelevisionShow implements Parcelable {

    private String genre;
    private String duration;
    private String theme;
    private int setting;

    public TelevisionShow() {
        final String[] genres = {"Comedy", "Drama", "Action", "Mystery", "Reality", "Documentary", "Thriller"};
        final String[] durations = {"5 minutes", "25 minutes", "45 minutes", "60 minutes"};
        final String[] themes = {"SciFi", "Western", "Modern", "Fantasy" ,"Horror","CyberPunk", "Steampunk", "Cyberspace", "Post-Apocalyptic", "Historical", "Superhero", "Melodrama", "Mockumentary", "Smut"};
        final int[] settings = {R.drawable.city, R.drawable.desert, R.drawable.forest, R.drawable.islands, R.drawable.space};

        Random r = new Random();
        genre = genres[r.nextInt(genres.length)];
        duration = durations[r.nextInt(durations.length)];
        theme = themes[r.nextInt(themes.length)];
        setting = settings[r.nextInt(settings.length)];
    }

    protected TelevisionShow(Parcel in) {
        genre = in.readString();
        duration = in.readString();
        theme = in.readString();
        setting = in.readInt();
    }

    public static final Creator<TelevisionShow> CREATOR = new Creator<TelevisionShow>() {
        @Override
        public TelevisionShow createFromParcel(Parcel in) {
            return new TelevisionShow(in);
        }

        @Override
        public TelevisionShow[] newArray(int size) {
            return new TelevisionShow[size];
        }
    };

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        this.setting = setting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(genre);
        parcel.writeString(duration);
        parcel.writeString(theme);
        parcel.writeInt(setting);
    }
}
