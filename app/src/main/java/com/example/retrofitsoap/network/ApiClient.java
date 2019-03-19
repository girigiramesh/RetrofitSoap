package com.example.retrofitsoap.network;

import android.content.Context;

import com.example.retrofitsoap.model.UsCittiesResponse;
import com.example.retrofitsoap.utils.CommonUtils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by hymavathi.k on 12/18/2017.
 */

public class ApiClient {
    private static Retrofit retrofit = null;
    private static WebServices webServices;
    public static ApiClient apiClient;

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    private ApiClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Strategy strategy = new AnnotationStrategy();

            Serializer serializer = new Persister(strategy);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                    .baseUrl(WSUtils.BASEURL)
                    .client(okHttpClient)
                    .build();

//            .addConverterFactory(
//                    SimpleXmlConverterFactory.createNonStrict(
//                            new Persister(new AnnotationStrategy())

            webServices = retrofit.create(WebServices.class);
        }
    }

    private String formSoapBody(Map<String, String> map, String methodName) {
        return String.format(WSUtils.SOAP_TEMPLATE, formSoapRequest(map, methodName));
    }

    private String formSoapRequest(Map<String, String> map, String methodName) {
        StringBuilder builder = new StringBuilder();

        builder.append("<" + methodName + " xmlns=\"http://www.webserviceX.NET\">");

        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        try {
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String val = map.get(key);
//                String val = URLEncoder.encode(map.get(key), "UTF-8"); // .net ppl need to decode it
//                val = val.replace("&", "&amp;");
                builder.append("<" + key + ">" + val + "</" + key + ">");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("</" + methodName + ">");
        return builder.toString();
    }

    public void WSRequest(Context context, String WSUrl, Map<String, String> params, final int requestCode, final IParser iParser) {

        if (!CommonUtils.isOnline(context)) {
            iParser.noInternetConnection(requestCode);
        } else {
            String strRes = formSoapBody(params, WSUrl);
            RequestBody body = RequestBody.create(WSUtils.SOAP_MEDIA_TYPE, strRes);
            Callback callback = new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    iParser.successResponse(requestCode, response);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    iParser.errorResponse(requestCode, t);
                }
            };
            if (WSUrl.equals(WSUtils.GET_CITIES)) {
                requestCourseList(body, callback);
            }
        }
    }

    private void requestCourseList(RequestBody body, Callback retrofitCallback) {
        Call<UsCittiesResponse> courseList = webServices.requestStateInfo(body);
        courseList.enqueue(retrofitCallback);
    }


}
