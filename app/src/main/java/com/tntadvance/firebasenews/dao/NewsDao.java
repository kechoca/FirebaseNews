package com.tntadvance.firebasenews.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thana on 6/27/2017 AD.
 */

public class NewsDao implements Parcelable {
    private String newsId;
    private String header;
    private String content;
    private String imagePath;
    private String date;

    public NewsDao() {
    }

    protected NewsDao(Parcel in) {
        newsId = in.readString();
        header = in.readString();
        content = in.readString();
        imagePath = in.readString();
        date = in.readString();
    }

    public static final Creator<NewsDao> CREATOR = new Creator<NewsDao>() {
        @Override
        public NewsDao createFromParcel(Parcel in) {
            return new NewsDao(in);
        }

        @Override
        public NewsDao[] newArray(int size) {
            return new NewsDao[size];
        }
    };

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imgPath) {
        this.imagePath = imgPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(newsId);
        parcel.writeString(header);
        parcel.writeString(content);
        parcel.writeString(imagePath);
        parcel.writeString(date);
    }
}
