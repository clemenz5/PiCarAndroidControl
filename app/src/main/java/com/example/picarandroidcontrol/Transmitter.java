package com.example.picarandroidcontrol;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Transmitter {
	private String ip;
	private String port;
	private RequestQueue queue;

	public Transmitter(String ip, String port, Context context) {
		this.ip = ip;
		this.port = port;
		queue = Volley.newRequestQueue(context);
	}


	public void sendLeftPowerRequest(int leftAxis){
		String url = "http://" + ip + ":" + port + "/powerLeft?value=" + leftAxis;
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		queue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
		queue.add(stringRequest);
	}

	public void sendRightPowerRequest(int rightAxis){
		String url = "http://" + ip + ":" + port + "/powerRight?value=" + rightAxis;
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		});

		queue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});
		queue.add(stringRequest);
	}
}
