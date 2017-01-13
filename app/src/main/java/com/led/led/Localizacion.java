package com.led.led;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by WARREN on 10/01/2017.
 */
public class Localizacion implements LocationListener {

    TextView mensaje1;
    TextView mensaje2;

    @Override
    public void onLocationChanged(Location loc) {

        mensaje1.setText("warren");
        loc.getLatitude();
        loc.getLongitude();
        String Text = "Mi ubicacion actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();

        mensaje1.setText(Text);
//			this.mainActivity.setLocation(loc);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        mensaje1.setText("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es activado
        mensaje1.setText("GPS Activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Este metodo se ejecuta cada vez que se detecta un cambio en el
        // status del proveedor de localizacion (GPS)
        // Los diferentes Status son:
        // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
        // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
        // espera que este disponible en breve
        // AVAILABLE -> Disponible
    }

}/* Fin de la clase localizacion */