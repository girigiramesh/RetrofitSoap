package com.example.retrofitsoap.network;

import okhttp3.MediaType;

/**
 * Created by hymavathi.k on 12/20/2017.
 */

public class WSUtils {
    public static final String SOAP_TEMPLATE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
            + "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">"
            + "<soap12:Body>" + "%s" + "</soap12:Body>" + "</soap12:Envelope>";

    public static final MediaType SOAP_MEDIA_TYPE = MediaType.parse("text/xml");

    public static final String BASEURL = "http://www.webservicex.net/";


    public static final String GET_CITIES = "GetInfoByCity";

    public static final int REQ_GET_CITIES = 1;

    /*GetInfoByCity input keys*/
    public static final String KEY_USCITY = "USCity";
}
