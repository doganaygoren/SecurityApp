package com.example.security;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText etLiveFeedIp, etAlarmPushCode, etWarningPushCode, etCamActivationCode, etSystemCheckCode, etTripSensorMin, etTripSensorMax, etBuzzerActivationCode, etLedCheckCode;
    ImageView ivIPAccept, ivAlarmPush, ivWarningPush, ivCameraActivationPush, ivSystemCheckPush, ivTripMinPush, ivTripMaxPush, ivBuzzerPush, ivLedCheckPush;
    Button btnDefault, btnInfo, btnCheckUpdates;

    private static final String DEFAULT_IP="35.204.170.200/client";
    private static final String DEFAULT_ALARM_PUSH_CODE="alarm";
    private static final String DEFAULT_WARNING_PUSH_CODE="warning";
    private static final String DEFAULT_CAM_ACTIVATION_CODE="camera";
    private static final String DEFAULT_SYSTEM_CHECK_CODE="check";
    private static final int DEFAULT_TRIP_MIN=40;
    private static final int DEFAULT_TRIP_MAX=200;
    private static final String DEFAULT_BUZZER_ACTIVATION_CODE="buzzer";
    private static final String DEFAULT_LED_CHECK_CODE="led";

    private String NEW_IP;
    private String NEW_ALARM_PUSH_CODE;
    private String NEW_WARNING_PUSH_CODE;
    private String NEW_CAM_ACTIVATION_CODE;
    private String NEW_SYSTEM_CHECK_CODE;
    private int NEW_TRIP_MIN;
    private int NEW_TRIP_MAX;
    private String NEW_BUZZER_ACTIVATION_CODE;
    private String NEW_LED_CHECK_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etAlarmPushCode=findViewById(R.id.etAlarmPushCode);
        etBuzzerActivationCode=findViewById(R.id.etBuzzerActivationCode);
        etCamActivationCode=findViewById(R.id.etCamActivationCode);
        etLedCheckCode=findViewById(R.id.etLedCheckCode);
        etLiveFeedIp=findViewById(R.id.etLiveFeedIp);
        etSystemCheckCode=findViewById(R.id.etSystemCheckCode);
        etTripSensorMin=findViewById(R.id.etTripSensorMin);
        etTripSensorMax=findViewById(R.id.etTripSensorMax);
        etWarningPushCode=findViewById(R.id.etWarningPushCode);
        ivAlarmPush=findViewById(R.id.ivAlarmPush);
        ivBuzzerPush=findViewById(R.id.ivBuzzerPush);
        ivCameraActivationPush=findViewById(R.id.ivCameraActivationPush);
        ivIPAccept=findViewById(R.id.ivIPAccept);
        ivWarningPush=findViewById(R.id.ivWarningPush);
        ivSystemCheckPush=findViewById(R.id.ivSystemCheckPush);
        ivTripMinPush=findViewById(R.id.ivTripMinPush);
        ivTripMaxPush=findViewById(R.id.ivTripMaxPush);
        ivLedCheckPush=findViewById(R.id.ivLedCheckPush);
        btnCheckUpdates=findViewById(R.id.btnCheckUpdates);
        btnDefault=findViewById(R.id.btnDefault);
        btnInfo=findViewById(R.id.btnInfo);

        loadDefaults();

        //**************************************************************************************** ONCLICK METHODS ***************************************************************************

        ivIPAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_IP= etLiveFeedIp.getText().toString();
                etLiveFeedIp.setText(NEW_IP);
                Toast.makeText(getApplicationContext(),"IP is Changed to " + NEW_IP ,Toast.LENGTH_SHORT).show();
            }
        });

        ivAlarmPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_ALARM_PUSH_CODE=etAlarmPushCode.getText().toString();
                etAlarmPushCode.setText(NEW_ALARM_PUSH_CODE);
                Toast.makeText(getApplicationContext(),"Alarm Code is Changed to " + NEW_ALARM_PUSH_CODE ,Toast.LENGTH_SHORT).show();
            }
        });

        ivBuzzerPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_BUZZER_ACTIVATION_CODE=etBuzzerActivationCode.getText().toString();
                etBuzzerActivationCode.setText(NEW_BUZZER_ACTIVATION_CODE);
                Toast.makeText(getApplicationContext(),"Buzzer Code is Changed to " + NEW_BUZZER_ACTIVATION_CODE ,Toast.LENGTH_SHORT).show();
            }
        });

        ivCameraActivationPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_CAM_ACTIVATION_CODE=etCamActivationCode.getText().toString();
                etCamActivationCode.setText(NEW_CAM_ACTIVATION_CODE);
                Toast.makeText(getApplicationContext(),"Camera Activation Code is Changed to " + NEW_CAM_ACTIVATION_CODE ,Toast.LENGTH_SHORT).show();
            }
        });

        ivWarningPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_WARNING_PUSH_CODE=etWarningPushCode.getText().toString();
                etWarningPushCode.setText(NEW_WARNING_PUSH_CODE);
                Toast.makeText(getApplicationContext(),"Warning Code is Changed to " + NEW_WARNING_PUSH_CODE ,Toast.LENGTH_SHORT).show();
            }
        });

        ivSystemCheckPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_SYSTEM_CHECK_CODE=etSystemCheckCode.getText().toString();
                etSystemCheckCode.setText(NEW_SYSTEM_CHECK_CODE);
                Toast.makeText(getApplicationContext(),"System Code is Changed to " + NEW_SYSTEM_CHECK_CODE ,Toast.LENGTH_SHORT).show();
            }
        });

        ivTripMinPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_TRIP_MIN=Integer.parseInt(etTripSensorMin.getText().toString());
                if(NEW_TRIP_MIN >=10 && NEW_TRIP_MIN<=399 && checkMinRange(NEW_TRIP_MIN)){
                    etTripSensorMin.setText(String.valueOf(NEW_TRIP_MIN));
                    Toast.makeText(getApplicationContext(),"Trip Sensor Min Range is now " + NEW_TRIP_MIN ,Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!checkMinRange(NEW_TRIP_MIN))
                        Toast.makeText(getApplicationContext(),"Min value can not be greater that max value.",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Min value limit is between 10 and 399.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivTripMaxPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_TRIP_MAX=Integer.parseInt(etTripSensorMax.getText().toString());
                if(NEW_TRIP_MAX <= 400 && NEW_TRIP_MAX>11 && checkMaxRange(NEW_TRIP_MAX)) {
                    etTripSensorMax.setText(String.valueOf(NEW_TRIP_MAX));
                    Toast.makeText(getApplicationContext(), "Trip Sensor Max Range is now " + NEW_TRIP_MAX, Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!checkMaxRange(NEW_TRIP_MAX))
                        Toast.makeText(getApplicationContext(),"Max value can not be less that min value.",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Max value limit is between 11 and 400.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivLedCheckPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NEW_LED_CHECK_CODE=etLedCheckCode.getText().toString();
                etLedCheckCode.setText(NEW_LED_CHECK_CODE);
                Toast.makeText(getApplicationContext(),"LED Check Code is Changed to " + NEW_LED_CHECK_CODE ,Toast.LENGTH_SHORT).show();
            }
        });


        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDefaults();
                Toast.makeText(getApplicationContext(),"All Values are Changed to Default Values",Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"No Available Updates. ",Toast.LENGTH_SHORT).show();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(SettingsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Information About Application");
                builder.setMessage("This application is created by Doğanay GÖREN for Senior Project.");


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private boolean checkMinRange(int min) {

        if(min<NEW_TRIP_MAX)
            return true;
        else
            return false;

    }

    private boolean checkMaxRange(int max){

        if(max>NEW_TRIP_MIN)
            return true;
        else
            return false;
    }

    private void loadDefaults() {

        etWarningPushCode.setText(DEFAULT_WARNING_PUSH_CODE);
        etTripSensorMax.setText(String.valueOf(DEFAULT_TRIP_MAX));
        etTripSensorMin.setText(String.valueOf(DEFAULT_TRIP_MIN));
        etSystemCheckCode.setText(DEFAULT_SYSTEM_CHECK_CODE);
        etLiveFeedIp.setText(DEFAULT_IP);
        etAlarmPushCode.setText(DEFAULT_ALARM_PUSH_CODE);
        etBuzzerActivationCode.setText(DEFAULT_BUZZER_ACTIVATION_CODE);
        etCamActivationCode.setText(DEFAULT_CAM_ACTIVATION_CODE);
        etLedCheckCode.setText(DEFAULT_LED_CHECK_CODE);
    }
}
