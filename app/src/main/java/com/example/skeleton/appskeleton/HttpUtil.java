package com.example.skeleton.appskeleton;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    private enum Http_Request_Type {
        GET, POST, PUT, DELETE;
    }

    public static String getApiUrl() {
        return "http://52.27.16.34:8080/v1/";
    }

    public static String DoHttpPostRequest(String url, String toServer) {
        return DoHttpRequest(url, toServer, Http_Request_Type.POST);
    }

    public static String DoHttpGetRequest(String url, String toServer) {
        return DoHttpRequest(url, toServer, Http_Request_Type.GET);
    }

    /*public static String DoHttpPutRequest(String url, String toServer) {
        return DoHttpRequest(url, toServer, Http_Request_Type.PUT);
    }*/

    public static String DoHttpRequest(final String url, final String toServer, final Http_Request_Type type) {

                Log.d("DoHttpJsonRequest", type.toString() + " " + url + " \n" + toServer);
                String fromServer = null;
                HttpClient httpClient = new DefaultHttpClient();

                try {

                    HttpRequestBase request;
                    HttpResponse httpResponse;
                    HttpEntity entity;
                    switch (type) {
                        case GET:
                            request = new HttpGet(url);
                            break;
                        case POST:
                            request = new HttpPost(url);
                            break;
                        case PUT:
                            request = new HttpPut(url);
                            break;
                        case DELETE:
                        default:
                            Log.e("DoHttpJsonRequest", "Unimplemented type " + type.toString());
                            return null;
                    }

                    StringEntity params = new StringEntity(toServer);
                    request.addHeader("content-type", "application/json");

                    if (type == Http_Request_Type.PUT || type == Http_Request_Type.POST) {
                        ((HttpEntityEnclosingRequestBase) request).setEntity(params);
                    }

                    httpResponse = httpClient.execute(request);
                    entity = httpResponse.getEntity();
                    fromServer = EntityUtils.toString(entity, "UTF-8");
                    Log.d("API Response", fromServer);

                } catch (Exception ex) {
                    Log.d("CLOUD", ex.toString());
                    return fromServer;
                } finally {
                    httpClient.getConnectionManager().shutdown();
                }
                return fromServer;
            }

    }
