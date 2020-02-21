package com.msg91.sendotp.sample;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;


import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Apply extends AppCompatActivity {
EditText date,time,datails,addres;
Button update;
    private DatePicker datePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView dateView;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    TextView map;
    SharedPreferences shh,sh,sho;
    int dayOfMonth;
    Calendar calendar;
    Location location;
    private int MAP = 2;
String aa,bb;
    Intent a;
    String address, city, state, country, postalCode, knownName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);




a=getIntent();



       // Toast.makeText(Apply.this, a.getStringExtra("pho"), Toast.LENGTH_LONG).show();

        date=findViewById(R.id.adate);
       time=findViewById(R.id.atime);
    datails=findViewById(R.id.adetails);
        addres=findViewById(R.id.aaddress);
    update=findViewById(R.id.abt);
  map=findViewById(R.id.map);

        sh=getSharedPreferences("data",MODE_PRIVATE);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
      date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(Apply.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                             date.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
       map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maplLabel = "ABC Label";
                final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q="+location.getLatitude()+","+location.getLongitude()+"&z=16 (" + maplLabel + ")"));
                startActivity(intent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                //Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Apply.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });

        sho = getSharedPreferences("locq", MODE_PRIVATE);
      //  Toast.makeText(Apply.this, sho.getString("la",null), Toast.LENGTH_LONG).show();
        SharedPreferences.Editor ede = sho.edit();

        LocationManager locationManager = (LocationManager) Apply.this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(Apply.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Apply.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Listener());
        // Try to request the location immediately
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            handleLatLng(location.getLatitude(), location.getLongitude());

            ede.putString("la", String.valueOf(location.getLatitude()));
            ede.putString("lo", String.valueOf(location.getLongitude()));
            ede.apply();

        }

    }



    private void handleLatLng(final double latitude, final double longitude) {
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(Apply.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        shh = getSharedPreferences("locc", MODE_PRIVATE);
        SharedPreferences.Editor ed = shh.edit();
        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        country = addresses.get(0).getCountryName();
        postalCode = addresses.get(0).getPostalCode();
        knownName = addresses.get(0).getFeatureName();
        ed.putString("city", city);
        ed.putString("lo", state);
        ed.apply();


        addres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addres.setText(address);
            }
        });

update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {




        if (date.getText().toString().isEmpty()){

            date.setError("Empty Field");
        }
        else if (time.getText().toString().isEmpty()){
            time.setError("Empty Field");
        }
        else if (datails.getText().toString().isEmpty()){
            datails.setError("Empty Field");
        }
        else if (addres.getText().toString().isEmpty()){
            addres.setError("Empty Field");
        }

        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/appluemp.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server
                          //  Toast.makeText(Apply.this, response, Toast.LENGTH_LONG).show();

                            date.getText().clear();
                            time.getText().clear();
                            datails.getText().clear();
                            addres.getText().clear();
                            if (response.contains("ok")) {

                                new SweetAlertDialog(Apply.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(" Success")
                                        .setContentText("Back to Dashboard!")
                                        .setConfirmText("Yes")

                                        .show();


//
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                        }

                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();



                    params.put("date",date.getText().toString());
                    params.put("time",time.getText().toString());
                    params.put("det",datails.getText().toString());
                    params.put("add",addres.getText().toString());
                    params.put("phe",a.getStringExtra("pho"));
                    params.put("name",sh.getString("name",null));
                    params.put("ph",sh.getString("phone",null));
                    params.put("img",sh.getString("image",null));
                    params.put("la",sho.getString("la",null));
                    params.put("lo",sho.getString("lo",null));
                    params.put("ename",a.getStringExtra("namee"));
                    params.put("job",a.getStringExtra("job"));
                    params.put("eimg",a.getStringExtra("eimg"));
//
//
//
//


// Toast.makeText(MainActivity.this,"submitted",Toast.LENGTH_LONG).show();

//returning parameter
                    return params;
                }

            };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(Apply.this);
            requestQueue.add(stringRequest);






        }


        {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/sms.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server
                              //Toast.makeText(Apply.this, response, Toast.LENGTH_LONG).show();

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                        }

                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();



                    params.put("add", address);
                    params.put("det", datails.getText().toString());
                    params.put("date", date.getText().toString());
                    params.put("time", time.getText().toString());
                    params.put("phone",a.getStringExtra("pho"));
                    params.put("name",sh.getString("name",null));
                    params.put("ph",sh.getString("phone",null));
                    params.put("en",a.getStringExtra("pho"));
//
//returning parameter
                    return params;
                }

            };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(Apply.this);
            requestQueue.add(stringRequest);


        }
    }
});


    }
    class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
