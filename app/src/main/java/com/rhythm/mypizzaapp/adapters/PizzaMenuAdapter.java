package com.rhythm.mypizzaapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.getterAndSetterClasses.PizzaMenuBeanClass;
import com.rhythm.mypizzaapp.utils.RecyclerViewClickListener;

import java.util.ArrayList;

public class PizzaMenuAdapter extends RecyclerView.Adapter<PizzaMenuAdapter.ViewHolder> implements View.OnClickListener  {

    private Context mContext;
    private ArrayList<PizzaMenuBeanClass> mPizzaList;
    private RecyclerViewClickListener mRecyclerViewClickListener;

    public PizzaMenuAdapter(Context mContext, ArrayList<PizzaMenuBeanClass> pizzaList, RecyclerViewClickListener recyclerViewClickListener) {
        this.mContext = mContext;
        mPizzaList = pizzaList;
        mRecyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pizza_menu_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.mPizzaNameIv.setImageResource(mPizzaList.get(position).getPizzaIv());
        viewHolder.mPizzaLabelIv.setImageResource(mPizzaList.get(position).getPizzaLabelTagIv());
        viewHolder.mPizzaNameTv.setText(mPizzaList.get(position).getPizzaName());
        viewHolder.mPizzaDetailTv.setText(mPizzaList.get(position).getPizzaDetails());

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewClickListener.itemClickListener(view,viewHolder.getAdapterPosition());
//                Toast.makeText(mContext, mPizzaList.get(viewHolder.getAdapterPosition()).getPizzaName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPizzaList.size();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.pizza_menu_parent_layout:
//                Toast.makeText(mContext, mPizzaList.get((Integer) v.getTag()).getPizzaName(), Toast.LENGTH_SHORT).show();
////                int position = (int)v.getTag();
////                mRecyclerViewClickListeners.itemClickListener(v,position);
////                break;
//        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPizzaNameIv, mPizzaLabelIv;
        private TextView mPizzaNameTv, mPizzaDetailTv;
        private ConstraintLayout mParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPizzaNameIv = itemView.findViewById(R.id.pizza_iv);
            mPizzaLabelIv = itemView.findViewById(R.id.veg_non_veg_label_tag_iv);
            mPizzaNameTv = itemView.findViewById(R.id.pizza_name_tv);
            mPizzaDetailTv = itemView.findViewById(R.id.pizza_description_tv);
            mParentLayout = itemView.findViewById(R.id.pizza_menu_parent_layout);
        }
    }
}
