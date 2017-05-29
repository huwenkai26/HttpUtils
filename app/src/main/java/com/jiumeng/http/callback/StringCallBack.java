package com.jiumeng.http.callback;

import java.io.UnsupportedEncodingException;


/**
 * Created by jiumeng on 2017/2/15.
 */

public abstract class StringCallBack extends DefaultCallBack {
    @Override
    public void onSucceed(byte[] content) {
        try {
            onSucceed(new String(content,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public abstract void onSucceed(String content);
}
