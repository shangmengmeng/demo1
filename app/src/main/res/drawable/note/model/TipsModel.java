package com.anew.note.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pig on 2016/11/26.
 */

public class TipsModel implements Serializable {
    private    ArrayList<TipModel>list;

    public ArrayList<TipModel> getList() {
        if (list ==null){
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(ArrayList<TipModel> list) {
        this.list = list;
    }
}
