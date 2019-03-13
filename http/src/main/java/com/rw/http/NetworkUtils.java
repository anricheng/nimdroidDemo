package com.rw.http;


public class NetworkUtils {

    private static NetworkUtilsComponent mNetworkUtilsComponent;

    public NetworkUtils() {
        if (mNetworkUtilsComponent == null) {
            mNetworkUtilsComponent = DaggerNetworkUtilsComponent.create();
        }
    }

    public GsonUtil getGsonUtil() {
        return mNetworkUtilsComponent.getGsonUtil();
    }

    public ClientUtil getClientUtil() {
        return mNetworkUtilsComponent.getClientUtil();
    }

    public NetworkingErrorUtil getNetworkingErrorUtil() {
        return mNetworkUtilsComponent.getNetworkingErrorUtil();
    }
}
