package com.jiumeng.http.callback;

import com.google.gson.Gson;
import com.jiumeng.callback.ErrorCode;
import com.jiumeng.utils.Debug;


/**
 * Created by jiumeng on 2017/2/15.
 */

public abstract class JsonCallBack<M> extends StringCallBack {

    private Class<M> requestBean;

    protected JsonCallBack(Class<M> requestBean) {
        this.requestBean = requestBean;
    }

    @Override
    public void onSucceed(String content) {
    	Debug.core("json:"+content);
        if (content != null) {
            Gson gson = new Gson();
            final M m = gson.fromJson(content, requestBean);
            if (m != null) {
                onResponse(m);
            } else {
            	onError(new ErrorCode("Json parse error"));
            }
        } else {
        	onError(new ErrorCode("Json parse exception"));
        }
    }


    public abstract void onResponse(M m);

}
