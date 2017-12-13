package com.annam.annamownernew.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsTripDetails;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.R;
import com.annam.annamownernew.Utilities.Utilities;
import com.annam.annamownernew.customfonts.MyTextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SFT on 14/10/2016.
 */
public class TripDetails_Fragment extends Fragment implements View.OnClickListener {

    private MyTextView tvBookingID, tvLocation, tvMachineType, tvMachineName, tvDate, tvMachineDesc, trip_land_condition,
            tvAmount, tvArrivalTime, tvOwnerName, tvOwnerPhone, tvOwnerNameTitle, trip_land_area, tvOwnerPhoneTitle,
            tvDaysTitle, tvHoursTitle, tvMintsTitle;
    private EditText etDays, etHours, etMints;
    private LinearLayout farmerDetails, tripDetails, acept_decline, btn_start_layout, btn_end_layout, start_end;
    private View line;
    private Button btnAccept, btnDecline, btnStart, btnEnd;
    private ProgressDialog loading;
    private String bookingID;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.trip_details_fragment, container, false);
        bookingID = getArguments().getString("bookingID");

        initViews(rootView);

        getData();
        getActivity().setTitle("Job Details");
        return rootView;
    }

    private void initViews(View rootView) {

        line = (View) rootView.findViewById(R.id.delete_view);
        farmerDetails = (LinearLayout) rootView.findViewById(R.id.farmer_details_layout);
//        tripDetails = (LinearLayout) rootView.findViewById(R.id.trip_time_layout);
        acept_decline = (LinearLayout) rootView.findViewById(R.id.btn_layout_accept_decline);
        btn_start_layout = (LinearLayout) rootView.findViewById(R.id.btn_start_layout);
        btn_end_layout = (LinearLayout) rootView.findViewById(R.id.btn_end_layout);
        start_end = (LinearLayout) rootView.findViewById(R.id.btn_layout_start_end);
        trip_land_area = (MyTextView) rootView.findViewById(R.id.trip_land_area);
        tvBookingID = (MyTextView) rootView.findViewById(R.id.td_bookingid);
        tvLocation = (MyTextView) rootView.findViewById(R.id.td_location);
        tvMachineType = (MyTextView) rootView.findViewById(R.id.td_machinetype);
        tvMachineName = (MyTextView) rootView.findViewById(R.id.td_machinename);
        tvDate = (MyTextView) rootView.findViewById(R.id.td_date);
        tvMachineDesc = (MyTextView) rootView.findViewById(R.id.td_machineno);
        tvAmount = (MyTextView) rootView.findViewById(R.id.td_amount);
        tvArrivalTime = (MyTextView) rootView.findViewById(R.id.td_exptime);
        tvOwnerName = (MyTextView) rootView.findViewById(R.id.td_ownername);
        tvOwnerPhone = (MyTextView) rootView.findViewById(R.id.td_ownerphone);
        tvOwnerNameTitle = (MyTextView) rootView.findViewById(R.id.td_ownernametitle);
        tvOwnerPhoneTitle = (MyTextView) rootView.findViewById(R.id.td_ownerphonetitle);
        trip_land_condition = (MyTextView) rootView.findViewById(R.id.trip_land_condition);
//        tvDaysTitle = (MyTextView) rootView.findViewById(R.id.td_daystitle);
//        tvHoursTitle = (MyTextView) rootView.findViewById(R.id.td_hourstitle);
//        tvMintsTitle = (MyTextView) rootView.findViewById(R.id.td_mintstitle);
//
//        etDays = (EditText) rootView.findViewById(R.id.td_days);
//        etHours = (EditText) rootView.findViewById(R.id.td_hours);
//        etMints = (EditText) rootView.findViewById(R.id.td_mints);

        btnAccept = (Button) rootView.findViewById(R.id.td_btnaccept);
        btnDecline = (Button) rootView.findViewById(R.id.td_btndecline);
        btnStart = (Button) rootView.findViewById(R.id.td_btnstart);
        btnEnd = (Button) rootView.findViewById(R.id.td_btnend);
        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);
        btnDecline.setVisibility(View.VISIBLE);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View rootView) {
        if (rootView == btnAccept) {
           /* btnStart.setVisibility(View.VISIBLE);
            tvOwnerName.setVisibility(View.VISIBLE);
            tvOwnerNameTitle.setVisibility(View.VISIBLE);
            tvOwnerPhone.setVisibility(View.VISIBLE);
            tvOwnerPhoneTitle.setVisibility(View.VISIBLE);
            btnAccept.setVisibility(View.INVISIBLE);
            btnDecline.setVisibility(View.INVISIBLE);*/

            acept_decline.setVisibility(View.GONE);
            start_end.setVisibility(View.VISIBLE);
            farmerDetails.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);


            acceptTrip();

        }
        if (rootView == btnDecline) {
            declineTrip();
        }
        if (rootView == btnStart) {
            startTrip();
            /*btnStart.setVisibility(View.INVISIBLE);
            btnEnd.setVisibility(View.VISIBLE);
            tvDaysTitle.setVisibility(View.VISIBLE);
            tvHoursTitle.setVisibility(View.VISIBLE);
            tvMintsTitle.setVisibility(View.VISIBLE);
            etDays.setVisibility(View.VISIBLE);
            etHours.setVisibility(View.VISIBLE);
            etMints.setVisibility(View.VISIBLE);*/
//            tripDetails.setVisibility(View.VISIBLE);
            btn_start_layout.setVisibility(View.GONE);
            btn_end_layout.setVisibility(View.VISIBLE);
        }
        if (rootView == btnEnd) {
            endTrip();
        }
    }

    private void declineTrip() {
        String url = ConsTripDetails.AcceptURL + bookingID + "&owner_id=" + OwnerInfo.getOwnerId()
                + "&own_machine_id=" + tvMachineDesc.getText().toString().trim() + "&status=1";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        Toast.makeText(getContext(), "Assigned To New Owner", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "No Other Owners Available", Toast.LENGTH_LONG).show();
                    }
                    fragment = new Owner_Home_Fragment();
                    Utilities.getInstance(getActivity()).changeHomeFragment(
                            fragment, "Owner_Home_Fragment", getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getData() {
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);
        String url = ConsTripDetails.URL + bookingID + "&owner_id=" + OwnerInfo.getOwnerId();
        Log.e( "getData: ",""+url );
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("errorCode").equals("Success")) {
                JSONObject tripDetails = jsonObject.getJSONObject("tripDetails");
                String sss = tripDetails.getString("land_area");
                Log.e("DETAILS", sss);
                tvBookingID.setText(tripDetails.getString("booking_id"));
                tvLocation.setText(tripDetails.getString("farmer_loc"));
                tvMachineType.setText(tripDetails.getString("machine_type"));
                tvMachineName.setText(tripDetails.getString("machine_name"));
                tvDate.setText(tripDetails.getString("date_and_time"));
                tvMachineDesc.setText(tripDetails.getString("machine_id"));
                tvAmount.setText(tripDetails.getString("rate"));
                trip_land_condition.setText(tripDetails.getString("land_condition"));
                String ss = tripDetails.getString("land_area");
                trip_land_area.setText(tripDetails.getString("land_area"));
                tvArrivalTime.setText(tripDetails.getString("exptime"));
                tvOwnerName.setText(tripDetails.getString("customer_name"));
                tvOwnerPhone.setText(tripDetails.getString("customer_phone"));



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void endTrip() {
        String url = ConsTripDetails.EndURL + bookingID + "&owner_id=" + OwnerInfo.getOwnerId();
//                +
//                "&day=" + etDays.getText().toString().trim() +
//                "&hour=" + etHours.getText().toString().trim() +
//                "&minutes=" + etMints.getText().toString().trim();
        Log.e("URL END",url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {


                        fragment = new Bill_Fragment();
                        Bundle args = new Bundle();
                        args.putString("response", response);
                        fragment.setArguments(args);
                        Utilities.getInstance(getActivity()).changeHomeFragment(
                                fragment, "Bill_Fragment", getActivity());
                    } else {
                        Toast.makeText(getActivity(), "Trip NOT Ended", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void startTrip() {
        String url = ConsTripDetails.StartURL + bookingID + "&owner_id=" + OwnerInfo.getOwnerId();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        Toast.makeText(getActivity(), "Trip Started", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Trip NOT Started", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "VOLLY ERROR");
//                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void acceptTrip() {
        String url = ConsTripDetails.AcceptURL + bookingID + "&owner_id=" + OwnerInfo.getOwnerId()
                + "&own_machine_id=" + tvMachineDesc.getText().toString().trim() + "&status=0";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        Toast.makeText(getActivity(), "Trip Accepted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Trip NOT Accepted", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
