package com.mscs.emr.automation.utils.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAnyTrustManager {

    private static final TrustManager[] TRUST_ALL_MANAGER = new TrustManager[]{new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Ignored
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // Ignored
        }
    }};

    public SSLContext getTrustingAllSslContext(String protocol) {
        try {
            SSLContext trustingAllSslContext = SSLContext.getInstance(protocol);
            trustingAllSslContext.init(null, TRUST_ALL_MANAGER, new SecureRandom());
            return trustingAllSslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IllegalStateException(e);
        }
    }
}