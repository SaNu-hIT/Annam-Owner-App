package com.annam.annamownernew.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.Adapter.ListBookingAdapater;
import com.annam.annamownernew.Constants.ConsTripDetails;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Model.TripModel;
import com.annam.annamownernew.R;
import com.annam.annamownernew.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by SFT on 14/10/2016.
 */
public class TripDetails_Fragment_new extends Fragment implements View.OnClickListener,ListBookingAdapater.AcceptDeclAin {

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
    RecyclerView recyclerview;
    ListBookingAdapater bukadapter;
    ArrayList<TripModel> tripModels=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.trip_fragment, container, false);
        //dummy data booking id ,it is used for argument passing in fragments
        bookingID = getArguments().getString("bookingID");


        initViews(rootView);

        getData();
        recyclerview= (RecyclerView) rootView.findViewById(R.id.recyclerview);

        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(getActivity()); // (Context context)
        mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);// LinearLayoutManager.VERTICAL
        recyclerview.setLayoutManager(mLinearLayoutManagerHorizontal);
        bukadapter = new ListBookingAdapater(tripModels, getActivity());
        bukadapter.setItemClickCallback(this);
        recyclerview.setAdapter(bukadapter);
        getActivity().setTitle("Booking List");
        return rootView;
    }

    private void initViews(View rootView) {


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


//            acceptTrip();

        }
        if (rootView == btnDecline) {
//            declineTrip();
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

    private void declineTrip(String bookingIDs,String machaneid) {
        String url = ConsTripDetails.AcceptURL + bookingIDs + "&owner_id=" + OwnerInfo.getOwnerId()
                + "&own_machine_id=" + machaneid + "&status=1";


        URI uri = null;
        try {
            uri = new URI(url.replaceAll(" ", "%20"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final String finalurl = String.valueOf(uri);
        StringRequest stringRequest = new StringRequest(finalurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        getData();
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
        String url = ConsTripDetails.URL+ OwnerInfo.getOwnerId();
        Log.e( "url: ",url );

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                tripModels.clear();
                bukadapter.notifyDataSetChanged();
                showJSON(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        tripModels.clear();
        bukadapter.notifyDataSetChanged();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("errorCode").equals("Success")) {
                if(jsonObject.has("tripDetails"))
                {
                    JSONArray jsonElements = jsonObject.getJSONArray("tripDetails");
                    for (int s = 0; s < jsonElements.length(); s++) {
                        TripModel trip = new TripModel();
                        JSONObject data = jsonElements.getJSONObject(s);
                        String booking_id = data.getString("booking_id");
                        String farmer_loc = data.getString("farmer_loc");
                        String date_and_time = data.getString("date_and_time");
                        String land_area = data.getString("land_area");
                        String machine_type = data.getString("machine_type");
                        String machine_name = data.getString("machine_name");
                        String machine_id = data.getString("machine_id");
                        String exptime = data.getString("exptime");
                        String rate = data.getString("rate");

                        String customer_name = data.getString("customer_name");
                        String customer_phone = data.getString("customer_phone");
                        String land_condition;
                        if (data.has("land_condition")) {
                            //Checking address Key Present or not
                             land_condition = data.getString("land_condition");



                        }
                        else {
                            //Do Your Staff
                            land_condition="";
                        }




                        trip.setBooking_id(booking_id);
                        trip.setFarmer_loc(farmer_loc);
                        trip.setDate_and_time(date_and_time);
                        trip.setLand_area(land_area);
                        trip.setMachine_type(machine_type);
                        trip.setMachine_name(machine_name);

trip.setLand_condition(land_condition);
                        trip.setMachine_id(machine_id);
                        trip.setExptime(exptime);
                        trip.setRate(rate);
                        trip.setCustomer_name(customer_name);
                        trip.setCustomer_phone(customer_phone);


                        tripModels.add(trip);
                        bukadapter.notifyDataSetChanged();

                    }
                }
                else
                {
                    fragment = new Owner_Home_Fragment();
                    Utilities.getInstance(getActivity()).changeHomeFragment(
                            fragment, "Owner_Home_Fragment", getActivity());


//                    bukadapter.notifyDataSetChanged();
                }







            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void showJSON(String response) {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.getString("errorCode").equals("Success")) {
//                JSONObject tripDetails = jsonObject.getJSONObject("tripDetails");
//                String sss = tripDetails.getString("land_area");
//                Log.e("DETAILS", sss);
//                tvBookingID.setText(tripDetails.getString("booking_id"));
//                tvLocation.setText(tripDetails.getString("farmer_loc"));
//                tvMachineType.setText(tripDetails.getString("machine_type"));
//                tvMachineName.setText(tripDetails.getString("machine_name"));
//                tvDate.setText(tripDetails.getString("date_and_time"));
//                tvMachineDesc.setText(tripDetails.getString("machine_id"));
//                tvAmount.setText(tripDetails.getString("rate"));
//                trip_land_condition.setText(tripDetails.getString("land_condition"));
//                String ss = tripDetails.getString("land_area");
//                trip_land_area.setText(tripDetails.getString("land_area"));
//                tvArrivalTime.setText(tripDetails.getString("exptime"));
//                tvOwnerName.setText(tripDetails.getString("customer_name"));
//                tvOwnerPhone.setText(tripDetails.getString("customer_phone"));
//
//
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

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
                        Toast.makeText(getActivity(), "Job NOT Ended", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                        Toast.makeText(getActivity(), "Job Started", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Job NOT Started", Toast.LENGTH_LONG).show();
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

    private void acceptTrip(String bookingIDs,String tvMachineDescs) {
        String url = ConsTripDetails.AcceptURL + bookingIDs + "&owner_id=" + OwnerInfo.getOwnerId()
                + "&own_machine_id=" + tvMachineDescs+ "&status=0";

        Log.e("acceptTrip url: ",url );

        URI uri = null;
        try {
            uri = new URI(url.replaceAll(" ", "%20"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final String finalurl = String.valueOf(uri);
        StringRequest stringRequest = new StringRequest(finalurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        getData();
                        Toast.makeText(getActivity(),    "Job Accepted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Job NOT Accepted", Toast.LENGTH_LONG).show();
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

    @Override
    public void acceptClick(String bookingID, String machaneid) {
        acceptTrip(bookingID,machaneid);
        Log.e( "acceptClick: ","acceptClick " );
        Log.e( "bookingID: ",""+bookingID );
        Log.e( "machaneid: ",""+machaneid );
    }

    @Override
    public void declainClick(String bookingID, String machaneid) {
        declineTrip(bookingID,machaneid);


        Log.e( "declainClick: ","declainClick " );
        Log.e( "bookingID: ",""+bookingID );
        Log.e( "machaneid: ",""+machaneid );
    }
}
