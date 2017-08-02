package com.bambi.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Miriana on 3/3/2016.
 * Request to send images
 */
class MultiPartRequest<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    private HttpEntity httpentity;
    private String FILE_PART_NAME;


    private final Response.Listener<T> mListener;
    private final FileBody mFilePart;
    private final Map<String, ContentBody> mStringPart;

    MultiPartRequest(String url, Class<T> clazz, Response.ErrorListener errorListener,
                     Response.Listener<T> listener, FileBody file,
                     Map<String, ContentBody> mStringPart, String picParam) {
        super(Method.POST, url, errorListener);
        this.clazz = clazz;
        this.mListener = listener;
        this.mFilePart = file;
        this.mStringPart = mStringPart;
        FILE_PART_NAME = picParam;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.setCharset(Charset.forName("UTF-8"));
        buildMultipartEntity();
        httpentity = entity.build();
    }

    private void buildMultipartEntity() {
        if (mFilePart != null)
            entity.addPart(FILE_PART_NAME, mFilePart);
        if (mStringPart != null) {
            for (Map.Entry<String, ContentBody> entry : mStringPart.entrySet()) {

                entity.addPart(entry.getKey(), entry.getValue());

                Log.i("VolleyRequest", entry.getValue().getCharset() + entity.toString());
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }


    @Override
    public byte[] getBody() throws AuthFailureError {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}
