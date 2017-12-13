package com.annam.annamownernew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsBooking;
import com.annam.annamownernew.Constants.ConsSignup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SFT on 11/10/2016.
 */
public class Signup extends AppCompatActivity implements View.OnClickListener {

    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 13;
    private EditText txtName, txtPhone,
            txtAddress, txtEmail, txtPassword, txtConfirmPassword;
    private Button buttonSignup, buttonCancel, buttonLocation;
    private ProgressDialog loading;
    String latitude = "0";
    AutoCompleteTextView actv;
    String longitude = "0";
//    String[] fruits;
ArrayList<String> fruits;

    public static final String  AnnamFarer_uploadsignup_URL = ConsBooking.AnnamOWNER_BaseUrl+"signup_onload.php";
    public void PreloadItem()
    {
        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        String deviceid= Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        setPanchayath(deviceid);
        initViews();
        PreloadItem();
        new AppEULA(this).show();
    }

    private void initViews() {
        txtName = (EditText) findViewById(R.id.signup_name);
        txtPhone = (EditText) findViewById(R.id.signup_phone);
        txtAddress = (EditText) findViewById(R.id.signup_address);
        txtEmail = (EditText) findViewById(R.id.signup_email);
        buttonLocation = (Button) findViewById(R.id.signup_panchayath);
        txtPassword = (EditText) findViewById(R.id.signup_password);
        txtConfirmPassword = (EditText) findViewById(R.id.signup_confirm_password);
        buttonSignup = (Button) findViewById(R.id.btn_signuppage);
        buttonCancel = (Button) findViewById(R.id.btn_signupcancel);
         actv= (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        buttonSignup.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonLocation.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  CUSTOM_AUTOCOMPLETE_REQUEST_CODE){
            if(data!=null) {
                // set the location recieved from picklocation activity
                buttonLocation.setText(data.getStringExtra("Location Address"));
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
                        Toast.makeText(getApplicationContext(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

public  void setPanchayath(final String deviceid)
{
    StringRequest stringRequest = new StringRequest(Request.Method.POST, AnnamFarer_uploadsignup_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e( "onResponse: ",jsonObject.toString() );
                        PanchayathAutoComplet(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String, String> map = new HashMap<String, String>();
            map.put("deviceid", deviceid);
            return map;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}

    private void PanchayathAutoComplet(JSONObject jsonObject) {


        try {
            String status=jsonObject.getString("status").toString();
            String  errorcode=jsonObject.getString("errorcode").toString();
            if(status.equals("1"))
            {
                fruits=new ArrayList<>();
                JSONArray panchayath=jsonObject.getJSONArray("panchayath");
                int count=panchayath.length();
                for (int i=0;i<count;i++)
                {
                    JSONObject objec=panchayath.getJSONObject(i);

                 fruits.add(objec.get("name").toString());

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (this, android.R.layout.select_dialog_item, fruits);
                //Getting the instance of AutoCompleteTextView

                actv.setThreshold(1);//will start working from first character
                actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setPanchyath() {

        StringRequest stringRequest = new StringRequest(AnnamFarer_uploadsignup_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",response.toString() );
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                Toast.makeText(this,"LatLan Not Found",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLocation){
            Intent i= new Intent(Signup.this, PickLocationActivity.class);
            startActivityForResult(i, CUSTOM_AUTOCOMPLETE_REQUEST_CODE);
        }
        if(view == buttonSignup){
            signup();
        }
        
        if(view == buttonCancel){
            Intent Login = new Intent(getApplicationContext(), Login.class);
            startActivity(Login);
            Signup.this.finish();
        }
    }

    private void signup() {
        if(!validatesignupfielsEntered()){
            String phone = txtPhone.getText().toString().trim();
            String name = txtName.getText().toString().trim();
            String address = txtAddress.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String location = buttonLocation.getText().toString();
            String password = txtPassword.getText().toString().trim();
            String deviceid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
String id=actv.getText().toString();
            Log.e( "signup: ", id);
            loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

            String tempURL = ConsSignup.URL + name + "&address=" + address + "&phone=" + phone +
                    "&email=" + email + "&password=" + password + "&location=" +
                    location + "&latitude=" + latitude + "&longitude=" + longitude+"&deviceid="+deviceid+"&panchayath="+id;
            Log.e( "tempURL: ", tempURL);
            String url = fixURL(tempURL);

            Log.e("Fixurl: ",url );
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    Log.e( "Sign Up: ",""+response );
                    setJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Volley Error " + error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void setJSON(String response) {
        String data_avail="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsSignup.ERROR_CODE);
            if (data_avail.equals("Success")){
                Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
                Signup.this.finish();
            } else {
                Toast.makeText(this,"User Already Exist",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private boolean validatesignupfielsEntered() {

        String str_signup_phone = txtPhone.getText().toString();
        String str_signup_name = txtName.getText().toString();
        String str_signup_address = txtAddress.getText().toString();
        String str_signup_email = txtEmail.getText().toString();
        String str_signup_panchayath = buttonLocation.getText().toString();
        String str_signup_password = txtPassword.getText().toString();
        String str_signup_confirmpassword = txtConfirmPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid
        if (TextUtils.isEmpty(str_signup_phone)) {
            txtPhone.setError(getString(R.string.error_field_required));
            focusView = txtPhone;
            cancel = true;
        }
        if (str_signup_phone.length()!= 10) {
            txtPhone.setError("Enter a Valid Phone Number");
            focusView = txtPhone;
            cancel = true;
        }
        if (TextUtils.isEmpty(str_signup_name)) {
            txtName.setError(getString(R.string.error_field_required));
            focusView = txtName;
            cancel = true;
        }
        if (TextUtils.isEmpty(str_signup_address)) {
            txtAddress.setError(getString(R.string.error_field_required));
            focusView = txtAddress;
            cancel = true;
        }
        if (TextUtils.isEmpty(str_signup_email)) {
            txtEmail.setError(getString(R.string.error_field_required));
            focusView = txtEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(str_signup_panchayath)) {
            buttonLocation.setError(getString(R.string.error_field_required));
            focusView = buttonLocation;
            cancel = true;
        }

        if (TextUtils.isEmpty(str_signup_password)) {

            txtPassword.setError(getString(R.string.error_field_required));
            focusView = txtPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(str_signup_confirmpassword)) {
            txtConfirmPassword.setError(getString(R.string.error_field_required));
            focusView = txtConfirmPassword;
            cancel = true;
        }
        if (!str_signup_password.equals(str_signup_confirmpassword)) {
            txtConfirmPassword.setError("Password Mismatch");
            focusView = txtConfirmPassword;
            cancel = true;
        }


        if (cancel) {

            focusView.requestFocus();
        } else {

            return cancel;
        }
        return cancel;
    }

    public void onBaclClick(View view)
    {
        Intent Login = new Intent(getApplicationContext(), Login.class);
        startActivity(Login);
        Signup.this.finish();
    }
}
