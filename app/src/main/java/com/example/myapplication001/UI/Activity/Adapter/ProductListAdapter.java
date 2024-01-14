package com.example.myapplication001.UI.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication001.Config.Config;
import com.example.myapplication001.R;
import com.example.myapplication001.UI.Activity.ProductItem;
import com.example.myapplication001.Utils.Toas;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.mViewHolder>{
    private Context mcontext;
    private  LayoutInflater layoutInflater;
    private List<ProductItem> recycle_datas;

    public ProductListAdapter() {
    }

    public ProductListAdapter(Context mcontext, List<ProductItem> datas) {
        this.mcontext = mcontext;
        this.layoutInflater = LayoutInflater.from(mcontext);
        this.recycle_datas=datas;
    }
    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= layoutInflater.inflate(R.layout.product_adapter,parent,false);
        return new mViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        ProductItem productItem=recycle_datas.get(position);
               //加载图片
        Picasso.with(mcontext)
                .load(Config.baseUrl+productItem.getgoods_logo())
                .placeholder(R.drawable.pictures_no)
                .into(holder.imageView_logo);
        holder.tv_name.setText(productItem.getgoods_name());
        holder.tv_lable.setText(productItem.getgoods_introduce());
        holder.tv_price.setText(productItem.getgoods_price()+"元/份");
        holder.tv_count.setText(""+productItem.getCount());
    }

    @Override
    public int getItemCount() {
        return recycle_datas.size();
    }
      //自定义viewholder
    class mViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView_logo,imageView_sub,imageView_add;
        public TextView tv_name,tv_lable,tv_price,tv_count;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_logo=itemView.findViewById(R.id.id_iv_image);
            imageView_add=itemView.findViewById(R.id.id_iv_add);
            imageView_sub=itemView.findViewById(R.id.id_iv_sub);
            tv_name=itemView.findViewById(R.id.id_tv_name);
            tv_lable=itemView.findViewById(R.id.id_tv_label);
            tv_price=itemView.findViewById(R.id.id_tv_price);
            tv_count=itemView.findViewById(R.id.id_tv_count);
            //为控件添加响应事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO  跳转至商品详情页面
                }
            });
            //商品份数增加按钮
            imageView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position1=getLayoutPosition();
                    //修改数据集
                    ProductItem productItem1=recycle_datas.get(position1);
                    //数量加一
                    productItem1.setCount(productItem1.getCount()+1);
                    //修改UI
                    tv_count.setText(""+productItem1.getCount());
                    //回调
                    if(mproductListener!=null){
                        mproductListener.onProductAdd(productItem1);
                    }
                }
            });
            //商品分数减少按钮
            imageView_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position2=getLayoutPosition();
                    ProductItem productItem2=recycle_datas.get(position2);
                    if(productItem2.getCount()<=0){
                        Toas.ShowToast("已经是0了哦~~");
                        return;
                    }
                    productItem2.setCount(productItem2.getCount()-1);
                    tv_count.setText(""+productItem2.getCount());
                   //回调
                    if(mproductListener!=null){
                        mproductListener.onProductSub(productItem2);
                    }

                }
            });

        }
    }
    //自定义接口

    /**
     * 通过接口回调传值（改变后的商品 ProductItem 对象）
     *  holder.tv_count.setText(""+productItem.getCount());
     */
    public interface OnproductListener{
        void onProductAdd(ProductItem productItem);
        void onProductSub(ProductItem productItem);
    }
    public OnproductListener mproductListener;
    public void setOnProductListener(OnproductListener productListener){
        this.mproductListener=productListener;
    }
}
