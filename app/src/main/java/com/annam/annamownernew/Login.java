package com.annam.annamownernew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.Model.MachineType;
import com.annam.annamownernew.Model.MachineType_List;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Ui.ForgotPasswordActivity;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String PREFS_LOGIN = "login";
    protected int splashTime = 3000;
    String username;
    String password;
    LinearLayout loginpanel;
    int timer;
    boolean hasLoggedIn;
    ProgressDialog dialog;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignup;
    private TextView forgotPassword;
    String deviceid;
    SharedPreferences sharedpreferences;
    SharedPreferences prefs;
     Thread th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup_main_activity);

        initViews();
         sharedpreferences = new SecurePreferences(getApplicationContext(), "AnnamAgroTech", PREFS_LOGIN);
        hasLoggedIn = sharedpreferences.getBoolean("hasLoggedIn", false);
        loginpanel = (LinearLayout) findViewById(R.id.pnl_login_panel);
        deviceid=Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

dialog=new ProgressDialog(this);

        if (hasLoggedIn) {
            loginpanel.setVisibility(View.GONE);
            userLogin();




//            String username=sharedpreferences.getString("username","");
//            String password=sharedpreferences.getString("password","");
//            editTextUsername.setText(username);
//
//            editTextPassword.setText(password);

            Log.e( "onCreate: ","loged in" );

        } else {


          th = new Thread() {

                @Override
                public void run() {
                    try {
                        for (timer = -400; timer < 41; timer++) {
                            int waited = 0;
                            while (waited < splashTime) {
                                Thread.sleep(1);
                                Login.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginpanel.getLayoutParams();
                                            params.bottomMargin = timer;
                                            loginpanel.setLayoutParams(params);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                waited += 900;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            };
        }

//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    th.start();
//                }
//            }, 3000);

        }


    private void initViews() {
        editTextUsername = (EditText) findViewById(R.id.edt_email_phone);
        editTextPassword = (EditText) findViewById(R.id.edt_passsword);
        buttonLogin = (Button) findViewById(R.id.btn_login);
        buttonSignup = (Button) findViewById(R.id.btn_signup);
        forgotPassword = (TextView) findViewById(R.id.forgot_txt_forgot);

        buttonLogin.setOnClickListener(this);
        buttonSignup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }


    private boolean validatesignupfielsEntered() {

        String str_signup_phone = editTextUsername.getText().toString();
        String str_signup_password = editTextPassword.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid
        if (TextUtils.isEmpty(str_signup_phone)) {
            editTextUsername.setError(getString(R.string.error_field_required));
            focusView = editTextUsername;
            cancel = true;
        }

        if (str_signup_phone.length()!= 10) {
            editTextUsername.setError("Enter a Valid Phone Number");
            focusView = editTextUsername;
            cancel = true;
        }

        if (TextUtils.isEmpty(str_signup_password)) {

            editTextPassword.setError(getString(R.string.error_field_required));
            focusView = editTextPassword;
            cancel = true;
        }



        if (cancel) {

            focusView.requestFocus();
        } else {

            return cancel;
        }

        Log.e( "validate ",""+cancel );
        return cancel;
    }

    @Override
    public void onClick(View view) {
        Log.e( "onClick: ","loge in");
        if (view == buttonLogin) {






if(!validatesignupfielsEntered())
{


    try {
        dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
    } catch (Exception e) {
        dialog.dismiss();
    }
    userLogin();
}
else {
    dialog.dismiss();

}


        }

        if (view == buttonSignup) {
            Intent singup = new Intent(getApplicationContext(), Signup.class);
            startActivity(singup);
            Login.this.finish();
        }

        if (view == forgotPassword) {
            Intent forgotPasswordIntent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(forgotPasswordIntent);
            Login.this.finish();
        }
    }


    private void userLogin() {
        if (hasLoggedIn) {

            username = sharedpreferences.getString("username", null);
            password = sharedpreferences.getString("password", null);



        } else {
            username = editTextUsername.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();



        }

        if (isNetworkAvailable(getBaseContext())) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConsLogin.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("On login  ",response.toString() );

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String flag = jsonObject.getString(ConsLogin.ERROR_CODE);
                                Log.e( "flag: ", ""+flag);
                                if (flag.equals("Success")) {

                                    if (!hasLoggedIn) {
                                        dialog.dismiss();
                                        //SharedPreferences.Editor editor = getSharedPreferences(PREFS_LOGIN_STATUS, Context.MODE_PRIVATE).edit();

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putBoolean("hasLoggedIn", true);
                                        editor.putString("username", username);
                                        editor.putString("password", password);

                                        editor.commit();
                                    }
                                    setOwnerInfo(jsonObject);

                                    JSONArray JA_machinetype = jsonObject.getJSONArray(ConsLogin.JSON_ARRAY_MACHINE_TYPE);

                                    MachineType_List.serverData = new ArrayList<MachineType>();
                                    for (int i = 1; i < JA_machinetype.length(); i++) {
                                        JSONObject machinetype = JA_machinetype.getJSONObject(i);
                                        setMachineType(machinetype);
                                    }
                                    Intent ownerHome = new Intent(getApplicationContext(), Owner_Home.class);
                                    startActivity(ownerHome);
                                    Login.this.finish();

                                } else {
                                    dialog.dismiss();


                                    ClearSharedPrifAndUpdate();
                                    Toast.makeText(getApplicationContext(), flag, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.cancel();
//                            Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                            DisplyMessage("Check internet connection and try again !");




                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> map = new HashMap<String, String>();
                    map.put(ConsLogin.KEY_USERNAME, username);
                    map.put(ConsLogin.KEY_PASSWORD, password);
                    map.put("deviceid", deviceid);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
dialog.dismiss();
           DisplyMessage("No internet connection");

        }
    }

    private void DisplyMessage(String message) {

        SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text(message) // text to be displayed
                        .textColor(Color.WHITE) // change the text color
                        .color(Color.RED) // change the background color
                        .actionLabel("Retry")
                        .actionColor(Color.BLACK) // action button label color
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                userLogin();
                            }
                        }) // action button's ActionClickListener
                , this); // activity where it is displayed
    }

    private void ClearSharedPrifAndUpdate() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("hasLoggedIn", false);
        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();
        editTextUsername.setText("");

        editTextPassword.setText("");

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginpanel.getLayoutParams();
        params.bottomMargin = 0;
        loginpanel.setLayoutParams(params);
    }

//    private void setMachineDetails(JSONObject machine_details) {
//        try {
//            MachineDetails md = new MachineDetails();
//            md.setMachineId(machine_details.getString(ConsLogin.MD_MACHINE_ID));
//            md.setMachineNo(machine_details.getString(ConsLogin.MD_MACHINE_NO));
//            md.setMachineName(machine_details.getString(ConsLogin.MD_MACHINE_NAME));
////            md.setMachineDesc(machine_details.getString(ConsLogin.MD_MACHINE_DESC));
//            md.setMAVAILABLE(machine_details.getString(ConsLogin.MD_MAVAILABLE));
////            md.setIMAGE(machine_details.getString(ConsLogin.MD_IMAGE));
//            MachineDetails_List.serverData.add(md);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void setMachineType(JSONObject machinetype) {
        try {
            MachineType mt = new MachineType();
            mt.setMachineTypeId(machinetype.getString(ConsLogin.MT_MACHINE_TYPE_ID));
            mt.setNAME(machinetype.getString(ConsLogin.MT_NAME));
            mt.setPickupReq(machinetype.getString(ConsLogin.MT_PICKUP_REQ));
            MachineType_List.serverData.add(mt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setOwnerInfo(JSONObject jsonObject) {
        try {
            OwnerInfo.setOwnerId(jsonObject.getString(ConsLogin.OWNER_ID));
            OwnerInfo.setOwnerName(jsonObject.getString(ConsLogin.OWNER_NAME));
            OwnerInfo.setONETIME(jsonObject.getString(ConsLogin.ONETIME));
            OwnerInfo.setDisableFlag(jsonObject.getString(ConsLogin.DISABLE_FLAG));
            OwnerInfo.setUSERNAME(username);
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
