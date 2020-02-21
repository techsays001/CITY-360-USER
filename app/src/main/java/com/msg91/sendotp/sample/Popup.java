package com.msg91.sendotp.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Popup extends AppCompatActivity {
TextView name,ph,date,exp,details;
ImageView image;
Button apply,review;
Intent j;
String a,b,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
j=getIntent();
          name=findViewById(R.id.enam123);
          ph=findViewById(R.id.enam1234);
          date=findViewById(R.id.empddate);
         details=findViewById(R.id.empdet);
         exp=findViewById(R.id.empexp);
        review=findViewById(R.id.rewbt);
          apply=findViewById(R.id.apbt);
         image=findViewById(R.id.img123);
        Picasso.get().load(j.getStringExtra("img")).into(image);
        name.setText(j.getStringExtra("name"));
        ph.setText(j.getStringExtra("ph"));
   //     Toast.makeText(Popup.this,j.getStringExtra("job"), Toast.LENGTH_LONG).show();


apply.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        Intent a= new Intent(getApplicationContext(),Apply.class);
        a.putExtra("pho",j.getStringExtra("ph"));
        a.putExtra("namee",j.getStringExtra("name"));
        a.putExtra("job",j.getStringExtra("job"));
        a.putExtra("eimg",j.getStringExtra("img"));
        startActivity(a);

    }
});
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent aa= new Intent(getApplicationContext(),Viewreview.class);

                aa.putExtra("pho",j.getStringExtra("ph"));
                startActivity(aa);

            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/viewempmore.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json_obj = jsonArray.getJSONObject(i);
//ba = json_obj.getString("balance");
                                a=json_obj.getString("date");
                                b=json_obj.getString("experiance");
                                c=json_obj.getString("details");



                            }
//Toast.makeText(Recharge.this, "your new balnce is "+ba, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(Signin.this, "success", Toast.LENGTH_LONG).show();
                        if (response.contains("success")) {

date.setText(a);
exp.setText(b);
details.setText(c);










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
//Adding parameters to request
                params.put("ph", j.getStringExtra("ph"));


//returning parameter
                return params;
            }

        };
// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(Popup.this);
        requestQueue.add(stringRequest);














    }
}
