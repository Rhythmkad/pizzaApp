package com.rhythm.mypizzaapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.utils.TypefaceUtil;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    protected RelativeLayout mPrivacyPolicyLayout, mTermsAndConditionLayout;
    protected Context mContext;
    private Switch mNotificationSwitch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)mContext).setToolbarTitle(GlobalConstants.FRAGMENT.SETTINGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initialize(View view) {

        mNotificationSwitch = view.findViewById(R.id.notification_switch_btn);
        mPrivacyPolicyLayout = view.findViewById(R.id.privacy_policy_layout);
        mTermsAndConditionLayout = view.findViewById(R.id.terms_condition_layout);

        /* click listeners */
        setClickListeners();
    }

    private void setClickListeners() {
        mPrivacyPolicyLayout.setOnClickListener(this);
        mTermsAndConditionLayout.setOnClickListener(this);
    }

    /*
     * OnClickListener method
     * */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.privacy_policy_layout:
                Toast.makeText(mContext, "Privacy Policies", Toast.LENGTH_SHORT).show();
//                intent = new Intent(mContext, CommonActivity.class);
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, mContext.getResources().getString(R.string.terms_conditions));
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.AGREEMENT);
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.ONLINE_DOCUMENT_TYPE, mPrivacyPolicyTv.getText().toString());  // sending which policy is clicked on screen
//                mContext.startActivity(intent);
                break;
            case R.id.terms_condition_layout:
                Toast.makeText(mContext, "Terms and Conditions", Toast.LENGTH_SHORT).show();

//                intent = new Intent(mContext, CommonActivity.class);
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, mContext.getResources().getString(R.string.privacy_policy_txt));
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.AGREEMENT);
//                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.ONLINE_DOCUMENT_TYPE, mTermsAndConditionTv.getText().toString());  // sending which policy is clicked on screen
//                mContext.startActivity(intent);
                break;
        }
    }
}