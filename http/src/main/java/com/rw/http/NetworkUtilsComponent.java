package com.rw.http;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = NetworkUtilsModule.class)
@Singleton
interface NetworkUtilsComponent {
    ClientUtil getClientUtil();
    GsonUtil getGsonUtil();
    NetworkingErrorUtil getNetworkingErrorUtil();
}
