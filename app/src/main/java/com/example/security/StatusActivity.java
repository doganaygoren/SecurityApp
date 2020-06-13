package com.example.security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

public class StatusActivity extends AppCompatActivity {

    TextView tvTripSensorStatus, tvMotionSensorStatus, tvBuzzerSensor, tvCameraStatus, tvLedStatus, tvEspCamStatus;
    SwitchCompat switchTripSensor, switchMotionSensor, switchBuzzer, switchCamera, switchLed;
    Button btnGoLive;

    DatabaseHelper databaseHelper= new DatabaseHelper(StatusActivity.this);

    private static final String TYPE_TRIP= "TRIP SENSOR STATE CHANGED.";
    private static final String DESCRIPTION_TRIP1="Trip sensor is now ARMED.";
    private static final String DESCRIPTION_TRIP2="Trip sensor is now DISARMED.";
    private static final String TYPE_MOTION="MOTION SENSOR STATE CHANGED.";
    private static final String DESCRIPTION_MOTION1="Motion sensor is now ARMED.";
    private static final String DESCRIPTION_MOTION2="Motion sensor is now DISARMED.";
    private static final String TYPE_BUZZER="BUZZER STATE CHANGED.";
    private static final String DESCRIPTION_BUZZER1="Buzzer is now ACTIVE.";
    private static final String DESCRIPTION_BUZZER2="Buzzer is now INACTIVE.";
    private static final String TYPE_CAMERA="OV-7670 CAMERA DETECTION.";
    private static final String DESCRIPTION_CAMERA1="OV-7670 detected something.";
    private static final String TYPE_LED="LED STATE CHANGED";
    private static final String DESCRIPTION_LED1="LED is now ACTIVE.";
    private static final String DESCRIPTION_LED2="LED is now INACTIVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        tvBuzzerSensor=findViewById(R.id.tvBuzzerSensor);
        tvCameraStatus=findViewById(R.id.tvCameraStatus);
        tvEspCamStatus=findViewById(R.id.tvEspCamStatus);
        tvLedStatus=findViewById(R.id.tvLedStatus);
        tvMotionSensorStatus=findViewById(R.id.tvMotionSensorStatus);
        tvTripSensorStatus=findViewById(R.id.tvTripSensorStatus);
        switchBuzzer=findViewById(R.id.switchBuzzer);
        switchCamera=findViewById(R.id.switchCamera);
        switchLed=findViewById(R.id.switchLed);
        switchMotionSensor=findViewById(R.id.switchMotionSensor);
        switchTripSensor=findViewById(R.id.switchTripSensor);
        btnGoLive=findViewById(R.id.btnGoLive);

        //*************************************************************************************** ONCLICK METHODS ********************************************************************

        switchTripSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    try {

                        tvTripSensorStatus.setText(R.string.armed);
                        tvTripSensorStatus.setTextColor(Color.rgb(0,255,0));
                        databaseHelper.addEvent(TYPE_TRIP,DESCRIPTION_TRIP1);
                        switchTripSensor.setChecked(true);
                    } catch (Exception e){

                        e.printStackTrace();
                    }

                }
                else{
                    try {

                        tvTripSensorStatus.setText(R.string.disarmed);
                        tvTripSensorStatus.setTextColor(Color.rgb(255,0,0));
                        databaseHelper.addEvent(TYPE_TRIP,DESCRIPTION_TRIP2);
                        switchTripSensor.setChecked(false);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

        switchMotionSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    tvMotionSensorStatus.setText(R.string.armed);
                    tvMotionSensorStatus.setTextColor(Color.rgb(0,255,0));
                    databaseHelper.addEvent(TYPE_MOTION,DESCRIPTION_MOTION1);
                    switchMotionSensor.setChecked(true);
                }
                else{
                    tvMotionSensorStatus.setText(R.string.disarmed);
                    tvMotionSensorStatus.setTextColor(Color.rgb(255,0,0));
                    databaseHelper.addEvent(TYPE_MOTION,DESCRIPTION_MOTION2);
                    switchMotionSensor.setChecked(false);
                }
            }
        });

        switchBuzzer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    tvBuzzerSensor.setText(R.string.active);
                    tvBuzzerSensor.setTextColor(Color.rgb(0,255,0));
                    databaseHelper.addEvent(TYPE_BUZZER,DESCRIPTION_BUZZER1);
                    switchBuzzer.setChecked(true);
                }
                else{
                    tvBuzzerSensor.setText(R.string.inactive);
                    tvTripSensorStatus.setTextColor(Color.rgb(255,0,0));
                    databaseHelper.addEvent(TYPE_BUZZER,DESCRIPTION_BUZZER2);
                    switchBuzzer.setChecked(false);
                }
            }
        });

        switchCamera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    tvCameraStatus.setText(R.string.detected);
                    tvCameraStatus.setTextColor(Color.rgb(255,0,0));
                    databaseHelper.addEvent(TYPE_CAMERA,DESCRIPTION_CAMERA1);
                    switchCamera.setChecked(true);
                }
                else{
                    tvCameraStatus.setText(R.string.standby);
                    tvCameraStatus.setTextColor(Color.rgb(0,91,255));
                    switchCamera.setChecked(false);
                }
            }
        });

        switchLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    tvLedStatus.setText(R.string.active);
                    tvLedStatus.setTextColor(Color.rgb(0,255,0));
                    databaseHelper.addEvent(TYPE_LED,DESCRIPTION_LED1);
                    switchLed.setChecked(true);
                }
                else{
                    tvLedStatus.setText(R.string.inactive);
                    tvLedStatus.setTextColor(Color.rgb(255,0,0));
                    databaseHelper.addEvent(TYPE_LED,DESCRIPTION_LED2);
                    switchLed.setChecked(false);
                }
            }
        });

        btnGoLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goLive= new Intent(StatusActivity.this, LiveFeedActivity.class);
                startActivity(goLive);
            }
        });
    }
}
