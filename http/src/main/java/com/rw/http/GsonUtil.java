package com.rw.http;

import com.google.gson.GsonBuilder;

import javax.inject.Inject;

public class GsonUtil {

    @Inject
    GsonUtil() {}

    public static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'";

    public GsonBuilder buildGson() {
        return new GsonBuilder().setDateFormat(DATE_FORMAT);
    }
}
