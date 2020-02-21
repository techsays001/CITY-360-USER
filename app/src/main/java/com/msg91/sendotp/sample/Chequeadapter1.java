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

public class Chequeadapter1 extends RecyclerView.Adapter<Chequeadapter1.ProductViewHolder> {
    Intent i;
    SharedPreferences sh;
    EditText empid;

    private Context mCtx;
    private List<Cheque1> productList;

    public Chequeadapter1(Context mCtx, List<Cheque1> productList) {
        sh = Objects.requireNonNull(mCtx).getSharedPreferences("data", MODE_PRIVATE);
        this.mCtx = mCtx;
        this.productList = productList;
        // sh=mCtx.getSharedPreferences("Official1",MODE_PRIVATE);
    }

    @Override
    public Chequeadapter1.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_nearreq, null);

        return new Chequeadapter1.ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final Chequeadapter1.ProductViewHolder holder, int position) {
        final Cheque1 cheque = productList.get(position);

        Picasso.get().load(cheque.getUser1()).into(holder.image);
        holder.blood.setText(cheque.getUser2());
        holder.phone.setText(cheque.getUser3());
        holder.name.setText(cheque.getUser4());



        holder.vieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent j=new Intent(mCtx,Popup.class);
                j.putExtra("img",cheque.getUser1());
                j.putExtra("name",cheque.getUser4());
                j.putExtra("ph",cheque.getUser2());
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
           vieww= itemView.findViewById(R.id.Viewww);


        }


    }

    public void filterList2(ArrayList<Cheque1> filteredList2) {
        productList = filteredList2;
        notifyDataSetChanged();
    }

}
