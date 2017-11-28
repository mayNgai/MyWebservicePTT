package com.dev.sevice.mywebserviceptt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        private TextView txt_product,txt_price;
        //private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_product = (TextView) v.findViewById(R.id.txt_product);
            txt_price = (TextView) v.findViewById(R.id.txt_price);
        }
    }
}
