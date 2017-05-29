package com.jiumeng.http.callback;

import android.os.Handler;
import android.os.Looper;

import com.jiumeng.callback.ErrorCode;
import com.jiumeng.http.HttpCallBack;
import com.jiumeng.http.HttpErrorCode;


/**
 * Created by jiumeng on 2017/2/15.
 */

public abstract class DefaultCallBack implements HttpCallBack {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public final void onResponse(final byte[] bytes) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSucceed(bytes);
            }
        });
    }

    @Override
    public final void onFailure(final HttpErrorCode errorCode) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onError(errorCode);
            }
        });
    }

    public abstract void onSucceed(byte[] content);

    public abstract void onError(ErrorCode errorCode);

}
