package com.bambi.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;

import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Mimi on 3/3/2016.
 * handle all api calls
 */
public abstract class RestClient<T> {
    private String url;
    private Context context;
    private Class<T> clazz;
    private Map<String, ContentBody> params2;

    protected RestClient(Context context, String base, String url, Class<T> clazz) {
        this.context = context;
        this.url = base + url.replace(" ", "%20");
        this.clazz = clazz;
        sendRequest();
    }

    protected RestClient(Context context, String base, String url, Class<T> clazz, Map<String,String> params) {
        this.context = context;
        this.url = base + url.replace(" ", "%20");
        this.clazz = clazz;
        sendPostRequest(params);
    }
    private void sendPostRequest(final Map<String,String> params) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse networkResponse = volleyError.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                            onAuthentication(new String(volleyError.networkResponse.data).replace("{", "").replace("}", "").replace("\"", "").replace("data:", "").trim());
                        } else if (volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                            internetError();
                        } else
                            onError(volleyError.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };
        Singletone.getInstance(context).addToRequestQueue(stringRequest);

    }


    public RestClient(Context context, String url, Class<T> clazz, Map<String, ContentBody> params2) {
        this.context = context;
        this.url = url;
        this.clazz = clazz;
//        String picFile = null;
        this.params2 = params2;
        sendMultiPart();
    }

    private void sendMultiPart() {
       /* if(!TextUtils.isEmpty(picFile)){
            File file = new File(picFile);
            byteFile= new ByteArrayBody(getBytesFromBitmap(ExifUtils.rotateBitmap(picFile, decodeSampledBitmap(file, 800, 400)))
                    , ContentType.create("image/png"), file.getName());

        }*/
        @SuppressWarnings("unchecked")
        MultiPartRequest multipartRequest = new MultiPartRequest(url, clazz,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse networkResponse = volleyError.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                            onAuthentication(new String(volleyError.networkResponse.data).replace("{", "").replace("}", "").replace("\"", "").replace("data:", "").trim());
                        } else if (volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                            internetError();
                        } else
                            onError(volleyError.getMessage());
                    }

                },
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        onSuccess(response);
                    }

                }, (FileBody) params2.get("image"), params2, "image");

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singletone.getInstance(context).addToRequestQueue(multipartRequest);

    }


    private void sendRequest() {
        @SuppressWarnings("unchecked")
        GsonRequest req = new GsonRequest(url, clazz, null, new Response.Listener<T>() {

            @Override
            public void onResponse(T parsedData) {
                onSuccess(parsedData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                NetworkResponse networkResponse = volleyError.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    onAuthentication(new String(volleyError.networkResponse.data).replace("{", "").replace("}", "").replace("\"", "").replace("data:", "").trim());
                } else if (volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                    internetError();
                } else
                    onError(volleyError.getMessage());
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singletone.getInstance(context).addToRequestQueue(req);

    }

    public void cancel() {
        Singletone.getInstance(context).getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public abstract void internetError();

    public abstract void onSuccess(Object response);

    public abstract void onError(String message);

    public abstract void onAuthentication(String message);


}
