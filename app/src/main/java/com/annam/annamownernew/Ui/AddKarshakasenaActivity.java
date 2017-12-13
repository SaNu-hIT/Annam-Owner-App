package com.annam.annamownernew.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.annam.annamownernew.Constants.ConsAddMachine;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Owner_Home;
import com.annam.annamownernew.R;
import com.annam.annamownernew.customfonts.MyEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.annam.annamownernew.Constants.ConsBooking.AnnamFARMAR_BaseUrl;
import static com.annam.annamownernew.Constants.ConsBooking.AnnamOWNER_BaseUrl;

/**
 * Created by SFT on 15/2/2017.
 */
public class AddKarshakasenaActivity extends AppCompatActivity {

    String strJobTitle, strNatureofWork, strRadious, strLabours,strrates;
    MyEditText edJobTitle, edNatureofWork, edRadious, edLabours,rates_text;
    Button bSave;
    ImageView backClick;
    ProgressDialog loading;
    private MaterialSpinner spinner;

    List<Integer> spinnerList;

    ArrayList<String> arrayList;
    private String rates_texts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_karshakasena);
        arrayList=new ArrayList<>();
        spinnerList=new ArrayList<>();


        initViews();
        initListners();
        setSpinner();
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setSpinner() {



        String url =AnnamFARMAR_BaseUrl+"getNatureOfWork.php?farmer_id=323";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }

    private void showJSON(String response) {
        Log.e( "showJSON: respomce",response );

        spinnerList.add(0);


        try {
            JSONObject jsonObject = new JSONObject(response);
          String  data_avail = jsonObject.getString(ConsAddMachine.ERROR_CODE);
            if (data_avail.equals("Success")) {
                JSONArray nature_of_work_details = jsonObject.getJSONArray("nature_of_work_details");

                for (int i = 0; i < nature_of_work_details.length(); i++) {
                    JSONObject machinetype = nature_of_work_details.getJSONObject(i);
                    arrayList.add(machinetype.getString("name"));
                }

                spinner.setItems(arrayList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initListners() {

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()) {

                    Log.e( "Nature work: ",strNatureofWork );
                    loading = ProgressDialog.show(AddKarshakasenaActivity.this, "Please wait...", "Fetching...", false, false);
                    loading.setCancelable(false);
                    String tempURL = AnnamOWNER_BaseUrl+"addkarshakasena.php?format=json&owner_id="
                            + OwnerInfo.getOwnerId()
                            + "&title=" + strJobTitle
                            + "&work=" + strNatureofWork
                            + "&radious=" + strRadious
                            + "&rate=" + strrates
                            + "&labours=" + strLabours;
                    Log.e("Mylog", tempURL);
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
                                    Toast.makeText(AddKarshakasenaActivity.this, "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(AddKarshakasenaActivity.this, "Please enter valid info", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsAddMachine.ERROR_CODE);
            if (data_avail.equals("Success")) {
                Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();

//                JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
//                MachineDetails_List.serverData = new ArrayList<MachineDetails>();
//                for (int i = 0; i < JA_machine_details.length(); i++) {
//                    JSONObject machine_details = JA_machine_details.getJSONObject(i);
//                    setMachineDetails(machine_details);
//                }
//                Intent ownerHome = new Intent(getApplicationContext(), Owner_Home.class);
//                startActivity(ownerHome);
                AddKarshakasenaActivity.this.finish();

            } else {

                Toast.makeText(this, "Unable to add new machine", Toast.LENGTH_LONG).show();
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
            md.setMachineDesc(machine_details.getString(ConsLogin.MD_MACHINE_DESC));
            md.setMAVAILABLE(machine_details.getString(ConsLogin.MD_MAVAILABLE));
            md.setIMAGE(machine_details.getString(ConsLogin.MD_IMAGE));
            MachineDetails_List.serverData.add(md);
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

    private boolean validate() {
        boolean flag = true;
        View focusView = null;

        strJobTitle = edJobTitle.getText().toString().trim();
        strNatureofWork = spinner.getText().toString().trim();
        strRadious = edRadious.getText().toString().trim();
        strLabours = edLabours.getText().toString().trim();
        strrates = rates_text.getText().toString().trim();

        if (TextUtils.isEmpty(strJobTitle)) {
            edJobTitle.setError(getString(R.string.error_field_required));
            focusView = edJobTitle;
            flag = false;
        }
        if (TextUtils.isEmpty(strNatureofWork)) {
            spinner.setError(getString(R.string.error_field_required));
            focusView = spinner;
            flag = false;
        }
        if (TextUtils.isEmpty(strRadious)) {
            edRadious.setError(getString(R.string.error_field_required));
            focusView = edRadious;
            flag = false;
        }
        if (TextUtils.isEmpty(strLabours)) {
            edLabours.setError(getString(R.string.error_field_required));
            focusView = edLabours;
            flag = false;
        }if (TextUtils.isEmpty(strrates)) {
            rates_text.setError(getString(R.string.error_field_required));
            focusView = rates_text;
            flag = false;
        }
        if (!flag) {
            focusView.requestFocus();
            return flag;
        } else {
            return flag;
        }
    }

    private void initViews() {
        edJobTitle = (MyEditText) findViewById(R.id.job_title);
        edNatureofWork = (MyEditText) findViewById(R.id.ed_nature);
        edRadious = (MyEditText) findViewById(R.id.ed_radious);
        edLabours = (MyEditText) findViewById(R.id.ed_labours);
        rates_text = (MyEditText) findViewById(R.id.rates_text);
        bSave = (Button) findViewById(R.id.add_button);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

        backClick = (ImageView) findViewById(R.id.backClick);
    }

    @Override
    public void onBackPressed() {
        Intent ownerHome = new Intent(getApplicationContext(), Owner_Home.class);
        startActivity(ownerHome);
        AddKarshakasenaActivity.this.finish();
    }
}
