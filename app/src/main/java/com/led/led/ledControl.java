package com.led.led;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;


public class ledControl extends ActionBarActivity {

    Button arriba, abajo, btnDis, izq, der;
    TextView  latitud, longitud,latdes,longdes;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent newint = getIntent();
 //       address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recivimos la mac address obtenida en la actividad anterior
 //       setContentView(R.layout.activity_led_control);
        max_dit=5.2;
        btnDis = (Button) findViewById(R.id.button4);
        arriba = (Button) findViewById(R.id.arriba);
        abajo = (Button) findViewById(R.id.abajo);
        izq = (Button) findViewById(R.id.izquierda);
        der = (Button) findViewById(R.id.derecha);
        latdes = (TextView) findViewById(R.id.tblatdest);
        longdes = (TextView) findViewById(R.id.tblongdes);
        latitud = (TextView) findViewById(R.id.idlatitud);
        longitud = (TextView) findViewById(R.id.idlongitud);
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local
                = new Localizacion();
        Local.mensaje1 = latitud;
        Local.mensaje2 = longitud;


        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        new ConnectBT().execute(); //Call the class to connect

/*
        arriba.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Button pressed TextOne.setText("Clicking");
                    // return true; } else if (event.getAction() == MotionEvent.ACTION_UP)
                    // { //Button released TextOne.setText("Released"); return true; } return false; } });
                }*/
        arriba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    arriva();
                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    paro();
                    return true;
                }
                return false;
            }
        });
        abajo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    abajo();
                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    paro();
                    return true;
                }
                return false;
            }
        });
        izq.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    izquierda();
                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    paro();
                    return true;
                }
                return false;
            }
        });
        der.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    derecha();
                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    paro();
                    return true;
                }
                return false;
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect();
            }
        });

    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }
    public Double max_dit;
    public void comensar(){


    }

    public void run(View v) {

    }
    public double dist(double a,double b,double x,double y){
        double ax=x-a;
        double by=y-b;

        return 8.5;
    }
    public void avansa(){
        try {
                arriva();
                Thread.sleep(5800);
                paro();
                Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void girar1(){
        try {
                izquierda();
                Thread.sleep(700);
                paro();
                Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void girar2(){
        try {
            for(int i=0;i<18;i++){
                derecha();
                Thread.sleep(52);
                paro();
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("W".toString().getBytes());

                msg("g");
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    public void paro(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("P".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    public void arriva(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("W".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    public void abajo(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    public void derecha(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("D".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    public void izquierda(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("A".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Conexión Fallida");
                finish();
            }
            else
            {
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
