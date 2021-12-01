package com.rhythm.mypizzaapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rhythm.mypizzaapp.GlobalConstants.GlobalConstants;
import com.rhythm.mypizzaapp.getterAndSetterClasses.SignupBeanClass;

import java.util.ArrayList;

public class SharedPreferenceClass {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;;
    private ArrayList<SignupBeanClass> signupDataList;

    public SharedPreferenceClass(Context context, ArrayList<SignupBeanClass> dataList){
        this.context = context;
        signupDataList = dataList;
        setSharedPreferences();
    }

    public SharedPreferenceClass(Context context) {
        this.context = context;
    }

    // creating sharedPreferences
    private void setSharedPreferences(){
        sharedPreferences = context.getSharedPreferences(GlobalConstants.SHARED_PREFERENCE.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (signupDataList != null) {
            for (int i = 0; i < signupDataList.size(); i++) {
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_NAME, signupDataList.get(i).getName());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_EMAIL, signupDataList.get(i).getEmail());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_ADDRESS, signupDataList.get(i).getAddress());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_PHONENUMBER, signupDataList.get(i).getPhoneNumber());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_PASSWORD, signupDataList.get(i).getPassword());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_PASSWORD, signupDataList.get(i).getPassword());
                editor.putString(GlobalConstants.SHARED_PREFERENCE.KEY_GENDER, signupDataList.get(i).getGender());
            }
            editor.apply();
        }
    }

    // setting logged in boolean value, if user has logged in set to true
    // than user did not need to enter the login details again, it will skip login screen
    // and show the home screen, and if false than it show the login screen.
    public void setLoggedIn(boolean isLoggedIn){
        sharedPreferences = context.getSharedPreferences(GlobalConstants.SHARED_PREFERENCE.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(GlobalConstants.SHARED_PREFERENCE.IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // if user wants to delete his account we will iterate the list
    // and return the position which contains the email id.
    public int deleteAccount(String email){
        int position = -1;
        if (signupDataList != null) {
            for (int i = 0; i < signupDataList.size(); i++) {
                if (signupDataList.get(i).getEmail().equalsIgnoreCase(email)){
                    return i;
                }
            }
        }
        return position;
    }

    // this method clear the entire data of shared preference
    public void clearSharedPreferences(){
        sharedPreferences = context.getSharedPreferences(GlobalConstants.SHARED_PREFERENCE.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
