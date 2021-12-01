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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rhythm.mypizzaapp.CommonActivity;
import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.adapters.OrderHistoryAdapter;
import com.rhythm.mypizzaapp.getterAndSetterClasses.OrderHistoryBeanClass;
import com.rhythm.mypizzaapp.utils.AlertDialogClass;
import com.rhythm.mypizzaapp.utils.AlertDialogInterface;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MyCartFragment extends Fragment implements View.OnClickListener, RecyclerViewClickListener, AlertDialogInterface {

    private Context mContext;
    private RecyclerView mMyCartRv;
    private Button mSubmitOrderBtn;
//    private ArrayList<OrderHistoryBeanClass> mMyOrdersList;
    private View emptyListIncludeLayout;
    private OrderHistoryAdapter myOrdersAdapter;
    private ImageView emptyBagIv;
    private TextView emptyOrderTv;
    private Dialog mDialog;
    private AlertDialogClass mAlertDialog;
    private ArrayList<OrderHistoryBeanClass> mCompleteOrderList;
    private RelativeLayout subTotalLayout;

    public MyCartFragment() {
        // Required empty public constructor
    }

    public MyCartFragment(ArrayList<OrderHistoryBeanClass> completeOrder) {
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
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CommonActivity)mContext).setToolbarTitle(GlobalConstants.FRAGMENT.MY_CART_FRAGMENT);
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

        emptyListIncludeLayout = view.findViewById(R.id.empty_list_layout);
        emptyBagIv = emptyListIncludeLayout.findViewById(R.id.empty_bag_iv);
        emptyOrderTv = emptyListIncludeLayout.findViewById(R.id.empty_cart_tv);
        subTotalLayout = view.findViewById(R.id.relativeLayout);
        // adding GIF image through Glide dependency
        Glide.with(this).load(R.drawable.empty_bag_gif)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(emptyBagIv);
        mAlertDialog = new AlertDialogClass();
        mMyCartRv = view.findViewById(R.id.my_cart_rv);
        mSubmitOrderBtn = view.findViewById(R.id.submit_order_btn);
        mSubmitOrderBtn.setOnClickListener(this);
        // Adding order history list data
//        mMyOrdersList.add(new OrderHistoryBeanClass(getString(R.string.veg_peppy_paneer),getString(R.string.veg_peppy_paneer_details), R.drawable.veg_peppy_paneer,R.drawable.veg_label, "12", "Regular | Hand tossed", false));
//        mMyOrdersList.add(new OrderHistoryBeanClass(getString(R.string.chicken_pepper_barbecue),getString(R.string.chicken_pepper_barbecue_details), R.drawable.chicken_pepper_barbeque,R.drawable.non_veg_label, "21", "Regular | Cheese Burst | Extra Cheese",false));
        updateList(mCompleteOrderList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_order_btn:
                Intent intent = new Intent((CommonActivity) mContext, MainActivity.class);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, GlobalConstants.FRAGMENT.MENU);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.MENU);
                Bundle listBundleToTransfer = new Bundle();
                listBundleToTransfer.putSerializable(GlobalConstants.COMPLETE_ORDER_DATA_FOR_MY_ORDERS,(Serializable) mCompleteOrderList);
                intent.putExtra(GlobalConstants.MENU_BUNDLE_DATA_FOR_MY_ORDERS,listBundleToTransfer);
                startActivity(intent);
                ((CommonActivity) mContext).finish();
                Toast.makeText(mContext, "Order placed successfully", Toast.LENGTH_SHORT).show();
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
            mMyCartRv.setVisibility(View.VISIBLE);
            subTotalLayout.setVisibility(View.VISIBLE);
            emptyListIncludeLayout.setVisibility(View.INVISIBLE);
            myOrdersAdapter = new OrderHistoryAdapter(mContext, myOrdersList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,true);
            linearLayoutManager.setStackFromEnd(true);
            mMyCartRv.setLayoutManager(linearLayoutManager);
            mMyCartRv.setAdapter(myOrdersAdapter);
        }else{
            mMyCartRv.setVisibility(View.INVISIBLE);
            subTotalLayout.setVisibility(View.INVISIBLE);
            emptyListIncludeLayout.setVisibility(View.VISIBLE);
//            emptyBagIv.setImageResource(R.drawable.pizza_delivery_boy);
            emptyOrderTv.setText("Your Cart is Empty");
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