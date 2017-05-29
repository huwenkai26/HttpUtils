package com.jiumeng.http;

import android.content.Context;


import java.util.Map;

/**
 * Created by jiumeng on 2017/2/15.
 */

public class HttpTask implements Runnable {
    private HttpRequest httpRequest;

    HttpTask(Context context, int requestMode, Map<String, String> requestHeaders, String bodyContent,   		
    		Map<String, String> requestParams, String url, HttpCallBack httpListener) {
        httpRequest = new HttpRequest(context);
        httpRequest.setUrl(url);
        httpRequest.setHttpCallBack(httpListener);
        httpRequest.setRequestParams(requestParams);
        httpRequest.setRequestHeader(requestHeaders);
        httpRequest.setRequestMode(requestMode);
        httpRequest.setBoby(bodyContent);
    }

    @Override
    public void run() {
        httpRequest.execute();
    }
}
