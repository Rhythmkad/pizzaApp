package com.rhythm.mypizzaapp.fragments;

import android.annotation.SuppressLint;
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

import com.rhythm.mypizzaapp.CommonActivity;
import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.adapters.PizzaMenuAdapter;
import com.rhythm.mypizzaapp.getterAndSetterClasses.PizzaMenuBeanClass;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuFragment extends Fragment implements RecyclerViewClickListener {

    private Context mContext;
    private RecyclerView mPizzaMenuRv;
    private ArrayList<PizzaMenuBeanClass> mPizzaList;

    public MenuFragment() {
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
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setToolbarTitle(GlobalConstants.FRAGMENT.MENU);
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
        mPizzaMenuRv = view.findViewById(R.id.menu_recyclerView);
        mPizzaList = new ArrayList<>();
        // Adding list data
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.veggie_paradise),getString(R.string.veggie_paradise_details), R.drawable.veggie_paradise,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.veg_peppy_paneer),getString(R.string.veg_peppy_paneer_details), R.drawable.veg_peppy_paneer,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.chicken_pepper_barbecue),getString(R.string.chicken_pepper_barbecue_details), R.drawable.chicken_pepper_barbeque,R.drawable.non_veg_label ));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.non_veg_supreme),getString(R.string.non_veg_supreme_details), R.drawable.non_veg_supreme,R.drawable.non_veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.new_margherita_veg),getString(R.string.new_margherita_veg_details), R.drawable.new_margherita_veg,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.new_fresh_veggie),getString(R.string.new_fresh_veggie_details), R.drawable.new_fresh_veggie,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.chicken_sausage),getString(R.string.chicken_sausage_details), R.drawable.chicken_sausage,R.drawable.non_veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.chicken_golden_delight),getString(R.string.chicken_golden_delight_details), R.drawable.chicken_golden_delight,R.drawable.non_veg_label ));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.chicken_pepperoni),getString(R.string.chicken_pepperoni_details), R.drawable.chicken_pepperoni,R.drawable.non_veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.indi_tandoori_paneer),getString(R.string.indi_tandoori_paneer_details), R.drawable.indi_tandoori_paneer,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.farmhouse),getString(R.string.farmhouse_details), R.drawable.farmhouse,R.drawable.veg_label));
        mPizzaList.add(new PizzaMenuBeanClass(getString(R.string.mexican_green_wave),getString(R.string.mexican_green_wave_details), R.drawable.mexican_green_wave,R.drawable.veg_label));
        PizzaMenuAdapter adapter = new PizzaMenuAdapter(mContext, mPizzaList, this);
        mPizzaMenuRv.setLayoutManager(new LinearLayoutManager(mContext));
        mPizzaMenuRv.setAdapter(adapter);
    }

    @Override
    public void itemClickListener(View view, int position) {
        ArrayList<PizzaMenuBeanClass> selectedItemData = new ArrayList<>();
        if (mPizzaList != null) {
            selectedItemData.add(new PizzaMenuBeanClass(mPizzaList.get(position).getPizzaName(),mPizzaList.get(position).getPizzaDetails(), mPizzaList.get(position).getPizzaIv(), mPizzaList.get(position).getPizzaLabelTagIv()));
        }
        Intent intent = new Intent(mContext, CommonActivity.class);
        intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.PLACE_ORDER_FRAGMENT);
        intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, GlobalConstants.FRAGMENT.PLACE_ORDER_FRAGMENT);
        Bundle listBundleToTransfer = new Bundle();
        listBundleToTransfer.putSerializable(GlobalConstants.PIZZA_DESCRIPTION,(Serializable) selectedItemData);
        intent.putExtra(GlobalConstants.MENU_BUNDLE_DATA_FOR_PLACE_ORDER,listBundleToTransfer);
        startActivity(intent);
    }
}