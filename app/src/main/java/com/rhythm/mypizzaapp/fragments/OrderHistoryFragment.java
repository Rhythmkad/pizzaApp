package com.rhythm.mypizzaapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhythm.mypizzaapp.CommonActivity;
import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.adapters.OrderHistoryAdapter;
import com.rhythm.mypizzaapp.getterAndSetterClasses.OrderHistoryBeanClass;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment implements RecyclerViewClickListener {

    private Context mContext;
    private RecyclerView mOrderHistoryRv;
    private ArrayList<OrderHistoryBeanClass> mOrderHistoryList;
    private OrderHistoryAdapter orderHistoryAdapter;
    private View emptyListIncludeLayout;
    private ImageView emptyBagIv;
    private TextView emptyOrderTv;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CommonActivity) mContext).setToolbarTitle(GlobalConstants.FRAGMENT.ORDER_HISTORY);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
    }

    /*
     * Initializing values
     * */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void initialize(View view) {
        mOrderHistoryRv = view.findViewById(R.id.order_history_rv);

        emptyListIncludeLayout = view.findViewById(R.id.empty_order_history_layout);
        emptyBagIv = emptyListIncludeLayout.findViewById(R.id.empty_bag_iv);
        emptyOrderTv = emptyListIncludeLayout.findViewById(R.id.empty_cart_tv);
        mOrderHistoryList = new ArrayList<>();
        // Adding order history list data
        mOrderHistoryList.add(new OrderHistoryBeanClass(getString(R.string.veg_peppy_paneer),getString(R.string.veg_peppy_paneer_details), R.drawable.veg_peppy_paneer,R.drawable.veg_label, "12", "Regular | Hand tossed", true));
        mOrderHistoryList.add(new OrderHistoryBeanClass(getString(R.string.chicken_pepper_barbecue),getString(R.string.chicken_pepper_barbecue_details), R.drawable.chicken_pepper_barbeque,R.drawable.non_veg_label, "21", "Regular | Cheese Burst | Extra Cheese",true));
        mOrderHistoryList.add(new OrderHistoryBeanClass(getString(R.string.non_veg_supreme),getString(R.string.non_veg_supreme_details), R.drawable.non_veg_supreme,R.drawable.non_veg_label,"17", "Medium | Hand tossed", true));
        mOrderHistoryList.add(new OrderHistoryBeanClass(getString(R.string.new_fresh_veggie),getString(R.string.new_fresh_veggie_details), R.drawable.new_fresh_veggie,R.drawable.veg_label, "17", "Medium | Hand tossed", true));

        updateList(mOrderHistoryList);
    }

    @Override
    public void itemClickListener(View view, int position) {
        mOrderHistoryList.remove(position);
        orderHistoryAdapter.notifyItemRemoved(position);
        orderHistoryAdapter.notifyItemChanged(position, mOrderHistoryList.size());
        updateList(mOrderHistoryList);
    }

    private void updateList(ArrayList<OrderHistoryBeanClass> mOrderHistoryList) {
        if (!mOrderHistoryList.isEmpty()){
            mOrderHistoryRv.setVisibility(View.VISIBLE);
            emptyListIncludeLayout.setVisibility(View.INVISIBLE);
            orderHistoryAdapter = new OrderHistoryAdapter(mContext, mOrderHistoryList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,true);
            linearLayoutManager.setStackFromEnd(true);
            mOrderHistoryRv.setLayoutManager(linearLayoutManager);
            mOrderHistoryRv.setAdapter(orderHistoryAdapter);
        }else{
            mOrderHistoryRv.setVisibility(View.INVISIBLE);
            emptyListIncludeLayout.setVisibility(View.VISIBLE);
            emptyBagIv.setImageResource(R.drawable.pizza_box_stack);
            emptyOrderTv.setText("No past orders to show");
            emptyOrderTv.setTextSize(20);
        }
    }
}