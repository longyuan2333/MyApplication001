package com.example.myapplication001.UI.Activity.Activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication001.Bean.Order;
import com.example.myapplication001.Biz.Orderbiz;
import com.example.myapplication001.Net.CommonCallBack;
import com.example.myapplication001.R;
import com.example.myapplication001.UI.Activity.Adapter.OrderListAdapter;
import com.example.myapplication001.UI.Activity.CircleTransform;
import com.example.myapplication001.UI.Activity.view.refresh.SwipeRefresh;
import com.example.myapplication001.UI.Activity.view.refresh.SwipeRefreshLayout;
import com.example.myapplication001.Utils.Toas;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class OrderActivity extends BaseActivity{
    private static final String TAG = "OrderActivity";
    private Button toOrderbtn;
    private ImageView iv_header;
    private TextView tv_username;
    private CircleTransform circleTransform =new CircleTransform();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private OrderListAdapter madapter;
    private List<Order> morderList=new ArrayList<>();
    private Orderbiz orderbiz=new Orderbiz();
    private String username;
    private  int currentPage=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);
        initView();
        loadData();
        initEvent();
    }

    private void initEvent() {
    toOrderbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(OrderActivity.this,ProductListActivity.class));
        }
    });
    }

    @Override
    protected void onResume() {
        super.onResume();
       loadData();
    }

    private void initView() {
   //     username= UserInfoViewHolder.getInstance().getUser().getUsername();
       iv_header=findViewById(R.id.order_iv_icon);
       toOrderbtn=findViewById(R.id.id_btn_take_order);
       tv_username=findViewById(R.id.id_tv_name);
       iv_header.setImageBitmap(circleTransform.transform(BitmapFactory.decodeResource(getResources(),R.drawable.icon)));
  //     tv_username.setText(username);
       recyclerView=findViewById(R.id.order_recycle);
       swipeRefreshLayout=findViewById(R.id.order_refresh);
       madapter=new OrderListAdapter(OrderActivity.this,morderList);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setAdapter(madapter);
        //设置开关
        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        //设置颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK);
       //上拉刷新
       swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
           @Override
           public void onPullUpRefresh() {
               loadMore();
                       }
       });
       //下拉刷新
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
           @Override
           public void onRefresh() {
               loadData();
                         }
       });

    }
    private void loadMore() {
        startLodingProgress();
       orderbiz.order_list(username, ++currentPage, new CommonCallBack<List<Order>>() {
           @Override
           public void onError(Call call, Exception e, int id) {

           }

           @Override
           public void onError(Exception e) {
               stopLodingProgress();
               Toas.ShowToast(e.getMessage());
               currentPage--;
               //如果还在刷新数据就关闭刷新
               swipeRefreshLayout.setPullUpRefreshing(false);
           }

           @Override
           public void onSuccess(List<Order> response) {
               stopLodingProgress();
               swipeRefreshLayout.setPullUpRefreshing(false);
               if(response.size()==0){
                   Toas.ShowToast("已经到底了~~");
                   return;
               }
               for(Order p : response){
                   morderList.add(p);
               }
               //通知适配器数据已经发生改变
               madapter.notifyDataSetChanged();
           }
       });
    }

    private void loadData() {
        startLodingProgress();
        orderbiz.order_list(username,1, new CommonCallBack<List<Order>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onError(Exception e) {
                stopLodingProgress();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toas.ShowToast("订单刷新失败");
            }
            @Override
            public void onSuccess(List<Order> response) {
                stopLodingProgress();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                morderList.clear();
                currentPage=1;
                for(Order p: response){
                    morderList.add(p);
                }
                Toas.ShowToast("订单刷新成功~~");
                madapter.notifyDataSetChanged();
            }
        });

    }
}
