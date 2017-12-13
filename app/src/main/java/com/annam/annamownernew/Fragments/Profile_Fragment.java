package com.annam.annamownernew.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.PickLocationActivity;
import com.annam.annamownernew.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by SFT on 10/10/2016.
 */
public class Profile_Fragment extends Fragment implements View.OnClickListener {

    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 13;

    EditText profile_name, profile_address, profile_phone, profile_mail;
    Button profile_save, profile_cancel, profile_location;
    private ProgressDialog loading;
    String latitude = "0";
    String longitude = "0";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.profile_fragment,container,false);

        profile_name=(EditText)profileView.findViewById(R.id.textProfileName);
        profile_address=(EditText)profileView.findViewById(R.id.textProfileAddress);
        profile_phone=(EditText)profileView.findViewById(R.id.textProfilePhone);
        profile_mail=(EditText)profileView.findViewById(R.id.textProfileMail);
        profile_location=(Button)profileView.findViewById(R.id.buttonProfileLocation);
        profile_save=(Button)profileView.findViewById(R.id.buttonProfileSave);
        profile_cancel=(Button)profileView.findViewById(R.id.buttonProfileCancel);
        profile_save.setOnClickListener(this);
        profile_cancel.setOnClickListener(this);
        profile_location.setOnClickListener(this);
     initianlState();
        getActivity().setTitle("Profile");
        initListeners();
        getData();
        return profileView;
    }

    private void initianlState() {
        profile_mail.setEnabled(false);
        profile_name.setEnabled(false);
        profile_address.setEnabled(false);
        profile_phone.setEnabled(false);
        profile_mail.setEnabled(false);
        profile_location.setEnabled(false);
        profile_save.setVisibility(View.GONE);
        profile_cancel.setVisibility(View.VISIBLE);
        profile_cancel.setText("EDIT");
    }

    private void getData() {
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String url = ConsLogin.PROFILE_URL + OwnerInfo.getOwnerId();

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
                        Toast.makeText(getActivity(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
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

    private void initListeners() {
        profile_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();

             profile_mail.setEnabled(true);
         profile_name.setEnabled(true);
                profile_address.setEnabled(true);
               profile_phone.setEnabled(true);
                profile_mail.setEnabled(true);
                 profile_location.setEnabled(true);
                profile_save.setVisibility(View.VISIBLE);
                profile_cancel.setVisibility(View.GONE);
//                profile_cancel.setText("EDIT");
                return;
            }
        });
    }

    public void setData(){
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String username, name, address, phone, email, location;
        username = profile_mail.getText().toString().trim();
        name = profile_name.getText().toString().trim();
        address = profile_address.getText().toString().trim();
        phone = profile_phone.getText().toString().trim();
        email = profile_mail.getText().toString().trim();
        location = profile_location.getText().toString().trim();

        String tempURL = ConsLogin.PROFILE_SETURL + OwnerInfo.getOwnerId() + "&username=" + username
                + "&name=" + name + "&address=" + address + "&phone=" + phone + "&email=" + email
                + "&location=" + location + "&latitude=" + latitude + "&longitude=" + longitude;

        String url = fixURL(tempURL);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                setJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setJSON(String response){
        String data_avail="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsLogin.ERROR_CODE);
            if (data_avail.equals("Success")){

                initianlState();

//                Toast.makeText(getContext(),"Successful",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(),"Error updating profile",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void DisplyMessage(String message) {

//        SnackbarManager.show(
//                Snackbar.with(getActivity().getApplicationContext()) // context
//                        .text(message) // text to be displayed
//                        .textColor(Color.WHITE) // change the text color
//                        .color(Color.RED) // change the background color
//                        .actionLabel("Retry")
//                        .actionColor(Color.BLACK) // action button label color
//                        .actionListener(new ActionClickListener() {
//                            @Override
//                            public void onActionClicked(Snackbar snackbar) {
////                                userLogin();
//                            }
//                        }) // action button's ActionClickListener
//                , this); // activity where it is displayed
    }

    private void showJSON(String response){
        String data_avail="";
        String name="";
        String address="";
        String phone = "";
        String email = "";
        String panchayath_loc = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsLogin.ERROR_CODE);
            JSONObject serverData = jsonObject.getJSONObject(ConsLogin.JSON_ARRAY_PROFILE);
            if (data_avail.equals("Success")){
                name = serverData.getString(ConsLogin.PROFILE_NAME);
                address = serverData.getString(ConsLogin.PROFILE_ADDRESS);
                phone = serverData.getString(ConsLogin.PROFILE_PHONE);
                email = serverData.getString(ConsLogin.PROFILE_EMAIL);
                panchayath_loc = serverData.getString(ConsLogin.PROFILE_LOCATION);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data_avail.equals("Success")){
            profile_name.setText(name);
            profile_address.setText(address);
            profile_phone.setText(phone);
            profile_mail.setText(email);
            profile_location.setText(panchayath_loc);
        } else {
            profile_name.setText("No Data Available");
            profile_address.setText("No Data Available");
            profile_phone.setText("No Data Available");
            profile_mail.setText("No Data Available");
            profile_location.setText("No Data Available");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == profile_save){
            setData();
        }
        if (view == profile_location){
            Intent i= new Intent(getActivity(), PickLocationActivity.class);
            startActivityForResult(i, CUSTOM_AUTOCOMPLETE_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  CUSTOM_AUTOCOMPLETE_REQUEST_CODE){
            if(data!=null) {
                // set the location recieved from picklocation activity
                profile_location.setText(data.getStringExtra("Location Address"));
                setLatLan(data.getStringExtra("PlaceID"));
            }
        }
    }

    private void setLatLan(String placeID) {
        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeID + "&key=AIzaSyAxIYFy6VhyhkvuDrpvTL_XxOI25kgpSkw";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setLatLanJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void setLatLanJSON(String response) {
        String data_avail="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString("status");
            if (data_avail.equals("OK")){
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject geometry = result.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                latitude = location.getString("lat");
                longitude = location.getString("lng");
            } else {
                Toast.makeText(getContext(),"LatLan Not Found",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
