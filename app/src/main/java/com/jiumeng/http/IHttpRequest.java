package com.jiumeng.http;

import java.util.Map;

/**
 * Created by jiumeng on 2017/2/15.
 */

public interface IHttpRequest {
    int GET = 0;
    int POST = 1;

    void setUrl(String url);

    void execute();

    void setHttpCallBack(HttpCallBack httpCallBack);

    void setRequestHeader(Map<String, String> requestHeader);

    void setRequestParams(Map<String, String> requestParams);

    void setRequestMode(int mode);

    void setRequestFailRetryCount(int retryCount);
    
    void setBoby(String body);
}
