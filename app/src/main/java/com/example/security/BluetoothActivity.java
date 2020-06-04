package com.example.security;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    SwitchCompat switchBluetooth;
    TextView tvDeviceNameDefiner, tvPairedHeader, tvAvailableDevices;
    ImageView ivEditButton, ivEditAcceptButton, ivRefresh;
    EditText etDeviceName;
    ListView lvPairedDevices, lvAvailableDevices;
    Button btnDiscover;
    View view2, view3, view4;

    BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;

    public ArrayList<BluetoothDevice> btDevices=new ArrayList<>();


    //*****************************************************************************LAYOUT INITIALIZATION ****************************************************************
    //In this part the layout initializations are done
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        switchBluetooth=findViewById(R.id.switchBluetooth);
        tvDeviceNameDefiner=findViewById(R.id.tvDeviceNameDefiner);
        tvPairedHeader=findViewById(R.id.tvPairedHeader);
        tvAvailableDevices=findViewById(R.id.tvAvailableDevices);
        ivEditButton=findViewById(R.id.ivEditButton);
        ivEditAcceptButton=findViewById(R.id.ivEditAcceptButton);
        ivRefresh=findViewById(R.id.ivRefresh);
        etDeviceName=findViewById(R.id.etDeviceName);
        lvPairedDevices=findViewById(R.id.lvPairedDevices);
        lvAvailableDevices=findViewById(R.id.lvAvailableDevices);
        btnDiscover=findViewById(R.id.btnDiscover);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        view4=findViewById(R.id.view4);

        tvDeviceNameDefiner.setText(bluetoothAdapter.getName());
        etDeviceName.setText(bluetoothAdapter.getName());

        // List paired devices in a suitable listview
        getPairedDevices();


        //Broadcasts When the Bond State is Changed.(PAIRED, PAIRING etc.)

        IntentFilter bondFilter=new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(bondStateReceiver, bondFilter);

        //Check if the bluetooth is already open
        if(bluetoothAdapter.isEnabled()) {
            switchBluetooth.setChecked(true);
            tvPairedHeader.setVisibility(View.VISIBLE);
            lvPairedDevices.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            tvAvailableDevices.setVisibility(View.VISIBLE);
            lvAvailableDevices.setVisibility(View.VISIBLE);
            btnDiscover.setVisibility(View.VISIBLE);
            ivRefresh.setVisibility(View.VISIBLE);
        }

        //*******************************************************ONCLICK METHODS***********************************************************************
        //Toggle Bluetooth ON/OFF
        switchBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    toggleBluetooth();
            }
        });
        //Device Name Change Operation
        tvDeviceNameDefiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                etDeviceName.setVisibility(View.VISIBLE);
                ivEditAcceptButton.setVisibility(View.VISIBLE);
                editDeviceName();
            }
        });
        //Device Name Change Operation
        ivEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                etDeviceName.setVisibility(View.VISIBLE);
                ivEditAcceptButton.setVisibility(View.VISIBLE);
                editDeviceName();
            }
        });

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Log.d("BluetoothActivity","btnDiscover:Looking for unpaired devices..");
                if(bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                    Log.d("BluetoothActivity","btnDiscover: Cancelling Discovery");

                    checkBTPermissions(); //Required for discovery, checks permissions in manifest

                    bluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent=new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(discoverBroadcastReceiver, discoverDevicesIntent);
                }
                if(!bluetoothAdapter.isDiscovering()){

                    checkBTPermissions();
                    bluetoothAdapter.startDiscovery();
                    IntentFilter discoverDevicesIntent=new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(discoverBroadcastReceiver, discoverDevicesIntent);
                }
            }
        });

        lvAvailableDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                bluetoothAdapter.cancelDiscovery();
                Log.d("BluetoothActivity","onItemClick: You Clicked On a Device");
                String deviceName=btDevices.get(position).getName();
                String deviceAddress=btDevices.get(position).getAddress();

                Log.d("BluetoothActivity","onItemClick: deviceName= " + deviceName);
                Log.d("BluetoothActivity","onItemClick: deviceAddress= " + deviceAddress);

                //creating bond
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
                    Log.d("BluetoothActivity","Trying to pair with " + deviceName);
                    btDevices.get(position).createBond();
                }
            }
        });
    }


    //************************************************* BROADCAST RECEIVERS ******************************************************************************
    private BroadcastReceiver discoverBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action= intent.getAction();
            Log.d("BluetoothActivity","onReceive: ACTION_FOUND");

            assert action != null;
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btDevices.add(device);
                assert device != null;
                Log.d("BluetoothActivity","onReceive:"+ device.getName() + ":" + device.getAddress() );

                DeviceAdapter deviceList = new DeviceAdapter(context, R.layout.device_adapter_layout, btDevices);
                lvAvailableDevices.setAdapter(deviceList);

            }
        }
    };

    private BroadcastReceiver bondStateReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action=intent.getAction();
            Log.d("BluetoothActivity","onReceive: ACTION_BOND_STATE");

            assert action != null;
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){

                BluetoothDevice device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // we have 3 cases for bonding
                //Already Bonded
                assert device != null;
                if( device.getBondState()== BluetoothDevice.BOND_BONDED){
                    Log.d("BluetoothActivity","onReceive: BOND_BONDED");
                }
                //Creating a bond
                if(device.getBondState()==BluetoothDevice.BOND_BONDING){
                    Log.d("BluetoothActivity","onReceive: BOND_BONDING");
                }
                //Breaking a bond
                if(device.getBondState()==BluetoothDevice.BOND_NONE){
                    Log.d("BluetoothActivity","onReceive: BOND_NONE");
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d("BluetoothActivity","onDestroy Called");
        super.onDestroy();

        if (discoverBroadcastReceiver != null) {
            // Sometimes the Fragment onDestroy() unregisters the observer before calling below code
            // See <a>http://stackoverflow.com/questions/6165070/receiver-not-registered-exception-error</a>
            try  {
                unregisterReceiver(discoverBroadcastReceiver);
                discoverBroadcastReceiver = null;
            }
            catch (IllegalArgumentException e) {
                // Check wether we are in debug mode
                    e.printStackTrace();
            }
        }
        //unregisterReceiver(discoverBroadcastReceiver);
        unregisterReceiver(bondStateReceiver);
    }


    //************************************************************************************PROGRAM METHODS****************************************************************************

    //Bluetooth Permission Check
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions(){

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck=this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck !=0 ) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1402); //Any Number
            }else{

                Log.d("BluetoothApplication","checkBTPermissions: No need to check permissions.");
            }
        }
    }


    //Toggle Bluetooth ON/OFF, Make Device Discoverable for 20 Seconds
    private void toggleBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Intent makeDiscoverable= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            makeDiscoverable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,120);
            startActivity(enableBt);
            startActivity(makeDiscoverable);
            tvPairedHeader.setVisibility(View.VISIBLE);
            lvPairedDevices.setVisibility(View.VISIBLE);
            view4.setVisibility(View.VISIBLE);
            tvAvailableDevices.setVisibility(View.VISIBLE);
            lvAvailableDevices.setVisibility(View.VISIBLE);
            btnDiscover.setVisibility(View.VISIBLE);
            ivRefresh.setVisibility(View.VISIBLE);
        }
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            tvPairedHeader.setVisibility(View.INVISIBLE);
            lvPairedDevices.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            tvAvailableDevices.setVisibility(View.INVISIBLE);
            lvAvailableDevices.setVisibility(View.INVISIBLE);
            btnDiscover.setVisibility(View.INVISIBLE);
            ivRefresh.setVisibility(View.INVISIBLE);
        }
    }

    // Change Device Name
    private void editDeviceName() {
        ivEditAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    String name = etDeviceName.getText().toString(); //get new device name
                    bluetoothAdapter.setName(name); //set new name
                    tvDeviceNameDefiner.setText(bluetoothAdapter.getName()); //set new name into the text view
                    etDeviceName.setText(bluetoothAdapter.getName()); //set new name into the edit text

                    //make unnecessary fields invisible
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    etDeviceName.setVisibility(View.INVISIBLE);
                    ivEditAcceptButton.setVisibility(View.INVISIBLE);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    Toast.makeText(getApplicationContext(),"Device Name Changed",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Turn On Bluetooth Before Changing Device Name",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //List Paired Device in According Field
    private void getPairedDevices(){
        pairedDevices= bluetoothAdapter.getBondedDevices();
        ArrayList<String> bondedList= new ArrayList<>();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device: pairedDevices){
                bondedList.add("Name: " + device.getName() + "\n" + "Address: " + device.getAddress());
            }
        }
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, bondedList);
        lvPairedDevices.setAdapter(adapter);
    }

}


