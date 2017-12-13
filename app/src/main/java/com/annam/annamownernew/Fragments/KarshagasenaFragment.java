package com.annam.annamownernew.Fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsBooking;
import com.annam.annamownernew.Constants.ConsEditMachine;
import com.annam.annamownernew.Model.KarshagasenaDetailsBean;
import com.annam.annamownernew.Model.Karshgasena_List;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Ui.EditKarshakasenaActivity;
import com.annam.annamownernew.Adapter.KarshagasenaAdapter;
import com.annam.annamownernew.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KarshagasenaFragment extends Fragment implements KarshagasenaAdapter.ItemClickCallback {

    TextView ownerName;
    private RecyclerView recView;
    private KarshagasenaAdapter karshagasenaAdapter;
    private ArrayList listData, second;
    private Fragment fragment;
    private ProgressDialog loading;

    @Override
    public void onResume() {
        super.onResume();
        Log.e("KarshakasenaFragment", "onResume");

        listData = (ArrayList) Karshgasena_List.getServerData();
        if (listData.size() == 0) {
            GetData();
            karshagasenaAdapter.notifyDataSetChanged();
        } else {
            listData.clear();
            GetData();
            karshagasenaAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.karshagasena_home, container, false);
        second = (ArrayList) Karshgasena_List.getServerData();
        listData = new ArrayList();
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);
        if (second.size() == 0) {
            GetData();
        }

        getActivity().setTitle("Karshakasena");
        recView = (RecyclerView) rootView.findViewById(R.id.rec_list);


        Log.e("KarshagasenaFragment", "onCreateView");
        if (getActivity() != null) {
// Code goes here.

            LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(getActivity()); // (Context context)
            mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);// LinearLayoutManager.VERTICAL
            recView.setLayoutManager(mLinearLayoutManagerHorizontal);
            karshagasenaAdapter = new KarshagasenaAdapter(Karshgasena_List.getServerData(), getActivity());
            recView.setAdapter(karshagasenaAdapter);
        }
//        karshagasenaAdapter.setItemClickCallback(getContext());


//        initViews(rootView);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

        return rootView;
    }

    private void GetData() {
        String url = ConsBooking.AnnamOWNER_BaseUrl+"getkarshakasena.php?owner_id=" + OwnerInfo.getOwnerId();
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
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


//    private void initViews(View rootView) {
//        ownerName = (TextView) rootView.findViewById(R.id.ownername);
//        ownerName.setText(OwnerInfo.getOwnerName());
//    }
//
//    private void showalert(final String response) {
//        if (getActivity()!=null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setCancelable(false);
//            builder.setMessage("New Booking Available!\nPress OK to view details");
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //if user pressed "yes",
//                    fragment = new TripDetails_Fragment();
//                    Bundle args = new Bundle();
//                    args.putString("bookingID", response);
//                    fragment.setArguments(args);
//                    Utilities.getInstance(getActivity()).changeHomeFragment(
//                            fragment, "TripDetails_Fragment", getActivity());
//                }
//            });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
//    }


//    @Override
//    public void onEditClick(int p) {
//        MachineDetails item = (MachineDetails) listData.get(p);
//        Intent i;
//        i = new Intent(getContext(), EditMachineActivity.class);
//        i.putExtra("macNo",item.getMachineNo());
//        startActivity(i);
//
//
//    }

//    @Override
//    public void onDeleteClick(int p) {
//        MachineDetails item = (MachineDetails) listData.get(p);
//        showDeleteAlert(item.getMachineName(),item.getMachineNo());
//    }

//    private void showDeleteAlert(String machineName, final String machineNo) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setCancelable(false);
//        builder.setMessage("Do you really want to Delete " + machineName + "?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user pressed "yes", then he is allowed to exit from application
//                deleteMachine(machineNo);
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    //    private void deleteMachine(String machineNo) {
//        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);
//        String url = ConsEditMachine.DELETE_URL + OwnerInfo.getOwnerId()
//                + "&machine_no=" + machineNo;
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                loading.dismiss();
//                showDeleteMachineJSON(response);
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(),"Network Error " + error.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//    }
//
    private void showJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsEditMachine.ERROR_CODE);
            if (data_avail.equals(ConsEditMachine.SUCCESS)) {
                try {
                    listData.clear();
                    JSONArray karshagasenaDetails = jsonObject.getJSONArray("karshakasena_details");
                    MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                    for (int i = 0; i < karshagasenaDetails.length(); i++) {
                        JSONObject senaDetails = karshagasenaDetails.getJSONObject(i);
                        setKarshagaseNaDetails(senaDetails);
                        karshagasenaAdapter = new KarshagasenaAdapter(Karshgasena_List.getServerData(), getActivity());
                        recView.setAdapter(karshagasenaAdapter);
                        karshagasenaAdapter.notifyDataSetChanged();
                        listData = (ArrayList) Karshgasena_List.getServerData();
                        karshagasenaAdapter.setItemClickCallback(this);

//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(this).attach(this).commit();
                    }
                } catch (JSONException e) {
                    Log.d("JSON", "MachineDetails Array Is NULL");
//                    MachineDetails_List.serverData = new ArrayList<MachineDetails>();
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.detach(this).attach(this).commit();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setKarshagaseNaDetails(JSONObject karshagaseNaDetails) {
        try {

            KarshagasenaDetailsBean karshagasenaDetailsBean = new KarshagasenaDetailsBean();
            karshagasenaDetailsBean.setKarshgasenaid(karshagaseNaDetails.getString("karshakasena_id"));


            karshagasenaDetailsBean.setOwnerid(karshagaseNaDetails.getString("owner_id"));
            karshagasenaDetailsBean.setTitile(karshagaseNaDetails.getString("title"));
            karshagasenaDetailsBean.setNatureofwork(karshagaseNaDetails.getString("nature_of_work"));
            karshagasenaDetailsBean.setRadious(karshagaseNaDetails.getString("radious"));
            karshagasenaDetailsBean.setLaborsno(karshagaseNaDetails.getString("labours_no"));
            karshagasenaDetailsBean.setLocation(karshagaseNaDetails.getString("location"));
            Karshgasena_List.serverData.add(karshagasenaDetailsBean);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEditClick(int p) {
        KarshagasenaDetailsBean item = (KarshagasenaDetailsBean) listData.get(p);
        Intent i;
        i = new Intent(getContext(), EditKarshakasenaActivity.class);
        i.putExtra("ID", item.getKarshgasenaid());
        startActivity(i);

    }

    @Override
    public void onDeleteClick(int p) {
        KarshagasenaDetailsBean item = (KarshagasenaDetailsBean) listData.get(p);
        showDeleteAlert(item.getKarshgasenaid(), item.getTitile());

    }

    private void showDeleteAlert(final String karshgasenaid, String titile) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage("Do you really want to Delete " + titile + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                deleteMachine(karshgasenaid);
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

    public void deleteMachine(String karshgasenaid) {

        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);
        String url = ConsBooking.AnnamOWNER_BaseUrl+"deletekarshakarena.php?karshakasena_id=" + karshgasenaid;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject rs = new JSONObject(response);
                    if (rs.getString("errorCode").equals("Success")) {
                        Log.e("SUCCLESS", "SUCCESS");
                        loading.dismiss();
                        GetData();
                    } else {
                        Log.e("FAIL", "FAIL");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong plz try again", Toast.LENGTH_SHORT).show();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Network Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
