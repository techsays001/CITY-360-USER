package com.msg91.sendotp.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Review extends AppCompatActivity {
    TextView name,ph,date,exp;
            EditText details;
    ImageView image;
    Button apply,review;
    Intent j;
    String a,b,c;
    int stars;
    float starsf;
    float touchPositionX;
    private RatingBar ratingBar;
    private TextView tvRateCount,tvRateMessage;
SharedPreferences sh;
    private float ratedValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        j=getIntent();
        name=findViewById(R.id.enam1231);
        details=findViewById(R.id.rewv);
        apply=findViewById(R.id.rbt1);
        image=findViewById(R.id.img1231);
        Picasso.get().load(j.getStringExtra("img")).into(image);
        name.setText(j.getStringExtra("name"));
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvRateCount =  findViewById(R.id.tvRateCount);
        sh=getSharedPreferences("data",MODE_PRIVATE);
        tvRateMessage =  findViewById(R.id.tvRateMessage);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = ratingBar.getRating();

                tvRateCount.setText("Your Rating : "

                        + ratedValue + "/5.");

                if(ratedValue<1){

                    tvRateMessage.setText("ohh ho...");

                }else if(ratedValue<2){

                    tvRateMessage.setText("Ok.");

                }else if(ratedValue<3){

                    tvRateMessage.setText("Not bad.");

                }else if(ratedValue<4){

                    tvRateMessage.setText("Nice");

                }else if(ratedValue<5){

                    tvRateMessage.setText("Very Nice");

                }else if(ratedValue==5){

                    tvRateMessage.setText("Thank you..!!!");

                }
              // Toast.makeText(Review.this,String.valueOf(ratedValue), Toast.LENGTH_LONG).show();
            }

        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(details.getText().toString().isEmpty()){


                    details.setError("Empty");


                }

else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/review.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//If we are getting success from server
                                    //  Toast.makeText(Apply.this, response, Toast.LENGTH_LONG).show();

                                    details.getText().clear();
                                    if (response.contains("ok")) {

                                        new SweetAlertDialog(Review.this, SweetAlertDialog.WARNING_TYPE)
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


                            params.put("uname", sh.getString("name", null));
                            params.put("rev", details.getText().toString());
                            params.put("rate", String.valueOf(ratedValue));
                            params.put("name", j.getStringExtra("name"));
                            params.put("img", sh.getString("image", null));
                            params.put("ph", j.getStringExtra("phone"));
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
                    RequestQueue requestQueue = Volley.newRequestQueue(Review.this);
                    requestQueue.add(stringRequest);


                }


            }
        });


    }
}
