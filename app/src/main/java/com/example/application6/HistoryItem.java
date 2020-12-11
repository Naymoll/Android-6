package com.example.application6;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryItem implements Parcelable {
    private String first;
    private String second;
    private String function;
    private String result;

    public HistoryItem(String first, String second, String result, String function) {
        this.first = first;
        this.second = second;
        this.result = result;
        this.function = function;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getFunction() {
        return function;
    }

    public String getResult() {
        return result;
    }

    public static final Creator<HistoryItem> CREATOR = new Creator<HistoryItem>() {
        @Override
        public HistoryItem createFromParcel(Parcel in) {
            return new HistoryItem(in);
        }

        @Override
        public HistoryItem[] newArray(int size) {
            return new HistoryItem[size];
        }
    };

    public String getTextRepresentation() {
        return String.format("Result of %s and %s of %s is %s",
                first, second, function, result);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected HistoryItem(Parcel in) {
        first = in.readString();
        second = in.readString();
        function = in.readString();
        result = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first);
        dest.writeString(second);
        dest.writeString(function);
        dest.writeString(result);
    }
}
