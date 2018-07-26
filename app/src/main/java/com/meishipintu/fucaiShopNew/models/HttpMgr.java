package com.meishipintu.fucaiShopNew.models;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpMgr {

	private static final String LOG_TAG = HttpMgr.class.getSimpleName();

	private static HttpMgr instance = new HttpMgr();
	private ConnectivityManager connectivityManager;
	private Context context;
	Intent intent;
	
	private HttpMgr() {
	}
	
	public void setContext(Context ctx, Class<?> cls) {
		context = ctx; 
		
		intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("RELOGIN", 1);
//		Toast.makeText(ctx, "登陆信息失效，请重新登陆", Toast.LENGTH_LONG).show();
		intent.setClass(context, cls);
	
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);	
	}

	public static HttpMgr getInstance() {
		return instance;
	}

	public JSONObject postJson(String url, JSONObject jsonObjectParam,
			boolean withAuth) throws Exception {
		if (!isNetworkAvailable()) {
			throw new NetworkConnectionException();
		}
		try {
			if (jsonObjectParam == null) {
				jsonObjectParam = new JSONObject();
			}
			String jsonPostString = jsonObjectParam.toString();

			StringBuilder builder = new StringBuilder();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new StringEntity(jsonPostString, "UTF8"));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			if (withAuth) {
				HttpParams params = httpPost.getParams();
				params.setParameter("uid", "asdlfkjasdlkfjalsdfj");
				httpPost.setParams(params);
			}

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				throw new HttpStatusCodeException(statusCode);
			}

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			String rtnJsonStr = builder.toString();
			return new JSONObject(rtnJsonStr);
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		}
	}

	public JSONObject postMap(String url, Map<String,Object> map,
							   boolean withAuth) throws Exception {
		if (!isNetworkAvailable()) {
			throw new NetworkConnectionException();
		}
		try {
			if (map == null) {
				map = new HashMap();
			}

			StringBuilder builder = new StringBuilder();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<>();
			Set<String> keySet = map.keySet();
			for(String key : keySet) {
			   nvps.add(new BasicNameValuePair(key, (String) map.get(key)));
		    }
		    try {
			    httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		    } catch (UnsupportedEncodingException e) {
			    e.printStackTrace();
		    }
			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-type", "application/json");
			if (withAuth) {
				HttpParams params = httpPost.getParams();
				params.setParameter("uid", "asdlfkjasdlkfjalsdfj");
				httpPost.setParams(params);
				// String encoding = encodeAccountAndPassword();
				// httpPost.setHeader("HTTP-Authorization", "Basic " +
				// encoding);
			}

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				throw new HttpStatusCodeException(statusCode);
			}

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			String rtnJsonStr = builder.toString();
			return new JSONObject(rtnJsonStr);
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		}
	}

	public String postJsonRetStr(String url, JSONObject jsonObjectParam,
			boolean withAuth) throws Exception {
		if (!isNetworkAvailable()) {
			throw new NetworkConnectionException();
		}
		try {
			if (jsonObjectParam == null) {
				jsonObjectParam = new JSONObject();
			}
			String jsonPostString = jsonObjectParam.toString();

			StringBuilder builder = new StringBuilder();
			HttpPost httpPost = new HttpPost(url);

			httpPost.setEntity(new StringEntity(jsonPostString, "UTF8"));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			if (withAuth) {
				HttpParams params = httpPost.getParams();
				params.setParameter("uid", "asdlfkjasdlkfjalsdfj");
				httpPost.setParams(params);
			}

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				throw new HttpStatusCodeException(statusCode);
			}

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			String rtnJsonStr = builder.toString();
			return rtnJsonStr;
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		}
	}

	public static String encodeAccountAndPassword(String userid, String pwd)
			throws UnsupportedEncodingException {
		String text =  userid + ":" + pwd;  //Cookies.getUserId()  Cookies.getPassword()
		return new String(Base64.encodeBase64(text.getBytes("UTF-8")));
	}

	public JSONObject postGet(String url) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		return postGet(url, httpClient);
	}

	public JSONObject postGet(String url, HttpClient httpClient)
			throws Exception {
		if (!isNetworkAvailable()) {
			throw new NetworkConnectionException();
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				throw new HttpStatusCodeException(statusCode);
			}

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			String rtnJsonStr = builder.toString();
			return new JSONObject(rtnJsonStr);
		} catch (HttpHostConnectException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		} catch (SocketException e) {
			e.printStackTrace();
			throw new NetworkConnectionException();
		}
	}

	public boolean isNetworkAvailable() {
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

}
