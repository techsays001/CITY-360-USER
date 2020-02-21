package com.msg91.sendotp.sample.ui.home;

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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msg91.sendotp.sample.Cheque;
import com.msg91.sendotp.sample.Chequeadapter;
import com.msg91.sendotp.sample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    View root;
    List<Cheque> productList;
    SwipeRefreshLayout s;
    //the recyclerview
    RecyclerView recyclerView;
    SwipeRefreshLayout swipe;
    SharedPreferences sh, shh;
    Chequeadapter adapter;
    EditText edt;
    Button delet;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        loadProducts();

        shh = getActivity().getSharedPreferences("data11", MODE_PRIVATE);
        recyclerView= root.findViewById(R.id.recylcerViewcq);
        edt = root.findViewById(R.id.editTextsearchq);
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
        swipe = root.findViewById(R.id.swiperefreshq);
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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/City_360/viewemp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_LONG).show();

                        swipe.setRefreshing(false);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = array.length() - 1; i >= 0; i--) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new Cheque(
                                        product.getString("image"),
                                        product.getString("phone"),
                                        product.getString("job"),
                                        product.getString("name")


                                ));
                            }

                            adapter = new Chequeadapter(getActivity(), productList);
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
                //    params.put("phone", shh.getString("cph", null));


                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    private void filter1(String text) {
        ArrayList<Cheque> filteredList1 = new ArrayList<>();

        for (Cheque item : productList) {
            if (item.getUser3().toLowerCase().contains(text.toLowerCase())) {
                filteredList1.add(item);
            }
        }

        adapter.filterList1(filteredList1);
    }
}