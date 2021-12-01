package com.rhythm.mypizzaapp.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rhythm.mypizzaapp.AuthenticationActivity;
import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.MainActivity;
import com.rhythm.mypizzaapp.R;
import com.rhythm.mypizzaapp.utils.ValidationsClass;

public class MyProfileFragment extends Fragment{

    private ImageView mUserProfileIv, mPhoneIcon, mAddressIcon;
    private TextView mUserNameTv, mPhoneNumberTv, mEmailTv, mAddressTv;
    private Context mContext;
    private Dialog mDialog;

    public MyProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_my_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setToolbarTitle(GlobalConstants.FRAGMENT.MY_PROFILE);
    }


    /*
     * Initializing id's
     * */
    private void initialize(View view) {

        View includePhoneLayout = view.findViewById(R.id.phone_layout);
        View includeEmailLayout = view.findViewById(R.id.email_layout);
        View includeAddressLayout = view.findViewById(R.id.address_layout);

        /* editText */
        mPhoneNumberTv = includePhoneLayout.findViewById(R.id.aboutTv);
        mEmailTv = includeEmailLayout.findViewById(R.id.aboutTv);
        mAddressTv = includeAddressLayout.findViewById(R.id.aboutTv);

        /* textView */
        mUserNameTv = view.findViewById(R.id.user_name_tv);

        /* imageView */
        mUserProfileIv = view.findViewById(R.id.user_profile_iv);
        mPhoneIcon = includePhoneLayout.findViewById(R.id.aboutIv);
        mAddressIcon = includeAddressLayout.findViewById(R.id.aboutIv);

        /* runtime changes */
        setRuntimeChanges();
    }

    /*
     * Run time changes
     * */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void setRuntimeChanges() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(GlobalConstants.SHARED_PREFERENCE.SHARED_PREFERENCE_KEY,Context.MODE_PRIVATE);
        String gender = "";
        if (sharedPreferences != null){
            String userName = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCE.KEY_NAME,"");
            String userEmail = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCE.KEY_EMAIL,"");
            String userAddress = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCE.KEY_ADDRESS,"");
            String userPhone = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCE.KEY_PHONENUMBER,"");
            gender = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCE.KEY_GENDER,"");

            mPhoneNumberTv.setText(getEditedPhoneNumber(userPhone));
            mEmailTv.setText(userEmail);
            mAddressTv.setText(firstLetterCapsInWholeSentence(userAddress));
            mUserNameTv.setText(firstLetterCapsInWholeSentence(userName));
        }else{
            mPhoneNumberTv.setText(getEditedPhoneNumber(mContext.getResources().getString(R.string.dummy_phone_number)));
            mEmailTv.setText(mContext.getResources().getString(R.string.dummy_email));
            mAddressTv.setText(firstLetterCapsInWholeSentence(mContext.getResources().getString(R.string.dummy_address)));
            mUserNameTv.setText(firstLetterCapsInWholeSentence(mContext.getResources().getString(R.string.dummy_name)));
        }
        mPhoneNumberTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        mEmailTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        mAddressTv.setTextColor(ContextCompat.getColor(mContext, R.color.black));

        mPhoneIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_phone));
        mAddressIcon.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_location));

        mAddressTv.setMaxLines(2);

        /*
         * setting profile image
         * */
        if (gender.equalsIgnoreCase("Male")) {
            Glide.with(this)
                    .load(R.drawable.male_avatar)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_my_profile_white)
                            .circleCrop())
                    .into(mUserProfileIv);
        }else{
            Glide.with(this)
                    .load(R.drawable.female_avatar)
                    .apply(new RequestOptions().placeholder(R.drawable.ic_my_profile_white)
                            .circleCrop())
                    .into(mUserProfileIv);
        }
    }


    /* return "9111144444" to "91-111-44444" */
    private String getEditedPhoneNumber(String phoneNumber) {

        if (new ValidationsClass().checkStringNull(phoneNumber)) {
            String firstThree = phoneNumber.substring(0, 2);
            String midThree = phoneNumber.substring(2, 5);
            String lastFour = phoneNumber.substring(5);

            phoneNumber = firstThree + "-" + midThree + "-" + lastFour;
            return phoneNumber;
        } else {
            return phoneNumber;
        }
    }

    /* return "i am good" to "I Am Good" */
    private String firstLetterCapsInWholeSentence(String string) {

        if (string != null && !string.isEmpty()) {
            string = string.trim();
            if (!string.isEmpty()) {
                String[] splitString = string.split(" ");
                String newStringValue = "";
                for (String caps : splitString) {
                    newStringValue = newStringValue.concat(firstLetterCaps(caps)) + " ";
                }
                return newStringValue.trim();
            } else return "";
        } else return "";
    }

    /* this function return like "hello" to "Hello" */
    private String firstLetterCaps(String str) {
        if (str != null && !str.isEmpty()) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str;
    }


    // show alert dialog message if user enters any invalid details in the login section
    public Dialog showAlertDialog(String title, String message, boolean isCancelable){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(isCancelable);
        if (!title.isEmpty())
            builder.setTitle(title);
        if (!message.isEmpty())
            builder.setMessage(message);
        builder.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int id) {

                // if user click yes to delete the account, then account will be deleted and
                // user logged out of the application and login screen will appear.
                Intent intent = new Intent(mContext, AuthenticationActivity.class);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.TITLE, GlobalConstants.FRAGMENT.LOGIN);
                intent.putExtra(GlobalConstants.FRAGMENT_INTENT_DATA.FRAGMENT_TAG, GlobalConstants.FRAGMENT.LOGIN);
                startActivity(intent);
            }
        });
        // if user click No than alert dialog will dismiss.
        builder.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
// Create the AlertDialog object and return it
        return builder.create();
    }
}