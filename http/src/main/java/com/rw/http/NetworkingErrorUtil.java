package com.rw.http;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.HttpException;


@Singleton
public class NetworkingErrorUtil {

    @Inject
    public NetworkingErrorUtil() {}

    public int getErrorStatusCode(Throwable e) {
        if (e instanceof StatusCarryingException) {
            return ((StatusCarryingException) e).getStatusCode();
        } else if (e instanceof HttpException) {
            return ((HttpException) e).code();
        } else if (isParseException(e)) {
            return StatusCodes.ERROR_RETROFIT_CONVERSION;
        } else if (e instanceof IOException) {
            if (e instanceof UnknownHostException) {
                return StatusCodes.ERROR_RETROFIT_NO_CONNECTION;
            } else {
                return StatusCodes.ERROR_RETROFIT_NETWORK;
            }
        } else {
            return StatusCodes.ERROR_UNKNOWN;
        }
    }

    public boolean isConnectivityError(Throwable e) {
        return isConnectivityError(getErrorStatusCode(e));
    }

    public boolean isConnectivityError(int statusCode) {
        switch (statusCode) {
            case StatusCodes.ERROR_RETROFIT_NO_CONNECTION:
            case StatusCodes.ERROR_RETROFIT_NETWORK:
            case StatusCodes.ERROR_POLL_TIMEOUT:
                return true;
            default:
                return false;
        }
    }

    public <T> Optional<T> getResponse(Throwable error, Class<T> responseClass) {
        T body = null;

        if (error instanceof HttpException) {
            HttpException httpException = (HttpException) error;
            try {
                final String responseBody = httpException.response().errorBody().string();
                body = new Gson().fromJson(responseBody, responseClass);
            } catch (IOException | RuntimeException ignored) {
            }
        }
        return Optional.fromNullable(body);
    }

    private boolean isParseException(Throwable e) {
        if (e instanceof JsonParseException) {
            return true;
        } else if (e instanceof ParseException) {
            return true;
        }

        return false;
    }
}
