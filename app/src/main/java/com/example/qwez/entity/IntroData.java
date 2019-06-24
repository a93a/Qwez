package com.example.qwez.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class IntroData implements Parcelable {

    private final String category;
    private final String difficulty;
    private final Uri photoUrl;
    private final int answered;

    public IntroData(String category, String difficulty, Uri photoUrl, int answered) {
        this.category = category;
        this.difficulty = difficulty;
        this.photoUrl = photoUrl;
        this.answered = answered;
    }

    protected IntroData(Parcel in) {
        category = in.readString();
        difficulty = in.readString();
        photoUrl = in.readParcelable(Uri.class.getClassLoader());
        answered = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(difficulty);
        dest.writeParcelable(photoUrl, flags);
        dest.writeInt(answered);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IntroData> CREATOR = new Creator<IntroData>() {
        @Override
        public IntroData createFromParcel(Parcel in) {
            return new IntroData(in);
        }

        @Override
        public IntroData[] newArray(int size) {
            return new IntroData[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public int getAnswered() {
        return answered;
    }

    @Override
    public String toString() {
        return "IntroData{" +
                "category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", photoUrl=" + photoUrl +
                ", answered=" + answered +
                '}';
    }
}
