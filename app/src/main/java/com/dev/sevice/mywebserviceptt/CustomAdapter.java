package com.dev.sevice.mywebserviceptt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by admin on 11/28/2017 AD.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
    private Context context;
    private List<TblData> arrayList;

    public CustomAdapter(Context _context,List<TblData> _arrayList){
        this.context = _context;
        this.arrayList = _arrayList;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.ViewHolder holder, final int i) {
        holder.txt_product.setText(arrayList.get(i).getProduct());
        holder.txt_price.setText(arrayList.get(i).getPrice());
        holder.txt_res.setVisibility(View.GONE);
        if(i==0){
            holder.txt_res.setVisibility(View.VISIBLE);
            holder.txt_res.setText("บาท/ลิตร");
        }
        if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Gasoline 95"))
            holder.img_logo.setImageResource(R.drawable.blue_gasoline_95);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Diesel"))
            holder.img_logo.setImageResource(R.drawable.blue_diesel);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Gasohol 91"))
            holder.img_logo.setImageResource(R.drawable.blue_gasohol_91);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Gasohol E20"))
            holder.img_logo.setImageResource(R.drawable.blue_gasohol_e20);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Gasohol 95"))
            holder.img_logo.setImageResource(R.drawable.blue_gasohol_95);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("Blue Gasohol E85"))
            holder.img_logo.setImageResource(R.drawable.blue_gasohol_e85);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("HyForce Premium Diesel"))
            holder.img_logo.setImageResource(R.drawable.hyforce_premium_diesel);
        else if(arrayList.get(i).getProduct().equalsIgnoreCase("NGV")){
            holder.txt_res.setVisibility(View.VISIBLE);
            holder.txt_res.setText("บาท/กิโลกรัม");
            holder.img_logo.setImageResource(R.drawable.ngv);
        }

    }
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_main, vGroup, false);
        return new CustomAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_product,txt_price,txt_res;
        private ImageView img_logo;
        //private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_res = (TextView) v.findViewById(R.id.txt_res);
            txt_product = (TextView) v.findViewById(R.id.txt_product);
            txt_price = (TextView) v.findViewById(R.id.txt_price);
            img_logo = (ImageView)v.findViewById(R.id.img_logo);
        }
    }
}
