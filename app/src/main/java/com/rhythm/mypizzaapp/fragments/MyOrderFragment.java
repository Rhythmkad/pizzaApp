package com.rhythm.mypizzaapp.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.adapters.OrderHistoryAdapter;
import com.rhythm.mypizzaapp.getterAndSetterClasses.OrderHistoryBeanClass;
import com.rhythm.mypizzaapp.utils.AlertDialogClass;
import com.rhythm.mypizzaapp.utils.AlertDialogInterface;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.util.ArrayList;

public class MyOrderFragment extends Fragment implements View.OnClickListener, RecyclerViewClickListener, AlertDialogInterface {

    private Context mContext;
    private RecyclerView mMyOrdersRv;
    private Button mViewPastOrderBtn;
    private ArrayList<OrderHistoryBeanClass> mMyOrdersList;
    private View emptyListIncludeLayout;
    private OrderHistoryAdapter myOrdersAdapter;
    private ImageView emptyBagIv;
    private TextView emptyOrderTv;
    private Dialog mDialog;
    private AlertDialogClass mAlertDialog;
    private ArrayList<OrderHistoryBeanClass> mCompleteOrderList;

    public MyOrderFragment() {
        // Required empty public constructor
    }
    public MyOrderFragment(ArrayList<OrderHistoryBeanClass> completeOrder) {
        // Required empty public constructor
        mCompleteOrderList = completeOrder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setToolbarTitle(GlobalConstants.FRAGMENT.MY_ORDERS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
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
        mAlertDialog = new AlertDialogClass();
        mMyOrdersRv = view.findViewById(R.id.my_order_rv);
        mViewPastOrderBtn = view.findViewById(R.id.past_order_btn);
        mViewPastOrderBtn.setOnClickListener(this);
        emptyListIncludeLayout = view.findViewById(R.id.empty_orders_include_layout);
        emptyBagIv = emptyListIncludeLayout.findViewById(R.id.empty_bag_iv);
        emptyOrderTv = emptyListIncludeLayout.findViewById(R.id.empty_cart_tv);
        // Adding order history list data
//        mMyOrdersList.add(new OrderHistoryBeanClass(getString(R.string.veg_peppy_paneer),getString(R.string.veg_peppy_paneer_details), R.drawable.veg_peppy_paneer,R.drawable.veg_label, "12", "Regular | Hand tossed", false));
//        mMyOrdersList.add(new OrderHistoryBeanClass(getString(R.string.chicken_pepper_barbecue),getString(R.string.chicken_pepper_barbecue_details), R.drawable.chicken_pepper_barbeque,R.drawable.non_veg_label, "21", "Regular | Cheese Burst | Extra Cheese",false));
        updateList(mCompleteOrderList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.past_order_btn:
                Intent intent = new Intent((MainActivity)mContext, CommonActivity.class);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, GlobalConstants.FRAGMENT.ORDER_HISTORY);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.ORDER_HISTORY);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void itemClickListener(View view, int position) {
        mDialog = mAlertDialog.showAlertDialog(mContext, "Order Cancel Confirmation","Are you sure you want to cancel your order?",this, false, "Yes", "No", position);
        if (mDialog != null){
            mDialog.show();
        }
    }

    private void updateList(ArrayList<OrderHistoryBeanClass> myOrdersList) {
        if (myOrdersList != null && myOrdersList.size() > 0){
            mMyOrdersRv.setVisibility(View.VISIBLE);
            emptyListIncludeLayout.setVisibility(View.INVISIBLE);
            myOrdersAdapter = new OrderHistoryAdapter(mContext, myOrdersList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,true);
            linearLayoutManager.setStackFromEnd(true);
            mMyOrdersRv.setLayoutManager(linearLayoutManager);
            mMyOrdersRv.setAdapter(myOrdersAdapter);
        }else{
            mMyOrdersRv.setVisibility(View.INVISIBLE);
            emptyListIncludeLayout.setVisibility(View.VISIBLE);
            emptyBagIv.setImageResource(R.drawable.pizza_delivery_boy);
            emptyOrderTv.setText("No Orders to deliver");
            emptyOrderTv.setTextSize(20);
        }
    }

    @Override
    public void onDialogConfirmAction(int position) {
        mCompleteOrderList.remove(position);
        myOrdersAdapter.notifyItemRemoved(position);
        myOrdersAdapter.notifyItemChanged(position, mCompleteOrderList.size());
        updateList(mCompleteOrderList);
    }

    @Override
    public void onDialogCancelAction(int position) {
        if (mDialog != null){
            mDialog.dismiss();
        }
    }
}