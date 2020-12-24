package com.asahi.demo.jwt.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;



public class HttpUtils {
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;

    private static String changeStreamToString(InputStream in) {
        StringBuffer sb = new StringBuffer();
        String readIn = null;
        try {
            BufferedReader re = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            while ((readIn = re.readLine()) != null) {
                sb.append(readIn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String submitDataByDoPost(String jsondata, String path) {
        HttpURLConnection urlConn = null;
        try {
            URL Url = new URL(path);
            urlConn = (HttpURLConnection) Url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(30000);// 设置连接主机超时（单位：毫秒）
            urlConn.setReadTimeout(30000);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Connection", "Keep-Alive");
            urlConn.setRequestProperty("Charset", "UTF-8");
            urlConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Content-Length",
                    String.valueOf(jsondata.getBytes().length));
            OutputStream os = urlConn.getOutputStream();
            os.write(jsondata.getBytes("UTF-8"));
            os.flush();
            os.close();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConn.getInputStream();
                return changeStreamToString(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return null;
    }

    public static String submitDataByDoGet(String jsondata, String path) {
        HttpURLConnection urlConn = null;
        try {
            URL Url = new URL(path);
            urlConn = (HttpURLConnection) Url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setConnectTimeout(30000);// 设置连接主机超时（单位：毫秒）
            urlConn.setReadTimeout(30000);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Connection", "Keep-Alive");
            urlConn.setRequestProperty("Charset", "UTF-8");
            // urlConn.setRequestProperty("Content-Type",
            // "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("Content-Length",
                    String.valueOf(jsondata.getBytes().length));
            OutputStream os = urlConn.getOutputStream();
            os.write(jsondata.getBytes("UTF-8"));
            os.flush();
            os.close();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConn.getInputStream();
                return changeStreamToString(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return null;
    }

    public static String submitDataByDoPostForJson(String jsondata, String path) {
        HttpURLConnection urlConn = null;
        try {
            URL Url = new URL(path);
            urlConn = (HttpURLConnection) Url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(30000);// 设置连接主机超时（单位：毫秒）
            urlConn.setReadTimeout(30000);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Connection", "Keep-Alive");
            urlConn.setRequestProperty("Charset", "UTF-8");
            urlConn.setRequestProperty("Content-Type",
                    "application/json");
            urlConn.setRequestProperty("Content-Length",
                    String.valueOf(jsondata.getBytes().length));
            OutputStream os = urlConn.getOutputStream();
            os.write(jsondata.getBytes("UTF-8"));
            os.flush();
            os.close();
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConn.getInputStream();
                return changeStreamToString(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return null;
    }


    public String connect(String host, int port) {
        String res = "与服务端建立连接失败!";
        try {
            s = new Socket(host, port);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            s.setSoTimeout(5000);
            bConnected = true;
            res = "OK";
            return res;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }

    public void disconnect() {
        try {
            bConnected = false;
            if (dos != null)
                dos.close();
            if (dis != null)
                dis.close();
            if (s != null)
                s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String submitDataSocket(String host, int port, String data) {
        String rs = "ERROR";
        rs = connect(host, port);
        if ("OK".equals(rs)) {
            try {
                rs = "服务端返回超时!";
                dos.write(data.getBytes());// .writeUTF(data);
                dos.flush();
                byte[] bytes = new byte[256];
                boolean done = false;

                while (bConnected && !done) {
                    int contentLength = dis.read(bytes);
                    if (contentLength != -1) {
                        byte[] newArr = new byte[contentLength];
                        System.arraycopy(bytes, 0, newArr, 0, contentLength);
                        String str = new String(newArr, "UTF-8");
                        rs = str;
                    }
                    done = true;
                }
            } catch (IOException e1) {
                rs = "服务端返回超时!";
                java.lang.System.out.println(e1.getLocalizedMessage());
            }
        }
        disconnect();
        return rs;
    }

    HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }


    public static void main(String[] args) {
        //HttpUtils.getToken();
    }
}


