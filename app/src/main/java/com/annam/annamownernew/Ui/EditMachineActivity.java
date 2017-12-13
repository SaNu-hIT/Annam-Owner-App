package com.annam.annamownernew.Ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.LinearLayout;
import android.widget.Spinner;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.Constants.ConsAddMachine;
import com.annam.annamownernew.Constants.ConsEditMachine;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.R;
import com.annam.annamownernew.customfonts.MyEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class EditMachineActivity extends AppCompatActivity {
    MyTextView machine;
    MyEditText macNo, macSpec1, macSpec2, macSpec3, macRPD, macRPH,radios;
    CheckBox  cbAnywhere, cbPickupYES, cbPickupNO;
    MyEditText edTransportRate;
//    CheckBox cbPanchyath, cbBlock, cbDistrict;
//    MyEditText edMinRate;
    Button save;
    private ProgressDialog loading;
    String bundleMacNo = null;
    String ownerMachineID = null;
    String machineType = null, machineName = null, machineNo, specification1, specification2, specification3, ratePerDay, ratePerHour, vehicleFuelNDriverType;
    String radious = "Anywhere", pickup = "false", transportRate, minRate;
    Spinner spinner3;
    String strMacNoOld = "0";
    LinearLayout specifications;
    boolean flag_cocunut_tree = true;
    private MyTextView day, rate_per_hours;

    /* 3 specs added (MyTextView)  ids - specificatn1, specificatn2, specificatn3;
       4 Check boxes added  ids - cb_panchayath, cb_block, cb_district, cb_anywhere;


       in Linear layout  id - layout_rate
                 ids - rate_per_day, rate_per_hour (MyEditTexts)

        in Linear layout  id - Layout_kilo
                 ids - copra_kg (MyEditText)

        in Linear layout  id - layout_tree
                 ids - tree_climb, tree_clean (MyEditTexts)



       in Linear layout  id - pickup_layout
       2 MyTextViews for pickup yes/no   ids - pickup_Yes, pickup_No;

       2 MyEditTexts for transportaion rate ids - transport_rate, transport_min_rate;
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_machine);
        Bundle extras = getIntent().getExtras();
        initViews();
        if (extras != null) {
            bundleMacNo = extras.getString("macNo");
            getData();
        }
        else
        {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }



    }

    private void getData() {
        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        String url = ConsEditMachine.getURL + OwnerInfo.getOwnerId() + "&machine_no=" + bundleMacNo;
        Log.e( "getData: ", ""+url);
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
                        Toast.makeText(EditMachineActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsEditMachine.ERROR_CODE);
            if (data_avail.equals(ConsEditMachine.SUCCESS)) {


                JSONObject machine_details = jsonObject.getJSONObject(ConsEditMachine.JSON_OBJECT_MACHINE_DETAILS);

                machine.setText(machine_details.getString(ConsEditMachine.MACHINE_NAME));
                macNo.setText(machine_details.getString(ConsEditMachine.MACHINE_NO));
                String sssss = machine_details.getString(ConsEditMachine.MACHINE_NAME);
                if (sssss.equals("coconut tree climber")) {
                    specifications.setVisibility(View.GONE);
                    flag_cocunut_tree = false;


                    rate_per_hours.setText("Rate per tree to clean");
                    day.setText("Date per tree to climb");
                    macRPD.setHint("rate tree climb ");
                    macRPH.setHint("rate tree clean ");
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.cocunut_tree_item, android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter);
                } else {

                    flag_cocunut_tree = true;
                    macSpec1.setText(machine_details.getString(ConsEditMachine.SPECIFICATION1));
                    macSpec2.setText(machine_details.getString(ConsEditMachine.SPECIFICATION2));
                    macSpec3.setText(machine_details.getString(ConsEditMachine.SPECIFICATION3));

                    specifications.setVisibility(View.VISIBLE);
                    rate_per_hours.setText("Rate Per Hour");
                    day.setText("Rate Per Day");
                    macRPD.setHint("Rate Per Day");
                    macRPH.setHint("rate per hours");
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.machine_spinner, android.R.layout.simple_spinner_dropdown_item);
                    spinner3.setAdapter(adapter);
                }
                macRPD.setText(machine_details.getString(ConsEditMachine.RATE_PER_DAY));
                macRPH.setText(machine_details.getString(ConsEditMachine.RATE_PER_HOUR));
                String serverRadious = machine_details.getString(ConsEditMachine.RADIOUS);


//                if (serverRadious.equals("Panchayath")) {
//                    cbPanchyath.setChecked(true);
//                    radious = "Panchayath";
//                } else if (serverRadious.equals("Block")) {
//                    cbBlock.setChecked(true);
//                    radious = "Block";
//                } else
// if (serverRadious.equals("District")) {
//                    cbDistrict.setChecked(true);
//                    radious = "District";
//                } else
                    if (serverRadious.equals("Anywhere")) {
                    cbAnywhere.setChecked(true);
                    radious = "Anywhere";
                }
                else
                    {
                        radious=serverRadious;
                        radios.setText(radious);

                    }
                String serverPickup = machine_details.getString(ConsEditMachine.PICKUP);
                if (serverPickup.equals("true")) {
                    cbPickupYES.setChecked(true);
                    pickup = "true";
                } else if (serverPickup.equalsIgnoreCase("false")) {
                    cbPickupNO.setChecked(true);
                    pickup = "false";
                }
                edTransportRate.setText(machine_details.getString(ConsEditMachine.TRANSPORTRATE));
//                edMinRate.setText(machine_details.getString(ConsEditMachine.MINRATE));
                ownerMachineID = machine_details.getString(ConsEditMachine.OWN_MACHID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        machine = (MyTextView) findViewById(R.id.em_machine);
        macNo = (MyEditText) findViewById(R.id.em_mac_no);
        macSpec1 = (MyEditText) findViewById(R.id.em_specificatn1);
        macSpec2 = (MyEditText) findViewById(R.id.em_specificatn2);
        macSpec3 = (MyEditText) findViewById(R.id.em_specificatn3);
        macRPD = (MyEditText) findViewById(R.id.em_rate_per_day);
        macRPH = (MyEditText) findViewById(R.id.em_rate_per_hour);
//        cbPanchyath = (CheckBox) findViewById(R.id.em_cb_panchayath);
//        cbBlock = (CheckBox) findViewById(R.id.em_cb_block);
//        cbDistrict = (CheckBox) findViewById(R.id.em_cb_district);
        cbAnywhere = (CheckBox) findViewById(R.id.em_cb_anywhere);
        cbPickupYES = (CheckBox) findViewById(R.id.em_pickup_Yes);
        cbPickupNO = (CheckBox) findViewById(R.id.em_pickup_No);
        edTransportRate = (MyEditText) findViewById(R.id.em_transport_rate);
        radios = (MyEditText) findViewById(R.id.radios);
        specifications = (LinearLayout) findViewById(R.id.specifications);
        day = (MyTextView) findViewById(R.id.day);
        rate_per_hours = (MyTextView) findViewById(R.id.rate_per_hours);
        save = (Button) findViewById(R.id.em_save_button);
//        cbPanchyath.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //is chkIos checked?
//                if (((CheckBox) v).isChecked()) {
//                    cbBlock.setChecked(false);
//                    cbDistrict.setChecked(false);
//                    cbAnywhere.setChecked(false);
//                    radious = "Panchayath";
//                }
//            }
//        });
//        cbBlock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //is chkIos checked?
//                if (((CheckBox) v).isChecked()) {
//                    cbPanchyath.setChecked(false);
//                    cbDistrict.setChecked(false);
//                    cbAnywhere.setChecked(false);
//                    radious = "Block";
//                }
//            }
//        });
//        cbDistrict.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //is chkIos checked?
//                if (((CheckBox) v).isChecked()) {
//                    cbPanchyath.setChecked(false);
//                    cbBlock.setChecked(false);
//                    cbAnywhere.setChecked(false);
//                    radious = "District";
//                }
//            }
//        });
        cbAnywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
//                    cbPanchyath.setChecked(false);
//                    cbBlock.setChecked(false);
//                    cbDistrict.setChecked(false);
                    radious = "Anywhere";
                }
            }
        });
        cbPickupYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    cbPickupNO.setChecked(false);
                    pickup = "true";
                }
            }
        });
        cbPickupNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    cbPickupYES.setChecked(false);
                    pickup = "false";
                }
            }
        });
        spinner3 = (Spinner) findViewById(R.id.em_v_f_d_status);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.machine_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicleFuelNDriverType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void EditMachSave(View view) {
        setData();
    }

    private void setData() {
        if (validate()) {
            String tempURL = ConsEditMachine.setURL
                    + OwnerInfo.getOwnerId()
                    + "&owner_machid=" + ownerMachineID
                    + "&machine_no=" + machineNo
                    + "&specification1=" + specification1
                    + "&specification2=" + specification2
                    + "&specification3=" + specification3
                    + "&vehicleFuelNDriverType=" + vehicleFuelNDriverType
                    + "&rate_per_day=" + ratePerDay
                    + "&rate_per_hour=" + ratePerHour
                    + "&radious=" + radious
                    + "&pickup=" + pickup
                    + "&transportrate=" + transportRate;

            String url = fixURL(tempURL);


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSONSetData(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditMachineActivity.this, "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(this, "Please enter valid info", Toast.LENGTH_LONG).show();
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

    private void showJSONSetData(String response) {
        String data_avail = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsEditMachine.ERROR_CODE);
            if (data_avail.equals(ConsEditMachine.SUCCESS)) {
                Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
                JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
                MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                for (int i = 0; i < JA_machine_details.length(); i++) {

                    JSONObject machine_details = JA_machine_details.getJSONObject(i);
//                    setMachineDetails(machine_details);
                }
//                Intent ownerHome = new Intent(getApplicationContext(), Owner_Home_Fragment.class);
//                startActivity(ownerHome);
                EditMachineActivity.this.finish();
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
            md.setIMAGE(machine_details.getString(ConsLogin.MD_IMAGE));
            MachineDetails_List.serverData.add(md);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        boolean flag = true;
        View focusView = null;

        machineNo = macNo.getText().toString().trim();
        specification1 = macSpec1.getText().toString().trim();
        specification2 = macSpec2.getText().toString().trim();
        specification3 = macSpec3.getText().toString().trim();
        ratePerDay = macRPD.getText().toString().trim();
        ratePerHour = macRPH.getText().toString().trim();
        transportRate = edTransportRate.getText().toString().trim();
        radious = radios.getText().toString().trim();
            if(cbAnywhere.isChecked())
            {
                radious=cbAnywhere.getText().toString();
        }
        else
        {
            radious=radios.getText().toString();

        }
//        minRate = edMinRate.getText().toString().trim();

        if (TextUtils.isEmpty(machineNo)) {
            macNo.setError(getString(R.string.error_field_required));
            focusView = macNo;
            flag = false;
        }
        if (flag_cocunut_tree) {
            if (TextUtils.isEmpty(specification1)) {
                macSpec1.setError(getString(R.string.error_field_required));
                focusView = macSpec1;
                flag = false;
            }
            if (TextUtils.isEmpty(specification2)) {
                macSpec2.setError(getString(R.string.error_field_required));
                focusView = macSpec2;
                flag = false;
            }
            if (TextUtils.isEmpty(specification3)) {
                macSpec3.setError(getString(R.string.error_field_required));
                focusView = macSpec3;
                flag = false;
            }
        }
        if (TextUtils.isEmpty(ratePerDay)) {
            macRPD.setError(getString(R.string.error_field_required));
            focusView = macRPD;
            flag = false;
        }
        if (TextUtils.isEmpty(ratePerHour)) {
            macRPH.setError(getString(R.string.error_field_required));
            focusView = macRPH;
            flag = false;
        }
        if (TextUtils.isEmpty(transportRate)) {
            edTransportRate.setError(getString(R.string.error_field_required));
            focusView = edTransportRate;
            flag = false;
        }
//        if (TextUtils.isEmpty(ra)) {
//            edTransportRate.setError(getString(R.string.error_field_required));
//            focusView = edTransportRate;
//            flag = false;
//        }
//        if (TextUtils.isEmpty(minRate)) {
//            edMinRate.setError(getString(R.string.error_field_required));
//            focusView = edMinRate;
//            flag = false;
//        }

        if (!flag) {
            focusView.requestFocus();
            return flag;
        } else {
            return flag;
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }
}
