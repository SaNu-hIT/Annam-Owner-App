package com.annam.annamownernew.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Fragments.dummy.DummyContent;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.Adapter.FirstPageAdapter;
import com.annam.annamownernew.Constants.ConsAddMachine;
import com.annam.annamownernew.Constants.ConsBooking;
import com.annam.annamownernew.Constants.ConsCheckBooking;
import com.annam.annamownernew.Constants.ConsEditMachine;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.R;
import com.annam.annamownernew.Ui.EditMachineActivity;
import com.annam.annamownernew.Utilities.Utilities;
import com.annam.annamownernew.customfonts.MyTextViewHead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Owner_Home_Fragment extends Fragment implements FirstPageAdapter.ItemClickCallback {

    MyTextViewHead ownerName;
    Context context;
    private RecyclerView recView;
    private FirstPageAdapter firstPageAdapter;
    private ArrayList listData,second;
    private Fragment fragment;
    private ProgressDialog loading;
    CardView linear1;
 List<MachineDetails> listDatanew;
    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Owner_Home_Fragment", "onResume");
if(listData.size()==0)
{
    getMachaneDetails();
}
else
{
    listData.clear();
    getMachaneDetails();
}



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listData=new ArrayList();


        second= (ArrayList) MachineDetails_List.getServerData();
        if (second.size() == 0) {
            Log.e("getMachaneDetails", "getMachaneDetails");

            getMachaneDetails();
        }
        else {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("Owner_Home_Fragment", "invoked");

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.owner_home, container, false);
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);

        recView = (RecyclerView) rootView.findViewById(R.id.rec_list);
        linear1 = (CardView) rootView.findViewById(R.id.linear1);

        listData.clear();
listData=new ArrayList();

        second= (ArrayList) MachineDetails_List.getServerData();
        if (second.size() == 0) {
            linear1.setVisibility(View.VISIBLE);

            Log.e("getMachaneDetails", "getMachaneDetails");
            getMachaneDetails();
        }
        else {

        }
        if (getActivity() != null) {
            context = getActivity().getBaseContext();

        } else {
            Log.e("NO MAIN ACTIVITy", "fdsf");
        }


int mColumnCount=2;

            Context context = rootView.getContext();

                recView.setLayoutManager(new GridLayoutManager(context, mColumnCount));




        firstPageAdapter = new FirstPageAdapter(MachineDetails_List.getServerData(), context);
        recView.setAdapter(firstPageAdapter);

        firstPageAdapter.notifyDataSetChanged();
        firstPageAdapter.setItemClickCallback(this);
        initViews(rootView);



        getActivity().setTitle("Home");

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        checkBooking();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

        return rootView;
    }

    private void checkBooking() {
        String url = ConsCheckBooking.URL + OwnerInfo.getOwnerId();

        Log.e( "checkBooking: ",""+url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONCE", response);
                if (!response.equals("0")) {

                    showalert(response);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "NO New Bookings", Toast.LENGTH_SHORT).show();
                    }
                }
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


    private void getMachaneDetails() {
        String url = ConsBooking.VIEWMACHANE + OwnerInfo.getOwnerId();
        Log.e("URL", url);

if(isNetworkAvailable(getContext())) {
    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.e("RESPONCE", response);
            setJSON(response);
            loading.cancel();
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (getActivity() != null) {
                        loading.cancel();

//                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    requestQueue.add(stringRequest);
}
else
{
    loading.cancel();

}
    }

    private void setJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsAddMachine.ERROR_CODE);
            if (data_avail.equals("Success")) {
                listData.clear();
                JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
                MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                for (int i = 0; i < JA_machine_details.length(); i++) {
                    JSONObject machine_details = JA_machine_details.getJSONObject(i);
                    setMachineDetails(machine_details);
                    listDatanew=MachineDetails_List.getServerData();
                    if (listDatanew.size()<1)
                    {
                        linear1.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        linear1.setVisibility(View.GONE);

                    }
                    firstPageAdapter = new FirstPageAdapter(MachineDetails_List.getServerData(), context);
                    recView.setAdapter(firstPageAdapter);
                    firstPageAdapter.notifyDataSetChanged();
                    listData = (ArrayList) MachineDetails_List.getServerData();
                    firstPageAdapter.setItemClickCallback(this);
                }

            } else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews(View rootView) {
        ownerName = (MyTextViewHead) rootView.findViewById(R.id.ownername);
        ownerName.setText(OwnerInfo.getOwnerName());
    }

    private void showalert(final String response) {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setMessage("New Booking Available!\nPress OK to view details");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes",
                    fragment = new TripDetails_Fragment_new();
                    Bundle args = new Bundle();
                    args.putString("bookingID", response);
                    fragment.setArguments(args);
                    Utilities.getInstance(getActivity()).changeHomeFragment(
                            fragment, "TripDetails_Fragment", getActivity());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    @Override
    public void onEditClick(int p) {
        if(listData.size()!=0) {
            MachineDetails item = (MachineDetails) listData.get(p);
            Intent i;
            i = new Intent(getContext(), EditMachineActivity.class);
            i.putExtra("macNo", item.getMachineNo());
            startActivity(i);
        }


    }

    @Override
    public void onDeleteClick(int p) {
        if(listData.size()!=0) {
            MachineDetails item = (MachineDetails) listData.get(p);
            showDeleteAlert(item.getMachineName(), item.getMachineNo());
        }
        else
        {
            Log.e("Error","No data");
        }
    }

    private void showDeleteAlert(String machineName, final String machineNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setMessage("Do you really want to Delete " + machineName + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                deleteMachine(machineNo);
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

    private void deleteMachine(String machineNo) {
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching...", false, false);
        String url = ConsEditMachine.DELETE_URL + OwnerInfo.getOwnerId()
                + "&machine_no=" + machineNo;
        Log.e( "deleteMachine: ",""+url );
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showDeleteMachineJSON(response);
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

    private void showDeleteMachineJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsEditMachine.ERROR_CODE);
            if (data_avail.equals(ConsEditMachine.SUCCESS)) {
                try {
                    JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
                    MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                    for (int i = 0; i < JA_machine_details.length(); i++) {
                        JSONObject machine_details = JA_machine_details.getJSONObject(i);
                        setMachineDetails(machine_details);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(this).attach(this).commit();
                    }
                } catch (JSONException e) {
                    Log.d("JSON", "MachineDetails Array Is NULL");
                    MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(this).attach(this).commit();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMachineDetails(JSONObject machine_details) {
        try {
            MachineDetails md = new MachineDetails();
            md.setMachineId(machine_details.getString(ConsLogin.MD_MACHINE_ID));
            md.setMachineNo(machine_details.getString(ConsLogin.MD_MACHINE_NO));
            md.setMachineName(machine_details.getString(ConsLogin.MD_MACHINE_NAME));
//            md.setMachineDesc(machine_details.getString(ConsLogin.MD_MACHINE_DESC));
            md.setMAVAILABLE(machine_details.getString(ConsLogin.MD_MAVAILABLE));
            String macha = machine_details.getString("image");
            md.setIMAGE(machine_details.getString("image"));
            MachineDetails_List.serverData.add(md);
            firstPageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
