package com.jiumeng.http;

import android.content.Context;

import com.jiumeng.utils.Debug;
import com.jiumeng.utils.CommonUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by jiumeng on 2017/2/15.
 */

public class HttpRequest implements IHttpRequest {


    private final Context mContext;
    public int mReadTimeout;
    public int mConnectionTimeout;
    private int mRetryCount;//请求网络失败 重试次数
    private int mRetryTime;//请求网络失败后 重试加载时间间隔
    private HttpCallBack mHttpCallBack;
    private String mRequestUrl;
    private Map<String, String> mRequestHeaders;
    private Map<String, String> mRequestParams;
    private int mRequestMode;
    private String mBodyContent;

    public HttpRequest(Context context) {
        this.mContext = context;
        //设置默认参数
        mRetryCount = 1;
        mRetryTime = 1000;
        mConnectionTimeout = 8000;
        mReadTimeout = 8000;
    }

    public HttpRequest(Context context, HttpRequestConfig config) {
        this.mContext = context;
        mRetryCount = config.retryCount;
        mRetryTime = config.retryTime;
        mConnectionTimeout = config.connectionTimeout;
        mReadTimeout = config.readTimeout;
    }


    @Override
    public void execute() {
    	Debug.core("http execute----" + "method:" + (mRequestMode== 0 ? "get" : "post"));
        Debug.core("http execute----" + "url:" + mRequestUrl);

        //判断网络
        if (CommonUtil.getNetworkType(mContext) == 0) {
            if (mHttpCallBack != null) {
                mHttpCallBack.onFailure(new HttpErrorCode("No internet connection detected."));
            }
            return;
        }
        requestNetwork();

    }


    private void requestNetwork() {

        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;

        if (mRequestParams != null) {
            String requestParams = mapToString(mRequestParams);
            if (!mRequestUrl.contains("?")) {
                mRequestUrl = mRequestUrl + "?" + requestParams;
            } else if (mRequestUrl.substring(mRequestUrl.length() - 1).equals("?")) {
                mRequestUrl = mRequestUrl + requestParams;
            }
        }
        Debug.core("request url:"+ mRequestUrl);
        try {
            URL url = new URL(mRequestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(mReadTimeout);
            conn.setConnectTimeout(mConnectionTimeout);

            //设置请求头
            if (mRequestHeaders != null) {
                for (String key : mRequestHeaders.keySet()) {
                    if (key != null && mRequestHeaders.get(key) != null) {
                        conn.setRequestProperty(key, mRequestHeaders.get(key));
                    }
                }
            }

            switch (mRequestMode) {
                case GET:
                    conn.setRequestMethod("GET");
                    break;
                case POST:
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    if (mBodyContent != null) {
                        byte[] b = mBodyContent.getBytes();
                        conn.getOutputStream().write(b, 0, b.length);
                        conn.getOutputStream().flush();
                        conn.getOutputStream().close();
                    }
                    break;
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                is = conn.getInputStream();

                byte[] bytes = new byte[1024];
                int len;
                bos = new ByteArrayOutputStream();
                while ((len = is.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
                if (mHttpCallBack != null) {
                    mHttpCallBack.onResponse(bos.toByteArray());
                }
            } else {
                //网络加载失败后重试
                retryRequest(new HttpErrorCode("Request error code:"+responseCode));

            }
        } catch (MalformedURLException e) {
            //网络加载失败后重试
            retryRequest(new HttpErrorCode("Request Url error"));            
            Debug.core(e.toString());
        } catch (IOException e) {
            //网络加载失败后重试
            retryRequest(new HttpErrorCode("Request IO Exception"));
            Debug.core(e.toString());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect(); // 断开连接
            }

        }
    }


    private void retryRequest(HttpErrorCode error) {
        //网络加载失败后重试
        if (mRetryCount > 0) {
            mRetryCount--;
            Debug.core(error + " try reload after " + mRetryTime/1000 + " seconds");
            try {
				Thread.sleep(mRetryTime);
				execute();
			} catch (Exception e) {
				// TODO: handle exception
			}            
        } else {
        	if (mHttpCallBack != null) {
                mHttpCallBack.onFailure(error);
            }
        }
    }

    private String mapToString(Map<String, String> map) {
        String[] params = new String[map.size()];
        int i = 0;
        for (String paramKey : map.keySet()) {
        	String value = map.get(paramKey);
        	paramKey = URLEncoder.encode(paramKey);
        	value = ((value == null) ? "" :URLEncoder.encode(value));
            String param = paramKey + "=" + value;
            params[i] = param;
            i++;
        }
        String paramsTemp = "";
        if (params != null) {
            for (String param : params) {
                if (param != null && !"".equals(param.trim())) {
                    paramsTemp += "&" + param;
                }
            }
        }
        Debug.core("request param:"+ paramsTemp);
        return paramsTemp;
    }


    @Override
    public void setUrl(String url) {
        this.mRequestUrl = url;
    }

    @Override
    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.mHttpCallBack = httpCallBack;
    }

    @Override
    public void setRequestHeader(Map<String, String> requestHeader) {
        this.mRequestHeaders = requestHeader;
    }

    @Override
    public void setRequestParams(Map<String, String> requestParams) {
        this.mRequestParams = requestParams;
    }

    @Override
    public void setRequestMode(int mode) {
        this.mRequestMode = mode;
    }

    @Override
    public void setRequestFailRetryCount(int retryCount) {
        this.mRetryCount = retryCount;
    }
    
    @Override
    public void setBoby(String body) {
    	mBodyContent = body;
    }

    public class HttpRequestConfig {
        public int retryCount;//请求网络失败 重试次数
        public int retryTime;//请求网络失败后 重试加载时间间隔
        public int readTimeout;
        public int connectionTimeout;
    }
}
