package com.ikoko.top.word.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Description: 如何描述该类
 *  
 * @author xujm
 * @date 2016年8月5日
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2016年8月5日.1	xujm		2016年8月5日		Create
 * </pre>
 *
 */
public class HttpClientFactory {

	private static PoolingHttpClientConnectionManager httpConnManager = null;
	private static PoolingHttpClientConnectionManager httpsConnManager = null;

	static {
		initHttp();
		initHttps();
	}

	private static void initHttp() {
		httpConnManager = new PoolingHttpClientConnectionManager();
		httpConnManager.setMaxTotal(100);
		httpConnManager.setDefaultMaxPerRoute(20);
	}

	private static void initHttps() {
		try {
			SSLContextBuilder builder = SSLContexts.custom();
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			});
			SSLContext sslContext = builder.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}

				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create().register("https", sslsf).build();

			httpsConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if (executionCount >= 5) {
				// Do not retry if over max retry count  
				return false;
			}
			if (exception instanceof InterruptedIOException) {
				// Timeout  
				return false;
			}
			if (exception instanceof UnknownHostException) {
				// Unknown host  
				return false;
			}
			if (exception instanceof ConnectTimeoutException) {
				// Connection refused  
				return false;
			}
			if (exception instanceof SSLException) {
				// SSL handshake exception  
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if (idempotent) {
				// Retry if the request is considered idempotent  
				return true;
			}
			return false;
		}
	};

	public static CloseableHttpClient getHttpsClient() {
		//设置超时
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(120000)
				.setConnectionRequestTimeout(60000).setStaleConnectionCheckEnabled(true).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpsConnManager)
				.setDefaultRequestConfig(defaultRequestConfig).setRetryHandler(retryHandler).build();
		return httpClient;
	}

	public static CloseableHttpClient getHttpClient() {
		//设置超时
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(120000)
				.setConnectionRequestTimeout(60000).setStaleConnectionCheckEnabled(true).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpConnManager)
				.setDefaultRequestConfig(defaultRequestConfig).setRetryHandler(retryHandler).build();
		return httpClient;
	}
}
