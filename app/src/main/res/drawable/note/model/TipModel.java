package com.anew.note.model;

import java.io.Serializable;

/**
 * Created by pig on 2016/11/26.
 */
//普通便签
public class TipModel implements Serializable {

    protected int tip;
    protected int number;
    protected String date;
    protected String content;


    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TipModel{" +
                "tip=" + tip +
                ", number=" + number +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
