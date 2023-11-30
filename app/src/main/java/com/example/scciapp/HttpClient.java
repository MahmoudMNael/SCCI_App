package com.example.scciapp;

import static android.content.Context.MODE_PRIVATE;

import java.net.CookieManager;

import okhttp3.OkHttpClient;

public class HttpClient {
	private static OkHttpClient instance = new OkHttpClient();
	public static String baseUrl = "http://10.0.2.2:5000/api";
	HttpClient(){
	
	}
	public static OkHttpClient getClient(){
		if (instance == null) {
			instance = new OkHttpClient();
		}
		return instance;
	}
}
