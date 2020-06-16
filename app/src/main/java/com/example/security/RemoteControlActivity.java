package com.example.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RemoteControlActivity extends AppCompatActivity {

    EditText etCommand, etPhone;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        etCommand=findViewById(R.id.etCommand);
        etPhone=findViewById(R.id.etPhone);
        btnSend=findViewById(R.id.btnSend);



    }

    public void send_btn(View view) {

        int permissionCheck=ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);

        if(permissionCheck==PackageManager.PERMISSION_GRANTED){

            sendMessage();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},0);
        }

    }

    private void sendMessage() {

        String command= etCommand.getText().toString().trim();
        String number= etPhone.getText().toString().trim();

        if(!etPhone.getText().toString().equals("") || etCommand.getText().toString().equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, command, null, null);

            Toast.makeText(getApplicationContext(), "Command has been sent via SMS.", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(getApplicationContext(),"Command or Number cannot be blank.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 0:

                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    sendMessage();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You do not have permission to send SMS.",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
