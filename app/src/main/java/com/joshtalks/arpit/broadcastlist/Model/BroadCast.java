package com.joshtalks.arpit.broadcastlist.Model;

/**
 * Created by ARPIT on 05-05-2018.
 */

public class BroadCast {
    private String BroadCastNum, Ref, Message, Date, Link, AuthorName, TimeToRead;

    BroadCast(){

    }

    public BroadCast(String broadCastNum, String ref, String message, String date, String link, String authorName, String timeToRead) {
        BroadCastNum = broadCastNum;
        Ref = ref;
        Message = message;
        Date = date;
        Link = link;
        AuthorName = authorName;
        TimeToRead = timeToRead;
    }

    public String getBroadCastNum() {
        return BroadCastNum;
    }

    public void setBroadCastNum(String broadCastNum) {
        BroadCastNum = broadCastNum;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getTimeToRead() {
        return TimeToRead;
    }

    public void setTimeToRead(String timeToRead) {
        TimeToRead = timeToRead;
    }
}
