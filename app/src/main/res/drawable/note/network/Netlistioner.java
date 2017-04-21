package com.anew.note.network;

/**
 * Created by pig on 2017/2/20.
 */

public interface Netlistioner {
    void onSuccess(Object data);
    void onFailure(String errMsg);
}
