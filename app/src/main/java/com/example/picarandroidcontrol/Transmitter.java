package com.example.picarandroidcontrol;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

	public void sendPowerRequest(int leftAxis, int rightAxis) {
		String url = "http://" + ip + ":" + port + "/engine?powerLeft=" + leftAxis + "&powerRight=" + rightAxis;
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
		queue.add(stringRequest);
		Log.d("Controls", leftAxis + " " + rightAxis);
	}
}
