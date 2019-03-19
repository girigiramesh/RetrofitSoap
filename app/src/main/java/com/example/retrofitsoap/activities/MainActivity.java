package com.example.retrofitsoap.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.retrofitsoap.R;
import com.example.retrofitsoap.databinding.ActivityMainBinding;
import com.example.retrofitsoap.model.UsCittiesResponse;
import com.example.retrofitsoap.network.ApiClient;
import com.example.retrofitsoap.network.IParser;
import com.example.retrofitsoap.adapter.CitiesAdapter;
import com.example.retrofitsoap.model.TableUsCities;
import com.example.retrofitsoap.network.WSUtils;
import com.example.retrofitsoap.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Response;

/**
 * Created by hymavathi.k on 12/18/2017.
 */
public class MainActivity extends AppCompatActivity implements IParser, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private CitiesAdapter adapter;
    private String city;

    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        apiClient = ApiClient.getInstance();
        binding.btMakeRequest.setOnClickListener(this);
        binding.etCityName.setText("chicago");
    }

    @Override
    public void onClick(View view) {
        CommonUtils.hideKeyboard(MainActivity.this);
        if (CommonUtils.isOnline(MainActivity.this)) {
            getCitiesValidation();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void getCitiesValidation() {
        city = binding.etCityName.getText().toString();
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter city name.", Toast.LENGTH_SHORT);
        } else {
            requestForCities();
        }
    }

    private void requestForCities() {
        CommonUtils.showWaitingDialog(this);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(WSUtils.KEY_USCITY, city);

        apiClient.WSRequest(this, WSUtils.GET_CITIES, map, WSUtils.REQ_GET_CITIES, (IParser) this);
    }

    @Override
    public void successResponse(int requestCode, Response response) {
        CommonUtils.hideWaitingDialog();
        if (requestCode == WSUtils.REQ_GET_CITIES) {
            try {
                Headers responseHeaders = response.headers();
                Log.e(TAG, responseHeaders.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            parseCities((UsCittiesResponse) response.body());
        }
    }

    private void parseCities(UsCittiesResponse envelop) {
        //nigam work on this part
        List<TableUsCities> zipCodes = new ArrayList<>();
        zipCodes.addAll(envelop.getElements());
        binding.rvElements.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CitiesAdapter(zipCodes);
        binding.rvElements.setAdapter(adapter);
    }

    @Override
    public void errorResponse(int requestCode, Throwable t) {
        CommonUtils.hideWaitingDialog();
        Log.e(TAG, t.getMessage());
        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noInternetConnection(int requestCode) {
        CommonUtils.hideWaitingDialog();
        Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
    }
}
