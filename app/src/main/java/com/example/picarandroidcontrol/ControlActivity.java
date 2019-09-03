package com.example.picarandroidcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ControlActivity extends AppCompatActivity {
	private JoystickView movementJoystick;
	private SeekBar leftAxisSeekbar;
	private SeekBar rightAxisSeekbar;
	private LinearLayout joystickLayout;
	private LinearLayout axisLayout;
	private Transmitter transmitter;
	private Switch layoutSwitch;
	private String ip;
	private String port;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		movementJoystick = findViewById(R.id.control_activity_movement_joystick);
		rightAxisSeekbar = findViewById(R.id.right_axis_seekbar);
		leftAxisSeekbar = findViewById(R.id.left_axis_seekbar);
		joystickLayout = findViewById(R.id.joystick_layout);
		axisLayout = findViewById(R.id.axis_layout);
		layoutSwitch = findViewById(R.id.layout_switch);

		ip = getIntent().getStringExtra("ip");
		port = getIntent().getStringExtra("port");

		this.transmitter = new Transmitter(ip, port, this);

		joystickLayout.setVisibility(View.GONE);
		axisLayout.setVisibility(View.VISIBLE);


		rightAxisSeekbar.setMax(200);
		leftAxisSeekbar.setMax(200);

		layoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					joystickLayout.setVisibility(View.VISIBLE);
					axisLayout.setVisibility(View.GONE);
				}else{
					joystickLayout.setVisibility(View.GONE);
					axisLayout.setVisibility(View.VISIBLE);
				}
			}
		});

		rightAxisSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				transmitter.sendRightPowerRequest(progress-100);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				rightAxisSeekbar.setProgress(100);
				transmitter.sendRightPowerRequest( 0);
			}
		});

		leftAxisSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				transmitter.sendLeftPowerRequest(progress-100);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				leftAxisSeekbar.setProgress(100);
				transmitter.sendLeftPowerRequest(0);
			}
		});

		movementJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
			@Override
			public void onMove(int angle, int strength) {
				float leftAxis = 0;
				float rightAxis = 0;
				if (angle <= 90 && angle >= 0) {

					if (angle < 45) {
						leftAxis = 100;
						rightAxis = -(100 - (angle / 0.45f));
					} else {
						leftAxis = 100;
						rightAxis = (angle - 45) / 0.45f;
					}
				} else if (angle > 90 && angle <= 180) {

					if (angle < 135) {
						leftAxis = 100 - ((angle - 90) / 0.45f);
						rightAxis = 100;
					} else {
						leftAxis = -((angle - 135) / 0.45f);
						rightAxis = 100;
					}
				} else if (angle > 180 && angle <= 270) {
					if (angle < 225) {
						leftAxis = 100 - ((angle - 180) / 0.45f);
						rightAxis = -100;
					} else {
						leftAxis = -((angle - 225) / 0.45f);
						rightAxis = -100;
					}
				} else if (angle > 270 && angle <= 360) {

					if (angle < 315) {
						leftAxis = -100;
						rightAxis = -(100 - (angle - 270 / 0.45f));
					} else {
						leftAxis = -100;
						rightAxis = -((angle - 315) / 0.45f);
					}
				}
				leftAxis = leftAxis * strength / 100;
				rightAxis = rightAxis * strength / 100;
				transmitter.sendRightPowerRequest((int)rightAxis);
				transmitter.sendLeftPowerRequest((int)rightAxis);
			}
		});

	}
}
