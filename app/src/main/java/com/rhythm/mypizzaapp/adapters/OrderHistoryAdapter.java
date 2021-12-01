package com.rhythm.mypizzaapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.getterAndSetterClasses.OrderHistoryBeanClass;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> implements View.OnClickListener  {

    private final Context mContext;
    private final ArrayList<OrderHistoryBeanClass> mOrderList;
    private RecyclerViewClickListener mRecyclerClickListener;

    public OrderHistoryAdapter(Context mContext, ArrayList<OrderHistoryBeanClass> pizzaList, RecyclerViewClickListener recyclerViewClickListener) {
        this.mContext = mContext;
        mOrderList = pizzaList;
        mRecyclerClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mPizzaNameIv.setImageResource(mOrderList.get(position).getPizzaIv());
        viewHolder.mPizzaLabelIv.setImageResource(mOrderList.get(position).getPizzaLabelTagIv());
        viewHolder.mPizzaNameTv.setText(mOrderList.get(position).getPizzaName());
        viewHolder.mPizzaDetailTv.setText(mOrderList.get(position).getPizzaDetails());
        viewHolder.mSizeAndCrustTv.setText(mOrderList.get(position).getPizzaCustomizeItems());

        if (mOrderList.get(position).getPizzaOrderStatus()){
            viewHolder.mOrderCompleteTv.setVisibility(View.VISIBLE);
            viewHolder.mOrderCompleteTv.setText(R.string.order_completed);
            viewHolder.mDeleteOrderTv.setText(R.string.delete_order);
            viewHolder.mPizzaPriceTv.setText("$ " + mOrderList.get(position).getPizzaPriceTotal());
        }else{
            viewHolder.mOrderCompleteTv.setVisibility(View.VISIBLE);
            viewHolder.mOrderCompleteTv.setText(R.string.order_on_its_way_txt);
            viewHolder.mDeleteOrderTv.setText(R.string.cancel_order_txt);
            viewHolder.mPizzaPriceTv.setText(mOrderList.get(position).getPizzaPriceTotal());
        }

        viewHolder.mDeleteOrderTv.setTag(viewHolder.getAdapterPosition());
        viewHolder.mDeleteOrderTv.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete_order_tv:
                int position = (int) view.getTag();
                mRecyclerClickListener.itemClickListener(view,position);
                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mPizzaNameIv, mPizzaLabelIv;
        private final TextView mPizzaNameTv, mPizzaDetailTv, mSizeAndCrustTv, mOrderCompleteTv, mDeleteOrderTv, mPizzaPriceTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPizzaNameIv = itemView.findViewById(R.id.pizza_order_iv);
            mPizzaLabelIv = itemView.findViewById(R.id.pizza_veg_non_veg_label_iv);
            mPizzaNameTv = itemView.findViewById(R.id.pizza_order_name_tv);
            mPizzaDetailTv = itemView.findViewById(R.id.pizza_order_detail_tv);
            mSizeAndCrustTv = itemView.findViewById(R.id.crust_size_order_tv);
            mOrderCompleteTv = itemView.findViewById(R.id.order_complete_tv);
            mDeleteOrderTv = itemView.findViewById(R.id.delete_order_tv);
            mPizzaPriceTv = itemView.findViewById(R.id.price_tv_cart);
        }
    }
}
