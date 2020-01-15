package com.satish.hustletest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.satish.hustletest.Api.CartItemsAPIs;
import com.satish.hustletest.Api.CartService;
import com.satish.hustletest.Api.ContactWrapper;
import com.satish.hustletest.model.CartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AmountIntf {

    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;
    public TextView tvItems;
    public TextView tvTotalPrice;
    AmountIntf amountIntf;

    private CartItemsAPIs items;
    private final String TAG = "MainActivityLogs";
    private List<CartItem> list = new ArrayList<>();
    private CartListAdapter cartlistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        loadData();
        setupRecyclerView();

    }

    private void loadData() {
        items.getList().enqueue(new Callback<ContactWrapper>() {
            @Override
            public void onResponse(Call<ContactWrapper> call, Response<ContactWrapper> response) {
                list = response.body().getContacts();
                cartlistAdapter.notifyDataSetChanged();
                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<ContactWrapper> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }



    private void setupRecyclerView() {
        cartlistAdapter = new CartListAdapter(MainActivity.this, list, this);
        rvCartList.setLayoutManager(new LinearLayoutManager(this));
        rvCartList.setAdapter(cartlistAdapter);
        rvCartList.setItemAnimator(new DefaultItemAnimator());
        rvCartList.setNestedScrollingEnabled(true);
    }


    @Override
    public void setAmount(int price, int state, int totalItem) {
        int totalAmt = Integer.parseInt(tvTotalPrice.getText().toString());
        tvItems.setText(totalItem + " items");
        if (state == 0) {
            tvTotalPrice.setText(String.valueOf((totalAmt + price)));
        } else {
            tvTotalPrice.setText(String.valueOf((totalAmt - price)));

        }
    }



    private void initialization() {
        ButterKnife.bind(this);
        items = CartService.getServices().create(CartItemsAPIs.class);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.include_toolbar, null);
        tvTotalPrice = mCustomView.findViewById(R.id.tvTotalPrice);
        tvItems = mCustomView.findViewById(R.id.tvItems);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

}
