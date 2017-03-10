package com.youjuke.swissgearlibrary.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 描述: https工具類
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-01-04 11:56
 */

public class HttpsUtils {

    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {

        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];

        return new TrustManager[]{

                new X509TrustManager() {

                    public X509Certificate[]getAcceptedIssuers() {

                        return originalTrustManager.getAcceptedIssuers();

                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {

                        try{

                            originalTrustManager.checkClientTrusted(certs, authType);

                        }catch(CertificateException e) {

                            e.printStackTrace();

                        }

                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {

                        try{

                            originalTrustManager.checkServerTrusted(certs, authType);

                        }catch(CertificateException e) {

                            e.printStackTrace();

                        }

                    }

                }

        };

    }

    public static SSLSocketFactory getSSLSocketFactory_Certificate(Context context, String keyStoreType,int keystoreResId){

        try{
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream caInput = context.getResources().openRawResource(keystoreResId);

            Certificate ca = cf.generateCertificate(caInput);

            caInput.close();

            if(keyStoreType ==null|| keyStoreType.length() ==0) {

                keyStoreType = KeyStore.getDefaultType();

            }

            KeyStore keyStore = KeyStore.getInstance(keyStoreType);

            keyStore.load(null,null);

            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);

            tmf.init(keyStore);

            TrustManager[] wrappedTrustManagers =getWrappedTrustManagers(tmf.getTrustManagers());

            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(null, wrappedTrustManagers,null);

            return sslContext.getSocketFactory();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
