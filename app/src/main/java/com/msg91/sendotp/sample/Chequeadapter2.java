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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Chequeadapter2 extends RecyclerView.Adapter<Chequeadapter2.ProductViewHolder> {
    Intent i;
    SharedPreferences sh;
    EditText empid;

    private Context mCtx;
    private List<Cheque2> productList;

    public Chequeadapter2(Context mCtx, List<Cheque2> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList = productList;
        // sh=mCtx.getSharedPreferences("Official1",MODE_PRIVATE);
    }

    @Override
    public Chequeadapter2.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_allbloodreq, null);

        return new Chequeadapter2.ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final Chequeadapter2.ProductViewHolder holder, int position) {
        final Cheque2 cheque = productList.get(position);

        Picasso.get().load(cheque.getUser1()).into(holder.image);
        holder.blood.setText(cheque.getUser2());
        holder.phone.setText(cheque.getUser3());
        holder.name.setText(cheque.getUser4());


        holder.vieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent j=new Intent(mCtx,Review.class);
                j.putExtra("img",cheque.getUser1());
                j.putExtra("name",cheque.getUser4());
                j.putExtra("phone",cheque.getUser2());
                j.putExtra("job",cheque.getUser3());
                mCtx.startActivity(j);


            }
        });

        holder.phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +cheque.getUser2()));
                mCtx.startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView blood ,name,phone,phonecall,vieww;
        ImageView image;


        public ProductViewHolder(View itemView) {
            super(itemView);


            blood = itemView.findViewById(R.id.bloodblood1);
            name= itemView.findViewById(R.id.bloodnames1);
            phone= itemView.findViewById(R.id.bloodphone1);
            image = itemView.findViewById(R.id.bloddimag11);
            phonecall = itemView.findViewById(R.id.pchonecall1);
            vieww = itemView.findViewById(R.id.viewbt1);



        }


    }



}
