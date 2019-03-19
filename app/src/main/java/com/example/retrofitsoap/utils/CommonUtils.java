package com.example.retrofitsoap.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.retrofitsoap.R;

/**
 * Created by hymavathi.k on 12/20/2017.
 */

public class CommonUtils {
    public static ProgressDialog progressDialog;

    public static void showWaitingDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle(context.getString(R.string.dialog_title));
            progressDialog.setMessage(context.getString(R.string.dialog_message));
        }

        progressDialog.show();
    }

    public static void hideWaitingDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }


    public static void showKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
