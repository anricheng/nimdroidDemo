package com.rw.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;

public class ModernTLSSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory mSocketFactory;
    private String[] mTlsVersions;

    @Inject
    public ModernTLSSocketFactory(SSLSocketFactory socketFactory) {
        mSocketFactory = socketFactory;

        ArrayList<String> tlsVersions = new ArrayList<>();
        for (TlsVersion tlsVersion : ConnectionSpec.MODERN_TLS.tlsVersions()) {
            tlsVersions.add(tlsVersion.javaName());
        }
        mTlsVersions = new String[tlsVersions.size()];
        mTlsVersions = tlsVersions.toArray(mTlsVersions);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return mSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return mSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(mSocketFactory.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTLSOnSocket(mSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(mSocketFactory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(mSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(mSocketFactory.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket)) {
            ((SSLSocket) socket).setEnabledProtocols(mTlsVersions);
        }
        return socket;
    }
}
