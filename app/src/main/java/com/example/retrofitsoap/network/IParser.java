package com.example.retrofitsoap.network;

import retrofit2.Response;

/**
 * Created by hymavathi.k on 12/20/2017.
 */

public interface IParser<T> {
    void successResponse(int requestCode, Response response);

    void errorResponse(int requestCode, Throwable t);

    void noInternetConnection(int requestCode);
}
