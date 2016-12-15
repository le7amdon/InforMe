package a7amdon.enis.tn.testiot.sensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import a7amdon.enis.tn.testiot.R;
import a7amdon.enis.tn.testiot.db.DatabaseHandler;
import a7amdon.enis.tn.testiot.model.Position;
import a7amdon.enis.tn.testiot.mqtt.MQTTPublish;
import a7amdon.enis.tn.testiot.operations.Chrono;
import a7amdon.enis.tn.testiot.operations.Operations;

/**
 * Created by 7amdon on 12/12/2016.
 */

public class SensorAccelerationTutoActivity extends AppCompatActivity implements SensorEventListener {
    private LocationManager locationManager;

    SensorManager sensorManager;
    Sensor accelerometer;
    DatabaseHandler databaseHandler;
    int blocMe = 0;
    Operations operations;
    Chrono chrono;
    MQTTPublish mqttPublish;
    double longi;
    double lati;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mqttPublish = new MQTTPublish(getApplicationContext());
        operations = new Operations(getApplicationContext());
        /**********/
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener gpsListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longi = location.getLongitude();
                lati = location.getLatitude();
                Log.d("GPS", "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
            }

            public void onProviderDisabled(String arg0) {
            }

            public void onProviderEnabled(String arg0) {
            }

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            }
        };
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 100, 1, gpsListener);
        /********************/
        databaseHandler = new DatabaseHandler(getApplicationContext());
        if (databaseHandler.getPositionById(1)!=null){
            databaseHandler.updatePosition(new Position(1,1,1,"10/10/10"));
        }else{
            databaseHandler.addPosition(new Position(1,1,1,"10/10/10"));
        }
        chrono =new Chrono();


       /* AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                chrono.start();
            }
        });*/


 }
    protected void onPause()
    {
        sensorManager.unregisterListener(this, accelerometer);
        super.onPause();
    }
    @Override
    protected void onResume()
    {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI); super.onResume();
    }
    public void publish(View v) throws IOException {
        mqttPublish.publish(v,"z="+databaseHandler.getPositionById(1).getZ()+" et les coordonnées GPS (lat,long) = ("+lati+","+longi+")" + getPlace());
    }
    public void onSensorChanged(SensorEvent event)
    {
        Position position = new Position();
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            position.setX(event.values[0]);
            position.setY(event.values[1]);
            position.setZ(event.values[2]);
            position.setDate(new Date().toString());

            /*Button a = (Button)findViewById(R.id.pbls);
            a.performClick();*/
           //String num = "24781911"; c
           /* String num = "21056391";
            String msg = "Taw Mrigel HHAHAHAHAHAHAAHAHAH";

            if(blocMe==0){
                SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
                blocMe++;
            }*/
            if (position.getZ()>=15.5){
                databaseHandler.updatePosition(position );
                chrono.setLast(java.lang.System.currentTimeMillis());
                if(blocMe==0){
                    String num = "24781911";
                    String msg = null;
                    try {
                        msg = "z="+databaseHandler.getPositionById(1).getZ()+" et les coordonnées GPS (lat,long) = ("+lati+","+longi+")" + getPlace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
                    Toast.makeText(getApplicationContext(),"Be Careful !",Toast.LENGTH_SHORT).show();
                    blocMe++;
                }
            }

/*

                if(blocMe==0){
                    databaseHandler.updatePosition(position );
                    chrono.setLast(java.lang.System.currentTimeMillis());
                    blocMe++;
                }else{
                    long last = chrono.getLast();
                    Position old_position = databaseHandler.getPositionById(1);
                    chrono.setLast(java.lang.System.currentTimeMillis());
                    if (position.getZ()>=15.5){
                        databaseHandler.updatePosition(position );
                        if(operations.twoAlertsSuccessives(last)){
                            if(chrono.getStoped()){
                                chrono.start();
                            }else{
                                if(!chrono.getLaunched()){
                                    chrono.start();
                                }
                                else{
                                    if(chrono.getDureeSec()==10){
                                        Toast.makeText(getApplicationContext(),"il y a "+chrono.getDureeSec()+"  secondes",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Stopping",Toast.LENGTH_SHORT).show();
                            chrono.stop();
                        }
                    }else{
                        chrono.stop();
                    }
                }*/

        }

     }

    public String getPlace() throws IOException {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<Address>();
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(lati,longi,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        return "I'm in "+country+" , "+city+" at "+address+" . "+postalCode;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
