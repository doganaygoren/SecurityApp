package com.example.security;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSettings, btnBluetooth, btnLiveFeed, btnStatus, btnRemoteControl, btnEventLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings=findViewById(R.id.btnSettings);
        btnBluetooth=findViewById(R.id.btnBluetooth);
        btnLiveFeed=findViewById(R.id.btnLiveFeed);
        btnStatus=findViewById(R.id.btnStatus);
        btnRemoteControl=findViewById(R.id.btnRemoteControl);
        btnEventLog=findViewById(R.id.btnEventLog);

        btnBluetooth.setOnClickListener(this);
        btnEventLog.setOnClickListener(this);
        btnRemoteControl.setOnClickListener(this);
        btnStatus.setOnClickListener(this);
        btnLiveFeed.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        int btnId=v.getId();
        Intent intent;
        switch (btnId){

            case R.id.btnBluetooth:
                intent=new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLiveFeed:
                intent=new Intent(MainActivity.this, LiveFeedActivity.class);
                startActivity(intent);
                break;
            case R.id.btnStatus:
                intent=new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);
                break;
            case R.id.btnEventLog:
                intent=new Intent(MainActivity.this, EventLogActivity.class);
                startActivity(intent);
                break;
            case R.id.btnRemoteControl:
                intent=new Intent(MainActivity.this, RemoteControlActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSettings:
                intent=new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
