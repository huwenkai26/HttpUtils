package com.jiumeng.http.image;

/**
 * Created by jiumeng on 2017/5/15.
 */

public interface ImageLoadListener {
    void start();
    void error(String errorMsg);
    void complete();
}
