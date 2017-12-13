package com.annam.annamownernew.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsChangePassword;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.R;
import com.annam.annamownernew.Utilities.Utilities;
import com.annam.annamownernew.customfonts.MyEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by SFT on 10/10/2016.
 */
public class ChangePassword_Fragment extends Fragment {
    MyEditText oldpassword, newpassword, confirmpassword;
    Button chanepassword;
    String strNewPassword, strConfirmPassword, strOldPassword;

    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.changepassword_fragment,container,false);
        oldpassword=(MyEditText)rootView.findViewById(R.id.edt_change_old_pasfeild);
        newpassword=(MyEditText)rootView.findViewById(R.id.edt_change_newfeild);
        confirmpassword=(MyEditText)rootView.findViewById(R.id.edt_change_confmfeild);
        chanepassword=(Button)rootView.findViewById(R.id.btn_change_ok);

        chanepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utilities.getInstance(getActivity()).isNetAvailable()) {
                    strOldPassword = oldpassword.getText().toString();
                    strNewPassword = newpassword.getText().toString();
                    strConfirmPassword = confirmpassword.getText().toString();
                    if (strNewPassword.equals(strConfirmPassword)){
                        changePassword();
                    } else {
                        Toast.makeText(getActivity(), "Password Mismatch", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "network error", Toast.LENGTH_SHORT).show();
                }


            }
        });

        getActivity().setTitle("Change Password");
        return rootView;
    }

    private void changePassword() {
        loading = ProgressDialog.show(getActivity(),"Please wait...","Fetching...",false,false);

        String tempURL = ConsChangePassword.URL + OwnerInfo.getUSERNAME() + "&old_password=" + strOldPassword + "&new_password=" + strNewPassword;

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
        String url = String.valueOf(uri);

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
//                        Toast.makeText(getActivity(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setJSON(String response) {
        String data_avail="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsChangePassword.ERROR_CODE);
            if (data_avail.equals("Success")){

                Toast.makeText(getActivity(),"Password Changed Successfully",Toast.LENGTH_LONG).show();
                oldpassword.setText("");
                newpassword.setText("");
                confirmpassword.setText("");


            } else {
                Toast.makeText(getActivity(),"Enter Correct Current Password",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}