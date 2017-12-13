package com.annam.annamownernew.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsTripDetails;
import com.annam.annamownernew.Adapter.BookingPageAdapter;
import com.annam.annamownernew.Constants.ConsBooking;
import com.annam.annamownernew.Model.BookingItem;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.R;
import com.annam.annamownernew.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements BookingPageAdapter.ItemClickCallback {

    private RecyclerView recView;
    private BookingPageAdapter bukadapter;
    private ProgressDialog loading;
    private List<BookingItem> data = new ArrayList<>();
//    private TextView tvDays, tvHours, tvMints;
//    private EditText edDays, edHours, edMints;

    private Button btnEnd, btnStart, btncancel;
    private Fragment fragment;
    private TextView noBookingAvailable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking, container, false);
        noBookingAvailable = (TextView) rootView.findViewById(R.id.noBookingAvailable);
        getActivity().setTitle("Bookings");


        getData();
        recView = (RecyclerView) rootView.findViewById(R.id.rec_list_booking);
        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(getActivity()); // (Context context)
        mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);// LinearLayoutManager.VERTICAL
        recView.setLayoutManager(mLinearLayoutManagerHorizontal);
        bukadapter = new BookingPageAdapter(data, getActivity());
        recView.setAdapter(bukadapter);

        setCallBack();
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!data.isEmpty()) {
//
//                    recView.setAdapter(bukadapter);
//                    setCallBack();
//                } else {
//                    recView.setVisibility(View.GONE);
//                    noBookingAvailable.setVisibility(View.VISIBLE);
//                }
//            }
//        }, 3);

        return rootView;
    }

    private void setCallBack() {
        bukadapter.setItemClickCallback(this);
    }

    private void getData() {
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        String url = ConsBooking.getBookingURL + OwnerInfo.getOwnerId();
        Log.e( "getData: ",""+url );

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",""+response );
                showJSON(response);
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        bukadapter.notifyDataSetChanged();
                        loading.dismiss();
//                        Toast.makeText(getActivity(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e( "onResponse: ",""+error );
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {

        try {
            Log.e("showJSON: ", ""+response);

            JSONObject jsonObject = new JSONObject(response);
            String data_avail = jsonObject.getString("errorCode");
            if (data_avail.equals("false")) {
                data.clear();
                if (!jsonObject.isNull("booking_details")) {
                    JSONArray serverDataArray = jsonObject.getJSONArray("booking_details");
                    for (int i = 0; i < serverDataArray.length(); i++) {
                        JSONObject serverData = serverDataArray.getJSONObject(i);
                        BookingItem item = new BookingItem();
                        item.setBOOKING_ID(serverData.getString("booking_id"));
                        item.setMACHINE_NAME(serverData.getString("selected_machine_name"));
                        item.setMACHINE_TYPE(serverData.getString("machine_type"));
                        item.setLOCATION(serverData.getString("farmer_location"));
                        item.setCUSTOMER_NAME(serverData.getString("farmer_name"));
                        item.setCUSTOMER_PHNO(serverData.getString("phone_no"));
                        item.setEST_AMOUNT(serverData.getString("Expected_Amount"));
                        item.setDATE(serverData.getString("Expected_Date"));
                        item.setMACHINE_NO(serverData.getString("selected_machine_name"));
                        item.setEXP_TIME(serverData.getString("Expected_Date"));
                        data.add(item);
                        Log.e("data chanage: ", "notify data change");


                    }

                }
                bukadapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancelClick(int p) {
        BookingItem item = (BookingItem) data.get(p);

        showalert(item.getCUSTOMER_PHNO(),item.getBOOKING_ID(),item.getMACHINE_NO());
    }

    private void showalert(final String customer_phno, final String bookingid, final String machaneid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setMessage("Do you really want to cancel?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
//                Toast.makeText(getContext(), "HOW COULD YOU ?", Toast.LENGTH_SHORT).show();
                sendSMS(customer_phno);
                declineTrip(bookingid,machaneid);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void sendSMS(String customer_phno) {
        String url = ConsBooking.sendSMSURL + OwnerInfo.getOwnerName() + "&phone=" + customer_phno;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")) {
                    Toast.makeText(getActivity(), "SMS Sent" + response, Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getActivity(), "Failure" + response, Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }




    @Override
    public void onStartTrip(int p) {
        final BookingItem item = (BookingItem) data.get(p);
        final View v = recView.getLayoutManager().findViewByPosition(p);

//        tvDays = (TextView) v.findViewById(R.id.td_daystitle);
//        tvHours = (TextView) v.findViewById(R.id.td_hourstitle);
//        tvMints = (TextView) v.findViewById(R.id.td_mintstitle);

//        tvDays.setFocusable(false);
//        tvHours.setFocusable(false);
//        tvMints.setFocusable(false);
//
//        edDays = (EditText) v.findViewById(R.id.td_days);
//        edHours = (EditText) v.findViewById(R.id.td_hours);
//        edMints = (EditText) v.findViewById(R.id.td_mints);

//        edDays.setFocusable(false);
//        edHours.setFocusable(false);
//        edMints.setFocusable(false);

//        tvDays.setVisibility(View.VISIBLE);
//        tvHours.setVisibility(View.VISIBLE);
//
//        tvMints.setVisibility(View.VISIBLE);
//        edDays.setVisibility(View.VISIBLE);
//
//        edHours.setVisibility(View.VISIBLE);
//        edMints.setVisibility(View.VISIBLE);


        String currentdate=getCurrentTime();
        if(currentdate.equals(item.getEXP_TIME()))
        {

            btnEnd = (Button) v.findViewById(R.id.td_btnend);
            btnEnd.setVisibility(View.VISIBLE);

            btnStart = (Button) v.findViewById(R.id.td_btnstart);
            btnStart.setVisibility(View.INVISIBLE);

            btncancel = (Button) v.findViewById(R.id.td_btncancel);
            btncancel.setVisibility(View.INVISIBLE);

            Log.e("onStartTrip: ",""+currentdate);
            startTrip(item.getBOOKING_ID());


        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());

            // set title
            alertDialogBuilder.setTitle("You are starting the job before the job date !" +
                    "");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            startTrip(item.getBOOKING_ID());
                            btnEnd = (Button) v.findViewById(R.id.td_btnend);
                            btnEnd.setVisibility(View.VISIBLE);

                            btnStart = (Button) v.findViewById(R.id.td_btnstart);
                            btnStart.setVisibility(View.INVISIBLE);

                            btncancel = (Button) v.findViewById(R.id.td_btncancel);
                            btncancel.setVisibility(View.INVISIBLE);



                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }



    }

    @Override
    public void onEndTrip(int p) {
        BookingItem item = (BookingItem) data.get(p);
        View v = recView.getLayoutManager().findViewByPosition(p);
//        edDays = (EditText) v.findViewById(R.id.td_days);
//        edHours = (EditText) v.findViewById(R.id.td_hours);
//        edMints = (EditText) v.findViewById(R.id.td_mints);
        endTrip(item.getBOOKING_ID());
    }

    private void startTrip(String booking_id) {

        String url = ConsTripDetails.StartURL + booking_id + "&owner_id=" + OwnerInfo.getOwnerId();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("Success")) {
                        Toast.makeText(getActivity(), "Work Started", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Work NOT Started", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private String fixURL(String tempURL) {
        URI uri = null;
        try {
            uri = new URI(tempURL.replaceAll(" ", "%20"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tempURL = String.valueOf(uri);
        uri = null;
        try {
            uri = new URI(tempURL.replaceAll("@", "%40"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tempURL = String.valueOf(uri);
        uri = null;
        try {
            uri = new URI(tempURL.replaceAll(",", "%2C"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return String.valueOf(uri);
    }


    private void declineTrip(String bookingIDs,String machaneid) {
        String url = ConsTripDetails.AcceptURL + bookingIDs + "&owner_id=" + OwnerInfo.getOwnerId()
                + "&own_machine_id=" + machaneid + "&status=1";



        String ss=fixURL(url);
        Log.e("declineTrip: ",""+ss );

        StringRequest stringRequest = new StringRequest(ss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                Log.e( "onResponse: ",""+response );
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getString("errorCode").equals("false")) {
                        getData();
                        Toast.makeText(getContext(), "Assigned To New Owner", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "No Other Owners Available", Toast.LENGTH_LONG).show();
                    }
//                    fragment = new Owner_Home_Fragment();
//                    Utilities.getInstance(getActivity()).changeHomeFragment(
//                            fragment, "Owner_Home_Fragment", getActivity());
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

    private void endTrip(String booking_id) {
        String url = ConsTripDetails.EndURL + booking_id + "&owner_id=" + OwnerInfo.getOwnerId();
        Log.e( "endTrip: ",""+url );
//                +
//                "&day=" + edDays.getText().toString().trim() +
//                "&hour=" + edHours.getText().toString().trim() +
//                "&minutes=" + edMints.getText().toString().trim();

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
                        Toast.makeText(getActivity(), "Work not Ended", Toast.LENGTH_LONG).show();
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
    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }//
}