package com.example.myapplication001.UI.Activity.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication001.Bean.Products;
import com.example.myapplication001.Biz.Orderbiz;
import com.example.myapplication001.Biz.Productsbiz;
import com.example.myapplication001.Net.CommonCallBack;
import com.example.myapplication001.R;
import com.example.myapplication001.UI.Activity.Adapter.ProductListAdapter;
import com.example.myapplication001.UI.Activity.OrderAddlist;
import com.example.myapplication001.UI.Activity.ProductItem;
import com.example.myapplication001.UI.Activity.view.refresh.SwipeRefresh;
import com.example.myapplication001.UI.Activity.view.refresh.SwipeRefreshLayout;
import com.example.myapplication001.Utils.Toas;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ProductListActivity extends BaseActivity {
    private static final String TAG = "ProductListActivity";
    private SwipeRefreshLayout swipeRefreshLayout; //刷新布局
    private RecyclerView recyclerView;
    private TextView textView_count;     //订单数量
    private Button button_pay;          //支付按钮
    private OrderAddlist orderAddlist;
    public List<ProductItem> mdata=new ArrayList<>();  //加载的商品列表
    public List<OrderAddlist> morderList=new ArrayList<>();//订单详细
    private Productsbiz productsbiz=new Productsbiz(); //商品业务类对象
    private Orderbiz orderbiz=new Orderbiz();     //订单业务类对象
    public ProductListAdapter madapter ;  //商品类适配器
    private int mCurrentPage=1;          //商品列表当前页
    private int mTotalCount=0;           //总数量
    private Double mTotalgoods_price=0.00;  //总价格

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToorBar();
        setTitle("订单");
        initView();   //初始化视图
        loadData();  //加载第1页商品列表
        initEvent();
    }

    private void initEvent() {
    button_pay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mTotalCount==0){
                Toas.ShowToast("请选择商品~~");
                return;
            }
            startLodingProgress();
        orderbiz.order_add(morderList, new CommonCallBack<String>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onError(Exception e) {
            stopLodingProgress();
            }

            @Override
            public void onSuccess(String response) {
                stopLodingProgress();
              Toas.ShowToast("订单已提交~~");
              return;
            }
        });

        }
    });
    }

    private void initView() {
        swipeRefreshLayout=findViewById(R.id.product_refresh);
        recyclerView=findViewById(R.id.product_recycle);
        textView_count=findViewById(R.id.product_count);
        button_pay=findViewById(R.id.product_pay);
        //初始化Adapter
        madapter=new ProductListAdapter(ProductListActivity.this,mdata);
        //为recycleview添加布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(madapter);
        //设置开关
        swipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        //设置颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        //上拉刷新
        swipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            public void onPullUpRefresh() {
                loadMore();
            }
        });
        //适配器的接口调用
        madapter.setOnProductListener(new ProductListAdapter.OnproductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                //每次监听到增加按钮事件，mTotalCoutn++,更新UI
                mTotalCount++;
                textView_count.setText("数量："+mTotalCount);
                mTotalgoods_price=mTotalgoods_price+productItem.getgoods_price();
                button_pay.setText(mTotalgoods_price+"元 立即支付");
                for (  OrderAddlist p : morderList){
                    if(p.getGoods_name()==productItem.getgoods_name()){
                        p.setNumber(productItem.getCount());
                        return;
                        }
                }
                orderAddlist=new OrderAddlist();
                orderAddlist.setGoods_name(productItem.getgoods_name());
                orderAddlist.setNumber(1);
                morderList.add(orderAddlist);
            }
            @Override
            public void onProductSub(ProductItem productItem) {
                //每次监听到减少按钮事件，mTotalCoutn--,更新UI
                 mTotalCount--;
                textView_count.setText("数量："+mTotalCount);
                mTotalgoods_price=mTotalgoods_price-productItem.getgoods_price();
                button_pay.setText(mTotalgoods_price+"元 立即支付");
                //更新订单中的菜品数量
                for (  OrderAddlist p : morderList){
                    if(p.getGoods_name()==productItem.getgoods_name()){
                        p.setNumber(productItem.getCount());
                        //如果商品份数为0，就从订单中移除该商品
                        if(p.getNumber()==0){
                            morderList.remove(p);
                        }
                        return;
                    }
                }
            }
        });
    }
  //上拉刷新方法，加载当前商品页的下一页
    private void loadMore() {
        startLodingProgress();
       productsbiz.listByPage(++mCurrentPage, new CommonCallBack<List<Products>>() {
           @Override
           public void onError(Call call, Exception e, int id) {

           }

           @Override
           public void onError(Exception e) {
               Toas.ShowToast(e.getMessage());
               stopLodingProgress();
               mCurrentPage--;
               //如果还在刷新数据就关闭刷新
                   swipeRefreshLayout.setPullUpRefreshing(false);

           }

           @Override
           public void onSuccess(List<Products> response) {
           stopLodingProgress();
           swipeRefreshLayout.setPullUpRefreshing(false);
           if(response.size()==0){
               Toas.ShowToast("已经到底了~~");
               return;
           }
           for(Products p : response){
               mdata.add(new ProductItem(p));
           }
               //通知适配器数据已经发生改变
           madapter.notifyDataSetChanged();
           }
       });

    }
  //下拉刷新方法 ，回调商品第1页
    private void loadData() {
        startLodingProgress();
        //加载数据
        productsbiz.listByPage(1, new CommonCallBack<List<Products>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onError(Exception e) {
                Toas.ShowToast(e.getMessage());
                stopLodingProgress();
                //如果还在刷新数据就关闭刷新
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onSuccess(List<Products> response) {
              stopLodingProgress();
                //如果还在刷新数据就关闭刷新
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                 mdata.clear();
                mCurrentPage=1;
                textView_count.setText("数量：0");
                button_pay.setText("0元 立即支付");
                for(Products p : response){
                    mdata.add(new ProductItem(p));
                }
                //通知适配器数据已经发生改变
                madapter.notifyDataSetChanged();
            }
        });
    }
}
