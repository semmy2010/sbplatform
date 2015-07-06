package com.sbplatform.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 基于 httpclient 4.4.1版本的 http工具类
 */

public class HttpUtil {

	private static final CloseableHttpClient httpClient;

	public static final String CHARSET = "UTF-8";

	static {

		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();

		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

	}

	public static String doGet(String url, Map<String, String> params, Map<String, String> headerParams) {

		return doGet(url, params, headerParams, CHARSET);

	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> headerParams) {

		return doPost(url, params, headerParams, CHARSET);

	}

	/**
	 * 
	 * HTTP Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * 
	 * @param params
	 *            请求的参数
	 * 
	 * @param charset
	 *            编码格式
	 * 
	 * @return 页面内容
	 */

	public static String doGet(String url, Map<String, String> params, Map<String, String> headerParams, String charset) {

		if (StringUtils.isBlank(url)) {
			return null;
		}

		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}

			HttpGet httpGet = new HttpGet(url);
			//设置请求头部参数
			if (headerParams != null && !headerParams.isEmpty()) {
				for (Map.Entry<String, String> entry : headerParams.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}

			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * 
	 * @param params
	 *            请求的参数
	 * 
	 * @param charset
	 *            编码格式
	 * 
	 * @return 页面内容
	 */

	public static String doPost(String url, Map<String, String> params, Map<String, String> headerParams, String charset) {

		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}

			HttpPost httpPost = new HttpPost(url);
			if (headerParams != null && !headerParams.isEmpty()) {
				for (Map.Entry<String, String> entry : headerParams.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
