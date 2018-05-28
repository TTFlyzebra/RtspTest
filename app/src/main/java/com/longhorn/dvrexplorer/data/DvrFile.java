package com.longhorn.dvrexplorer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.longhorn.dvrexplorer.utils.DateTools;

import java.io.Serializable;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by FlyZebra on 2018/5/21.
 * Descrip:
 */

public class DvrFile implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeByte(path);
        dest.writeByte(type);
        dest.writeByte(suffix);
        dest.writeByte(reserved);
        dest.writeInt(size);
        dest.writeInt(date);
        dest.writeInt(offset);
        dest.writeByte((byte) (isShowCheck ? 1 : 0));
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    /**
     * 文件索引
     */
    public int index;
    /**
     * 0-/NOR; 1-/EVT; 2-/PHO;
     */
    public byte path;
    /**
     * 0-"NOR_"; 1-"EVT_"; 2-"PHO_"; 3-"_D1_";
     */
    public byte type;
    /**
     * 0-".mp4"; 1-".jpg"
     */
    public byte suffix;

    public byte reserved;
    /**
     * 文件大小
     */
    public int size;
    /**
     * File created time from 1970/1/1-0:0:0 ,unit seconds
     */
    public int date;
    /**
     * Bytes offset of .mp4 file
     */
    public int offset;

    public boolean isShowCheck = false;

    public boolean isSelect = false;

    public DvrFile() {
    }

    protected DvrFile(Parcel in) {
        index = in.readInt();
        path = in.readByte();
        type = in.readByte();
        suffix = in.readByte();
        reserved = in.readByte();
        size = in.readInt();
        date = in.readInt();
        offset = in.readInt();
        isShowCheck = in.readByte() != 0;
        isSelect = in.readByte() != 0;
    }

    public static final Creator<DvrFile> CREATOR = new Creator<DvrFile>() {
        @Override
        public DvrFile createFromParcel(Parcel in) {
            return new DvrFile(in);
        }

        @Override
        public DvrFile[] newArray(int size) {
            return new DvrFile[size];
        }
    };

    public String getUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path == 0 ? Global.PATH_NOR : (path == 1 ? Global.PATH_EVT : Global.PATH_PHO));
        stringBuilder.append(DateTools.date2String(date * 1000L, DateTools.DATE_FORMAT_FILENAME));
        stringBuilder.append(suffix == 0 ? "A.MP4" : ".JPG");
//        stringBuilder.append(suffix==0?".MP4":".JPG");
        return stringBuilder.toString();
    }

    public String getPlayUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path == 0 ? Global.PATH_NOR : (path == 1 ? Global.PATH_EVT : Global.PATH_PHO));
        stringBuilder.append(DateTools.date2String(date * 1000L, DateTools.DATE_FORMAT_FILENAME));
        stringBuilder.append(suffix == 0 ? "B.MP4" : ".JPG");
//        stringBuilder.append(suffix==0?".MP4":".JPG");
        return stringBuilder.toString();
    }


    public String getTime() {
        return DateTools.date2String(date * 1000L);
    }


    @Override
    public String toString() {
        return "DvrFile{" +
                "index=" + index +
                ", path=" + path +
                ", type=" + type +
                ", suffix=" + suffix +
                ", reserved=" + reserved +
                ", size=" + size +
                ", date=" + date +
                ", offset=" + offset +
                '}';
    }

}
