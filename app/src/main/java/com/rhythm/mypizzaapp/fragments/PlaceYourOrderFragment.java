package com.rhythm.mypizzaapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rhythm.mypizzaapp.CommonActivity;
import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.getterAndSetterClasses.OrderHistoryBeanClass;
import com.rhythm.mypizzaapp.getterAndSetterClasses.PizzaMenuBeanClass;
import com.rhythm.mypizzaapp.utils.AlertDialogClass;
import com.rhythm.mypizzaapp.utils.AlertDialogInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class PlaceYourOrderFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AlertDialogInterface {

    private Context mContext;
    private ArrayList<PizzaMenuBeanClass> mPizzaDetailDataList;
    private Dialog mDialog;
    private ImageView mPizzaImage, mPizzaTypeTagIv;
    private TextView mPizzaNameTv, mPizzaDetailTv, mNumberOfPizzaTv, mPizzaPriceTv;
    private RadioGroup mRadioGroupPizzaSize, mRadioGroupPizzaCrust;
    private CheckBox mCheeseCheckBox;
    private String pizzaSize = "", pizzaCrust = "";
    private int mTotalAmount;
    private int pizzaImage, pizzaLabel;
    private ArrayList<OrderHistoryBeanClass> mPizzaToOrderDetails;
    private RadioButton sizeRadioBtn;
    private RadioButton crustRadioBtn;

    public PlaceYourOrderFragment() {
        // Required empty public constructor
    }

    public PlaceYourOrderFragment(ArrayList<PizzaMenuBeanClass> pizzaDetailDataList) {
        // Required empty public constructor
        mPizzaDetailDataList = pizzaDetailDataList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_your_order, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // onDestroy we will dismiss the dialog and set it to null.
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CommonActivity) mContext).setToolbarTitle(GlobalConstants.FRAGMENT.PLACE_ORDER_FRAGMENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
    }

    private void initialize(View view) {
        mPizzaNameTv = view.findViewById(R.id.pizza_name_tv);
        mPizzaDetailTv = view.findViewById(R.id.pizza_description_tv);
        mNumberOfPizzaTv = view.findViewById(R.id.persons_value_tv);
        mPizzaPriceTv = view.findViewById(R.id.pizza_price);
        mPizzaImage = view.findViewById(R.id.pizza_iv);
        mPizzaTypeTagIv = view.findViewById(R.id.veg_non_veg_label_tag_iv);
        Button placeOrderBtn = view.findViewById(R.id.place_orderBtn);
        Button mIncreasePerson = view.findViewById(R.id.plus_btn);
        Button mDecreasePerson = view.findViewById(R.id.minus_btn);
        mRadioGroupPizzaSize = view.findViewById(R.id.pizza_base_size_radio_group);
        mRadioGroupPizzaCrust = view.findViewById(R.id.pizza_crust_radio_group);
        sizeRadioBtn = view.findViewById(mRadioGroupPizzaSize.getCheckedRadioButtonId());
        crustRadioBtn = view.findViewById(mRadioGroupPizzaCrust.getCheckedRadioButtonId());
        mCheeseCheckBox = view.findViewById(R.id.extra_cheese_checkBox);

        String amount = mPizzaPriceTv.getText().toString().substring((mPizzaPriceTv.getText().toString().indexOf(" ")) + 1);
        mTotalAmount = Integer.parseInt(amount);
        if (mPizzaDetailDataList != null){
            for (PizzaMenuBeanClass i : mPizzaDetailDataList){
                mPizzaNameTv.setText(i.getPizzaName());
                mPizzaDetailTv.setText(i.getPizzaDetails());
                mPizzaImage.setImageResource(i.getPizzaIv());
                mPizzaTypeTagIv.setImageResource(i.getPizzaLabelTagIv());
                pizzaImage = i.getPizzaIv();
                pizzaLabel = i.getPizzaLabelTagIv();
            }
        }

        mRadioGroupPizzaSize.setOnCheckedChangeListener(this);
        mRadioGroupPizzaCrust.setOnCheckedChangeListener(this);

        placeOrderBtn.setOnClickListener(this);
        mIncreasePerson.setOnClickListener(this);
        mDecreasePerson.setOnClickListener(this);

        mCheeseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheeseCheckBox.isChecked()){
                    mTotalAmount += 1;
                }
                else if (!mCheeseCheckBox.isChecked()){
                    mTotalAmount -= 1 ;
                }
                updatePizzaAmount(mTotalAmount);
            }
        });
    }

    @Override
    public void onClick(View view) {
        AlertDialogClass alertDialogClass = new AlertDialogClass();
        int persons;
        switch (view.getId()) {
            case R.id.place_orderBtn:
                mDialog = alertDialogClass.showAlertDialog(mContext, "Order Confirmation", "Place order to your cart \nPizza name: "+ mPizzaNameTv.getText().toString() + "\nQuantity: " + mNumberOfPizzaTv.getText().toString() + "\nTotal: "+ mPizzaPriceTv.getText().toString(), this, false, "Continue", "Cancel", 0);
                if (mDialog != null) {
                    mDialog.show();
                }
                setPizzaToOrderDetails();
            break;
            case R.id.plus_btn:
                persons = Integer.parseInt(mNumberOfPizzaTv.getText().toString()) + 1;
                mNumberOfPizzaTv.setText(String.valueOf(persons));
//                int subTotal = mTotalAmount * persons;
                updatePizzaAmount(mTotalAmount);
            break;
            case R.id.minus_btn:
                persons = Integer.parseInt(mNumberOfPizzaTv.getText().toString());
                if (persons > 1){
                    persons -= 1;
                    mNumberOfPizzaTv.setText(String.valueOf(persons));
//                    subTotal = mTotalAmount * persons;
                    updatePizzaAmount(mTotalAmount);
                }
            break;
        }
    }

    private void setPizzaToOrderDetails() {
        String pizzaName, pizzaDescription, pizzaCustomizeItems, pizzaQuantity, extraCheese,
                totalAmount, pizzaSize, PizzaCrust;
        mPizzaToOrderDetails = new ArrayList<>();
        pizzaName = mPizzaNameTv.getText().toString();
        pizzaDescription = mPizzaDetailTv.getText().toString();
        totalAmount = mPizzaPriceTv.getText().toString();
        pizzaQuantity = mNumberOfPizzaTv.getText().toString();
        if (mCheeseCheckBox.isChecked()){
            extraCheese = "Extra cheese";
        }else{
            extraCheese = "";
        }

        pizzaSize = sizeRadioBtn.getText().toString().substring(0,sizeRadioBtn.getText().toString().indexOf(" "));
        PizzaCrust = crustRadioBtn.getText().toString().substring(0,crustRadioBtn.getText().toString().indexOf("("));
        StringBuilder stringBuilder = new StringBuilder();

        if (extraCheese.isEmpty()){
            pizzaCustomizeItems = String.valueOf(stringBuilder.append(pizzaSize).append(" | ").append(PizzaCrust));
        }else {
            pizzaCustomizeItems = String.valueOf(stringBuilder.append(pizzaSize).append(" | ").append(PizzaCrust).append(" | ").append(extraCheese));
        }

        mPizzaToOrderDetails.add(new OrderHistoryBeanClass(pizzaName, pizzaDescription, pizzaImage, pizzaLabel, totalAmount, pizzaCustomizeItems, false));
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (radioGroup.getId()){
            case R.id.pizza_base_size_radio_group:
                sizeRadioBtn = radioGroup.findViewById(id);
                if (pizzaCrust.isEmpty()){
                    pizzaCrust = "Hand Tossed ($ 2)";
                }
                pizzaSize = sizeRadioBtn.getText().toString();
                break;
            case R.id.pizza_crust_radio_group:
                crustRadioBtn = radioGroup.findViewById(id);
                if (pizzaSize.isEmpty()){
                    pizzaSize = "Regular ($ 10)";
                }
                pizzaCrust = crustRadioBtn.getText().toString();
                break;
        }
        getPizzaSizeValue(pizzaSize, pizzaCrust);

    }

    private void getPizzaSizeValue(String size, String crust){
        switch (size){
            case "Regular ($ 10)":
                mTotalAmount = 10;
               break;
            case "Medium ($ 15)":
                mTotalAmount = 15;
                break;
            case "Large ($ 20)":
                mTotalAmount = 20;
                break;
        }
                getPizzaCrustValue(crust);
    }

    private void getPizzaCrustValue(String crust){
        switch (crust){
            case "Hand Tossed ($ 2)":
                mTotalAmount += 2;
                break;
            case "Cheese Burst ($ 8)":
                mTotalAmount += 8;
                break;
            case "Fresh Pan Pizza ($ 5)":
                mTotalAmount += 5;
                break;
        }
        if (mCheeseCheckBox.isChecked()){
            mTotalAmount += 1 ;
        }
        updatePizzaAmount(mTotalAmount);

    }

    private void updatePizzaAmount(int totalAmount) {
        totalAmount = totalAmount * Integer.parseInt(mNumberOfPizzaTv.getText().toString());
        mPizzaPriceTv.setText("$ " + totalAmount);
    }

    @Override
    public void onDialogConfirmAction(int position) {
        if (mDialog != null) {
            mDialog.dismiss();
            // here when order is placed dialog will show
            // and when user click on confirm button, we will go to the menu screen in MainActivity.
            // with pizza's detail list that includes pizzaName, pizzaDescription, toppings, price etc.
            Intent intent = new Intent((CommonActivity) mContext, MainActivity.class);
            intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, GlobalConstants.FRAGMENT.MENU);
            intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.MENU);
            Bundle listBundleToTransfer = new Bundle();
            listBundleToTransfer.putSerializable(GlobalConstants.COMPLETE_ORDER_DATA_FOR_CART,(Serializable) mPizzaToOrderDetails);
            intent.putExtra(GlobalConstants.MENU_BUNDLE_DATA_FOR_CART,listBundleToTransfer);
            startActivity(intent);
            ((CommonActivity) mContext).finish();
//            ((CommonActivity) mContext).callFragments(GlobalConstants.FRAGMENT.MY_CART_FRAGMENT, GlobalConstants.FRAGMENT.MY_CART_FRAGMENT, 0,false, mPizzaToOrderDetails );

            Toast.makeText(mContext, "Order added to cart.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogCancelAction(int position) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}