package com.annam.annamownernew.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.annam.annamownernew.R;
import com.annam.annamownernew.customfonts.MyTextView;

import com.annam.annamownernew.Utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SFT on 15/10/2016.
 */
public class Bill_Fragment extends Fragment implements View.OnClickListener  {
    private MyTextView tvBookingID, tvLocation, tvDate, tvMachineType, tvMachineName, tvOwnerName,
            tvOwnerPhone,  tvDay, tvRate, tvExpTime,  tvAdditionalTime, tvToatalAmount, tvAdvancePay;
    private Button btnOkay;
    private String response;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.bill,container,false);
        response = getArguments().getString("response");
        initViews(rootView);
        getData();
        getActivity().setTitle("Bill");
        return rootView;
    }

    private void getData() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject bill_details = jsonObject.getJSONObject("bill_details");
            tvBookingID.setText(bill_details.getString("booking_id"));
            tvLocation.setText(bill_details.getString("farmer_loc"));
            tvDate.setText(bill_details.getString("date_and_time"));
            tvMachineType.setText(bill_details.getString("machine_type"));
            tvMachineName.setText(bill_details.getString("machine_name"));
            tvOwnerName.setText(bill_details.getString("customer_name"));
            tvOwnerPhone.setText(bill_details.getString("customer_phone"));
            tvDay.setText(bill_details.getString("Day"));
            tvRate.setText(bill_details.getString("rate"));
            tvExpTime.setText(bill_details.getString("exptime"));
//            tvAdditionalTime.setText(bill_details.getString("additional_time"));
            tvToatalAmount.setText(bill_details.getString("total_amount"));
            String s=bill_details.getString("total_amount");
            Log.e("TOTAL",s);
//            tvAdvancePay.setText(bill_details.getString("isAdvancePay"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View rootView) {
        tvBookingID = (MyTextView) rootView.findViewById(R.id.bill_bookingid);
        tvLocation = (MyTextView) rootView.findViewById(R.id.bill_location);
        tvDate = (MyTextView) rootView.findViewById(R.id.bill_date);
        tvMachineType = (MyTextView) rootView.findViewById(R.id.bill_machinetype);
        tvMachineName = (MyTextView) rootView.findViewById(R.id.bill_machinename);
        tvOwnerName = (MyTextView) rootView.findViewById(R.id.bill_ownername);
        tvOwnerPhone = (MyTextView) rootView.findViewById(R.id.bill_ownerphone);
        tvDay = (MyTextView) rootView.findViewById(R.id.bill_day);
        tvRate = (MyTextView) rootView.findViewById(R.id.bill_rate);
        tvExpTime = (MyTextView) rootView.findViewById(R.id.bill_exptime);
        tvAdditionalTime = (MyTextView) rootView.findViewById(R.id.bill_additionaltime);
        tvToatalAmount = (MyTextView) rootView.findViewById(R.id.bill_total_amount);
        tvAdvancePay = (MyTextView) rootView.findViewById(R.id.bill_advancepay);

        btnOkay = (Button) rootView.findViewById(R.id.bill_btnokay);
        btnOkay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        fragment = new Owner_Home_Fragment();
        Utilities.getInstance(getContext()).changeHomeFragment(
                fragment, "Owner_Home_Fragment", getActivity());
    }
}
