package com.satish.hustletest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.satish.hustletest.model.CartItem;
import java.util.List;



public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {

    private Activity activity;
    private List<CartItem> cartList;
    private AmountIntf amountIntf;
    private int totalItem =0;

    CartListAdapter(Activity activity, List<CartItem> cartList, AmountIntf amountIntf) {
        this.activity = activity;
        this.cartList = cartList;
        this.amountIntf = amountIntf;
    }

    @NonNull
    @Override
    public CartListAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_cart_items, parent, false);
        return new CartViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.CartViewHolder holder, int position) {
        CartItem mCartList = cartList.get(position);
        holder.tvItemName.setText(mCartList.getItemName());
        holder.tvQuantity.setText(mCartList.getQuantity().concat(" " + mCartList.getQuantityUnit()));
        holder.tvPrice.setText(mCartList.getPrice());

        Glide.with(activity)
                .load(mCartList.getItemUrl())
                .fitCenter()
                .into(holder.ivItem);

        holder.ibPlus.setOnClickListener(view ->{
                    increment(holder, Integer.parseInt(mCartList.getPrice()));

        });
//
        holder.ibMinus.setOnClickListener(view ->{
            decrement(holder, Integer.parseInt(mCartList.getPrice()));
        });

    }

    private void increment(CartViewHolder holder,int price) {

        int count = Integer.parseInt(holder.tvCount.getText().toString());
        if(count==0){
            totalItem = totalItem+1;
        }
        holder.tvCount.setText(String.valueOf(count + 1));
        amountIntf.setAmount(price,0,totalItem);

    }

    private void decrement(CartViewHolder holder, int price) {
        int count = Integer.parseInt(holder.tvCount.getText().toString());

        if (count != 0) {
            if(count==1){
                totalItem = totalItem-1;
            }
            holder.tvCount.setText(String.valueOf(count - 1));
            amountIntf.setAmount(price, 1,totalItem);

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName, tvPrice, tvCount, tvQuantity;
        ImageView ivItem;
        ImageButton ibPlus, ibMinus;
        View mItemView;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            tvCount = itemView.findViewById(R.id.tvCount);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            ibPlus = itemView.findViewById(R.id.ibPlus);
            ibMinus = itemView.findViewById(R.id.ibMinus);
            ivItem = itemView.findViewById(R.id.ivItem);
        }
    }
}
