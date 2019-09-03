package com.example.picarandroidcontrol;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
	private EditText ipInput;
	private EditText portInput;
	private Button loginBtn;
	private RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ipInput = findViewById(R.id.login_activity_ip_input);
		portInput = findViewById(R.id.login_activity_port_input);
		loginBtn = findViewById(R.id.login_activity_connect_button);

		queue = Volley.newRequestQueue(this);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://" + ipInput.getText().toString() + ":" + portInput.getText().toString() + "/check";
				StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								if(response.equals("yeet")){
									Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(LoginActivity.this, ControlActivity.class);
									if (ipInput.getText().toString().length()>15){
										intent.putExtra("ip", "[" + ipInput.getText().toString() +"]");
									}else{
										intent.putExtra("ip", ipInput.getText().toString());
									}
									intent.putExtra("port", portInput.getText().toString());
									startActivity(intent);
								}
							}
						}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
					}
				});
				queue.add(stringRequest);
			}
		});



	}
}
