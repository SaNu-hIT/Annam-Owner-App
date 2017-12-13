package com.annam.annamownernew.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsForgotPassword;
import com.annam.annamownernew.Login;
import com.annam.annamownernew.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SFT on 27/10/2016.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_forgotpassword;
    Button forgot_password;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initViews();
    }

    private void initViews() {
        edt_forgotpassword=(EditText) findViewById(R.id.edt_forgotfeild);
        forgot_password=(Button) findViewById(R.id.btn_forgot_ok);
        forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == forgot_password){
            forgotPassword();
        }
    }

    private void forgotPassword() {
        if (validate()){
            String username = edt_forgotpassword.getText().toString().trim();
            loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
            String url = ConsForgotPassword.URL + username;

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
                            Toast.makeText(getApplicationContext(), "Volley Error " + error.getMessage(),Toast.LENGTH_SHORT).show();
                            Log.e("VolleyError",error.getMessage());
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void showJSON(String response) {
        String data_avail="";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsForgotPassword.ERROR_CODE);
            if (data_avail.equals(ConsForgotPassword.SUCCESS)){
                Toast.makeText(this,"Password is send to your mail .Please Login with new password",Toast.LENGTH_LONG).show();
                Intent login = new Intent(getApplicationContext(), Login.class);
                startActivity(login);
                ForgotPasswordActivity.this.finish();
            } else {
                Toast.makeText(this,"User Does Not Exist",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validate(){
        boolean flag = true;
        String username = edt_forgotpassword.getText().toString().trim();
        View focusView = null;
        if (TextUtils.isEmpty(username)) {
            edt_forgotpassword.setError(getString(R.string.error_field_required));
            focusView = edt_forgotpassword;
            focusView.requestFocus();
            flag = false;
        }
        return flag;
    }
}
