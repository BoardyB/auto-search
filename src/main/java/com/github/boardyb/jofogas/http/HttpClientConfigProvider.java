package com.github.boardyb.jofogas.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

class HttpClientConfigProvider {

    static PoolingHttpClientConnectionManager createConnectionManager() throws Exception {
        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", createSocketFactory())
                        .build();
        return new PoolingHttpClientConnectionManager(socketFactoryRegistry);
    }

    static SSLConnectionSocketFactory createSocketFactory() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(new KeyManager[0], new TrustManager[]{new AcceptAllTrustManager()}, new SecureRandom());
        return new SSLConnectionSocketFactory(sslContext,
                new String[]{"TLSv1",
                        "SSLv3"},
                null,
                SSLConnectionSocketFactory
                        .getDefaultHostnameVerifier());
    }

}
