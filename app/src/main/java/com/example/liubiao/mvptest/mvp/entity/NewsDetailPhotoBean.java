package com.example.liubiao.mvptest.mvp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubiao on 2016/12/20.
 */
public class NewsDetailPhotoBean implements Parcelable{
    private String title;
    private List<Picture> pictures;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public static class Picture implements Parcelable {
        private String title;

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String imgSrc;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.imgSrc);
        }

        public Picture() {
        }

        protected Picture(Parcel in) {
            this.title = in.readString();
            this.imgSrc = in.readString();
        }

        public static final Creator<Picture> CREATOR = new Creator<Picture>() {
            @Override
            public Picture createFromParcel(Parcel source) {
                return new Picture(source);
            }

            @Override
            public Picture[] newArray(int size) {
                return new Picture[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeList(this.pictures);
    }

    public NewsDetailPhotoBean() {
    }

    protected NewsDetailPhotoBean(Parcel in) {
        this.title = in.readString();
        this.pictures = new ArrayList<>();
        in.readList(this.pictures, Picture.class.getClassLoader());
    }

    public static final Parcelable.Creator<NewsDetailPhotoBean> CREATOR = new Parcelable.Creator<NewsDetailPhotoBean>() {
        @Override
        public NewsDetailPhotoBean createFromParcel(Parcel source) {
            return new NewsDetailPhotoBean(source);
        }

        @Override
        public NewsDetailPhotoBean[] newArray(int size) {
            return new NewsDetailPhotoBean[size];
        }
    };
}
