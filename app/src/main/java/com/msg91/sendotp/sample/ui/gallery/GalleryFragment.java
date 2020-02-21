package com.msg91.sendotp.sample.ui.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msg91.sendotp.sample.Cheque;
import com.msg91.sendotp.sample.Cheque1;
import com.msg91.sendotp.sample.Chequeadapter;
import com.msg91.sendotp.sample.Chequeadapter1;
import com.msg91.sendotp.sample.Homepage;
import com.msg91.sendotp.sample.R;
import com.msg91.sendotp.sample.Registration;
import com.msg91.sendotp.sample.Signin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class GalleryFragment extends Fragment {
    View root;
    List<Cheque1> productList;
    SwipeRefreshLayout s;
    //the recyclerview
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    SharedPreferences sh, shh,sho;
    Chequeadapter1 adapter;
    EditText edt;
    Button delet;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        loadProducts();
        sho = getActivity().getSharedPreferences("locc", MODE_PRIVATE);
      //  Toast.makeText(getActivity(), sho.getString("city",null), Toast.LENGTH_LONG).show();
        shh = getActivity().getSharedPreferences("data11", MODE_PRIVATE);
        recyclerView= root.findViewById(R.id.recylcerViewcq1);
        edt = root.findViewById(R.id.editTextsearchq1);
        // delet = root.findViewById(R.id.delet1);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter1(editable.toString());
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe = root.findViewById(R.id.swiperefreshq1);
        sh = getActivity().getSharedPreferences("Official", MODE_PRIVATE);
        productList= new ArrayList<>();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList.clear();

                loadProducts();


            }
        });

        return root;
    }

    private void loadProducts() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/nearestemp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_LONG).show();
                        if(response.equals("no"))
                        {

                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Registration Success")
                                    .setContentText("Back To Login!")
                                    .setConfirmText("Yes,Login")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog
                                                    .setTitleText("Logining...!")

                                                    .setConfirmText("OK")

                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            Intent in=new Intent(getActivity(), Homepage.class);
                                                            startActivity(in);
                                                        }
                                                    })
                                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        }
                                    })
                                    .show();




//
                        }


                        swipe.setRefreshing(false);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = array.length() - 1; i >= 0; i--) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new Cheque1(
                                        product.getString("image"),
                                        product.getString("phone"),
                                        product.getString("job"),
                                        product.getString("name")


                                ));
                            }

                            adapter = new Chequeadapter1(getActivity(), productList);
                            // adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //  params.put("d",dis.getSelectedItem().toString().toLowerCase());
                params.put("city", sho.getString("city", null));


                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void filter1(String text) {
        ArrayList<Cheque1> filteredList1 = new ArrayList<>();

        for (Cheque1 item : productList) {
            if (item.getUser3().toLowerCase().contains(text.toLowerCase())) {
                filteredList1.add(item);
            }
        }

        adapter.filterList2(filteredList1);
    }
}