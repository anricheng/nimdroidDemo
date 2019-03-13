package com.rw.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

public class ClientUtil {

    private final OkHttpClient mOkHttpClient;
    private final ModernTLSSocketFactory mSocketFactory;
    private final X509TrustManager mTrustManager;
    private  Context mContext = null;

    @Inject
    ClientUtil(OkHttpClient okHttpClient, ModernTLSSocketFactory socketFactory, X509TrustManager trustManager) {
        mOkHttpClient = okHttpClient;
        mSocketFactory = socketFactory;
        mTrustManager = trustManager;
    }

    public RWServiceRetrofitBuilder buildRestAdapter(String baseUrl, long timeoutInSeconds, Gson gson, boolean followRedirects, Context context) {
        mContext = context;
        return new RWServiceRetrofitBuilder(mOkHttpClient, mSocketFactory, mTrustManager)
                .baseUrl(baseUrl)
                .timeoutInSeconds(timeoutInSeconds)
                .addConverter(GsonConverterFactory.create(gson))
                .followRedirects(followRedirects);
    }

    public class RWServiceRetrofitBuilder {

        private OkHttpClient mOkHttpClient;
        private SSLSocketFactory mSocketFactory;
        private X509TrustManager mTrustManager;
        private List<Interceptor> mInterceptors = new ArrayList<>();
        private List<Converter.Factory> mConverters = new ArrayList<>();
        private boolean mFollowRedirects = true;
        private long mTimeoutInSeconds;
        private String mBaseUrl;
        private ExecutorService mCallbackExecutor;
        private ExecutorService mHttpExecutor;
        private List<ConnectionSpec> mConnectionSpecs;

        public RWServiceRetrofitBuilder(OkHttpClient okHttpClient, SSLSocketFactory socketFactory, X509TrustManager trustManager) {
            mOkHttpClient = okHttpClient;
            mSocketFactory = socketFactory;
            mTrustManager = trustManager;
            mConnectionSpecs = Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT); // enable TLS 1.0/1.1/1.2 for https, cleartext for http
        }

        public RWServiceRetrofitBuilder addInterceptor(Interceptor interceptor) {
            mInterceptors.add(interceptor);
            return this;
        }

        public RWServiceRetrofitBuilder addConverter(Converter.Factory factory) {
            mConverters.add(factory);
            return this;
        }

        public RWServiceRetrofitBuilder followRedirects(boolean followRedirects) {
            mFollowRedirects = followRedirects;
            return this;
        }

        public RWServiceRetrofitBuilder timeoutInSeconds(long timeoutInSeconds) {
            mTimeoutInSeconds = timeoutInSeconds;
            return this;
        }

        public RWServiceRetrofitBuilder baseUrl(String baseUrl) {
            mBaseUrl = baseUrl;
            return this;
        }

        public RWServiceRetrofitBuilder callbackExecutor(ExecutorService executorService) {
            mCallbackExecutor = executorService;
            return this;
        }

        public RWServiceRetrofitBuilder httpExecutor(ExecutorService executorService) {
            mHttpExecutor = executorService;
            return this;
        }

        public RWServiceRetrofitBuilder connectionSpecs(ConnectionSpec... connectionSpecs) {
            mConnectionSpecs = Arrays.asList(connectionSpecs);
            return this;
        }

        public Retrofit build() {
            final OkHttpClient.Builder okHttpBuilder = mOkHttpClient.newBuilder();

            for(Interceptor interceptor : mInterceptors) {
                okHttpBuilder.addInterceptor(interceptor);
            }
            okHttpBuilder.addInterceptor(provideHttpLoggingInterceptor());

            okHttpBuilder.followRedirects(mFollowRedirects);
            okHttpBuilder.readTimeout(mTimeoutInSeconds, TimeUnit.SECONDS);
            okHttpBuilder.writeTimeout(mTimeoutInSeconds, TimeUnit.SECONDS);
            okHttpBuilder.connectTimeout(mTimeoutInSeconds, TimeUnit.SECONDS);

            if (mHttpExecutor != null) {
                okHttpBuilder.dispatcher(new Dispatcher(mHttpExecutor));
            }

            okHttpBuilder.sslSocketFactory(mSocketFactory, mTrustManager);
            okHttpBuilder.connectionSpecs(mConnectionSpecs);

            final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.client(okHttpBuilder.build());

            String baseUrl = mBaseUrl;
            if (baseUrl != null && baseUrl.length() > 0) {
                if (!baseUrl.endsWith("/")) {
                    baseUrl += "/";
                }

                retrofitBuilder.baseUrl(HttpUrl.parse(baseUrl));
            }

            if (mCallbackExecutor != null) {
                retrofitBuilder.callbackExecutor(mCallbackExecutor);
            }

            for (Converter.Factory converter : mConverters) {
                retrofitBuilder.addConverterFactory(converter);
            }

            retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

            return retrofitBuilder.build();
        }

        private HttpLoggingInterceptor provideHttpLoggingInterceptor() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger(){

                @Override
                public void log(String message) {
                    Log.i("response",message);
                }
            });
            interceptor.setLevel(Level.BODY);
            return interceptor;
        }
    }
}