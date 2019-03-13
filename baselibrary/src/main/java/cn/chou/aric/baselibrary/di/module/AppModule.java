package cn.chou.aric.baselibrary.di.module;

import android.content.Context;

import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.chou.aric.baselibrary.model.AppService;
import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 拥有全局生命周期的单例都在这个AppModule中提供
 */
@Module
public class AppModule {

    private Context mApplicationContext;

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Singleton
    @Provides
    Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    AppService provideAppService(Retrofit retrofit) {
        return retrofit.create(AppService.class);
    }

    @Singleton
    @Provides
    HostnameVerifier provideHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(HostnameVerifier hostnameVerifier) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(hostnameVerifier)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl("https://api.apiopen.top")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
