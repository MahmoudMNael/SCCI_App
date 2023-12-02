package com.example.scciapp;

import static android.content.Context.MODE_PRIVATE;

import java.net.CookieManager;

import okhttp3.OkHttpClient;

public class HttpClient {
	private static OkHttpClient instance = new OkHttpClient();
	public static String baseUrl = "https://scci24.online/app/24/api";
	HttpClient(){
	
	}
	public static OkHttpClient getClient(){
		if (instance == null) {
			instance = new OkHttpClient();
		}
		return instance;
	}
}
