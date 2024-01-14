package com.example.myapplication001.UI.Activity.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication001.Bean.Order;
import com.example.myapplication001.Config.Config;
import com.example.myapplication001.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.mOrderVH> {
    private Context context;
    private LayoutInflater layoutInflater;
    private Order morder;
    private List<Order> mdata;
    public OrderListAdapter(Context context,List<Order> array) {
        this.context = context;
        this.mdata=array;
        layoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public mOrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item=layoutInflater.inflate(R.layout.order_adapter,parent,false);
        return new mOrderVH(item);
    }

    @Override
    public void onBindViewHolder(@NonNull mOrderVH holder, int position) {
        morder=mdata.get(position);
        //加载图片
        Picasso.with(context)
                .load(Config.baseUrl+morder.getGoods_logo())
                .placeholder(R.drawable.pictures_no)
                .into(holder.iv_icon);
        holder.tv_name.setText(morder.getGoods_name());
        holder.tv_number.setText(morder.getNumber()+" ");
        holder.tv_price.setText(morder.getGoods_price());
        holder.tv_time.setText(morder.getCreate_time());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    class mOrderVH extends RecyclerView.ViewHolder{
        public ImageView iv_icon;
        public TextView tv_name,tv_price,tv_number,tv_time;

        public mOrderVH(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_time=itemView.findViewById(R.id.tv_time);
            iv_icon=itemView.findViewById(R.id.iv_image);
        }
    }
}
