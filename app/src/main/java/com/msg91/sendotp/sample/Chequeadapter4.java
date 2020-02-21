package com.msg91.sendotp.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter4 extends RecyclerView.Adapter<Chequeadapter4.ProductViewHolder> {
    Intent i;
    SharedPreferences sh;
    EditText empid;

    private Context mCtx;
    private List<Cheque4> productList;

    public Chequeadapter4(Context mCtx, List<Cheque4> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList = productList;
        // sh=mCtx.getSharedPreferences("Official1",MODE_PRIVATE);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_viewrevi, null);

        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Cheque4 cheque = productList.get(position);

        Picasso.get().load(cheque.getUser1()).into(holder.image);
        holder.name.setText(cheque.getUser2());
        holder.review.setText(cheque.getUser3());
        holder.rating.setRating(Float.parseFloat(cheque.getUser4()));;




    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView review,name;
        ImageView image;
        RatingBar rating;


        public ProductViewHolder(View itemView) {
            super(itemView);


            name= itemView.findViewById(R.id.vrew);
          review= itemView.findViewById(R.id.vname);
            image = itemView.findViewById(R.id.vimg);
        rating = itemView.findViewById(R.id.vratingBar);




        }


    }



}
