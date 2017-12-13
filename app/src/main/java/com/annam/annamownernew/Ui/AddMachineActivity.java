package com.annam.annamownernew.Ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.Model.MachineType_List;
import com.annam.annamownernew.customfonts.MyEditText;
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.Constants.ConsAddMachine;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.MachineType;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Owner_Home;
import com.annam.annamownernew.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AddMachineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,BillingProcessor.IBillingHandler{
    boolean flagCocunut_selected = true;
    MyTextView rate_per_hours, day;
    LinearLayout specificatns;
    List<String> machineNames;
    String machineType = null, machineName = null, machineNo, specification1, specification2, specification3, ratePerDay, ratePerHour, vehicleFuelNDriverType;
    String radious = "Anywhere", pickup  = "false", transportRate, minRate;
    String[] machineNameID;
    Button save_button;
    private MyEditText mach_no;
    private MyEditText spec1, spec2, spec3;
    private MyEditText rate_per_day;
    private MyEditText rate_per_hour;
    private MyEditText edTransportRate,radios;
    private Button b_save;
    private Spinner spinner, spinner2, spinner3;
    private CheckBox cbPanchyath, cbBlock, cbDistrict, cbAnywhere, cbPickupYES, cbPickupNO;
    BillingProcessor bp;
//    public void AddMachSave(View view) {
//addMachine(); modified by saneesh
//    }
    private ProgressDialog loading;
    private String subscriptioncode="";
    List<String> subscriptioncodes;

    public void AddMachCancel(View view) {
        Toast.makeText(this, "CANCEL CLICKED", Toast.LENGTH_SHORT).show();
    }
    public static String base64encoded="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA70SZETzrxi13Opu3DGpvlNKFnCfXL0zPjh+l8JAqSJFWH6HtgHG7jY+EWpv+VCt42jK+itxG/cH8e2DkM7du/j1BJAt7EgFmfW1Kxsn7IgScFXAQRSqDg6ZbEZrEZg1gFtl8Z3C476aHHOX5b+uovZEF3tPwK1snd+C7fGDCNjGMjI45cvGDI7uxciK8IbxxxO0QSTx6fV9wrClnLi0bSid4lrsUIioKptmepdIE/QpEWFRQS5XgAXwMhhacVcJUPUkgtFoil0f17Hir861Spj3TlCO15/nEAewsG57HYt1EJTXZnBtAWExQpBWZ6tcRomP1Q4vpLu+RKqSdnpBYxwIDAQAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_machine);
        mach_no = (MyEditText) findViewById(R.id.mac_no);
        spec1 = (MyEditText) findViewById(R.id.specificatn);
        spec2 = (MyEditText) findViewById(R.id.specificatn2);
        spec3 = (MyEditText) findViewById(R.id.specificatn3);
        bp = new BillingProcessor(this, base64encoded, this);

        rate_per_hours = (MyTextView) findViewById(R.id.rate_per_hours);
        day = (MyTextView) findViewById(R.id.rateper_day);
        rate_per_day = (MyEditText) findViewById(R.id.rate_per_day);
        rate_per_hour = (MyEditText) findViewById(R.id.rate_per_hour);
        radios = (MyEditText) findViewById(R.id.radios);
        specificatns = (LinearLayout) findViewById(R.id.specificatns);
        b_save = (Button) findViewById(R.id.save_button);
        cbPanchyath = (CheckBox) findViewById(R.id.cb_panchayath);
        cbBlock = (CheckBox) findViewById(R.id.cb_block);
        cbDistrict = (CheckBox) findViewById(R.id.cb_district);
        cbAnywhere = (CheckBox) findViewById(R.id.cb_anywhere);
        cbPickupYES = (CheckBox) findViewById(R.id.cb_pickup_yes);

        cbPickupNO = (CheckBox) findViewById(R.id.cb_pickup_no);
        save_button = (Button) findViewById(R.id.save_button);



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
                    cbPanchyath.setChecked(false);
                    cbBlock.setChecked(false);
                    cbDistrict.setChecked(false);
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
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMachine();


            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        edTransportRate = (MyEditText) findViewById(R.id.ed_transport_rate);

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3 = (Spinner) findViewById(R.id.v_f_d_status);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.machine_spinner, android.R.layout.simple_spinner_dropdown_item);
//        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(this);
        //mach_no.getText();
        List<String> machineTypeNames = new ArrayList<>();
        machineTypeNames.add("Select Machine Type");
        for (MachineType model : MachineType_List.serverData) {
            machineTypeNames.add(model.getNAME());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, machineTypeNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent == spinner) {
            String item = parent.getItemAtPosition(position).toString();
            if (!item.equals("Select Machine Type")) {
                String mTypeID = MachineType_List.serverData.get(position - 1).getMachineTypeId();
                machineType = mTypeID;
                getMachines(mTypeID);
            } else {
                spinner2.setAdapter(null);
            }

            if (item.equals("Karshaka Sena")) {

//                flagCocunut_selected = false;
//                specificatns.setVisibility(View.GONE);
//                rate_per_hours.setText("Rate per tree to clean");
//                day.setText("Rate per tree to climb");
//                rate_per_day.setHint("rate tree climb ");
//                rate_per_hour.setHint("rate tree clean ");


                ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.karshagasena_item, android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter);

            }
            else if (item.equals("Coconut Tree Climber")) {

                flagCocunut_selected = false;
                specificatns.setVisibility(View.GONE);
                rate_per_hours.setText("Rate per tree to clean");
                day.setText("Rate per tree to climb");
                rate_per_day.setHint("rate tree climb ");
                rate_per_hour.setHint("rate tree clean ");
                ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.cocunut_tree_item, android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter);

            } else {
                flagCocunut_selected = true;
                specificatns.setVisibility(View.VISIBLE);
                rate_per_hours.setText("Rate Per Hour");
                day.setText("Rate Per Day");
                rate_per_day.setHint("Rate Per Day");
                rate_per_hour.setHint("rate per hours");
                ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.machine_spinner, android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(adapter);

            }
        }
        if (parent == spinner2) {
            String item = parent.getItemAtPosition(position).toString();
            machineName = machineNameID[position];
            subscriptioncode = subscriptioncodes.get(position);
            Log.e( "subscript selected:","" +subscriptioncode);
        }
        if (parent == spinner3) {

            Log.e("ID", "" + id);
            Log.e("ID", "" + position);
            vehicleFuelNDriverType = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getMachines(String mTypeID) {
        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        String url = ConsAddMachine.URL + mTypeID;
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
                        Toast.makeText(getApplicationContext(), "Volley Error " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsAddMachine.ERROR_CODE);
            if (data_avail.equals("Success")) {
                JSONArray JA_machinery_details = jsonObject.getJSONArray(ConsAddMachine.MACHINERY_DETAILS);
                machineNames = new ArrayList<>();
                subscriptioncodes = new ArrayList<>();
                machineNameID = new String[JA_machinery_details.length()];
                for (int i = 0; i < JA_machinery_details.length(); i++) {
                    JSONObject machinetype = JA_machinery_details.getJSONObject(i);
                    machineNames.add(machinetype.getString("machine_name"));
                    machineNameID[i] = machinetype.getString("machine_id");

                   subscriptioncodes.add(machinetype.getString("subscription_Code"));

                    Log.e( "subscriptioncode: ",""+machinetype.getString("subscription_Code") );
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, machineNames);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(dataAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addMachine() {
        if (validate()) {
if(subscriptioncode.equals("")||subscriptioncode.equals(null))
{
    MakeAddmachineRequest();
}else
{
    bp.purchase(AddMachineActivity.this,"a2_.");
}






        } else {
            Toast.makeText(this, "Please enter valid info", Toast.LENGTH_LONG).show();
        }

    }

    private void MakeAddmachineRequest() {

        if(radios.getText().toString().equals(""))

        {
            radious="Anywhere";
        }
        else
        {
            radious=radios.getText().toString();
        }


        Log.e("radious", ""+radious);

        String tempURL = ConsAddMachine.AddURL + OwnerInfo.getOwnerId() + "&machine_type="
                + machineType + "&machine_id=" + machineName + "&machine_no=" + machineNo
                + "&specification1=" + specification1
                + "&specification2=" + specification2
                + "&specification3=" + specification3
                + "&vehicleFuelNDriverType=" + vehicleFuelNDriverType
                + "&rate_per_day=" + ratePerDay
                + "&rate_per_hour=" + ratePerHour
                + "&radious=" + radious
                + "&pickup=" + pickup
                + "&transportrate=" + transportRate;

        Log.e("tempURL", tempURL);
        String url = fixURL(tempURL);

        Log.e("fixURL", url);
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
                        Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setJSON(String response) {
        String data_avail = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            data_avail = jsonObject.getString(ConsAddMachine.ERROR_CODE);
            if (data_avail.equals("Success")) {
                Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
                JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
                MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                for (int i = 0; i < JA_machine_details.length(); i++) {
                    JSONObject machine_details = JA_machine_details.getJSONObject(i);
                    setMachineDetails(machine_details);
                }
                Intent ownerHome = new Intent(getApplicationContext(), Owner_Home.class);
                startActivity(ownerHome);
                AddMachineActivity.this.finish();

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
//            md.setMachineDesc(machine_details.getString(ConsLogin.MD_MACHINE_DESC));
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

        machineNo = mach_no.getText().toString().trim();
        specification1 = spec1.getText().toString().trim();
        specification2 = spec2.getText().toString().trim();
        specification3 = spec3.getText().toString().trim();
        ratePerDay = rate_per_day.getText().toString().trim();
        ratePerHour = rate_per_hour.getText().toString().trim();
        transportRate = edTransportRate.getText().toString().trim();
//        minRate = edMinRate.getText().toString().trim();

        if (machineName == null || machineType == null) {
            Toast.makeText(this, "Please select your Machine", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(machineNo)) {
            mach_no.setError(getString(R.string.error_field_required));
            focusView = mach_no;
            flag = false;
        }
        if (flagCocunut_selected) {
//
//            if (TextUtils.isEmpty(specification1)) {
//                spec1.setError(getString(R.string.error_field_required));
//                focusView = spec1;
//                flag = false;
//            }
//            if (TextUtils.isEmpty(specification2)) {
//                spec2.setError(getString(R.string.error_field_required));
//                focusView = spec2;
//                flag = false;
//            }
//            if (TextUtils.isEmpty(specification3)) {
//                spec3.setError(getString(R.string.error_field_required));
//                focusView = spec3;
//                flag = false;
//            }

        }


//        if (TextUtils.isEmpty(ratePerDay)) {
//            rate_per_day.setError(getString(R.string.error_field_required));
//            focusView = rate_per_day;
//            flag = false;
//        }
//        if (TextUtils.isEmpty(ratePerHour)) {
//            rate_per_hour.setError(getString(R.string.error_field_required));
//            focusView = rate_per_hour;
//            flag = false;
//        }
        if (TextUtils.isEmpty(transportRate)) {
            edTransportRate.setError(getString(R.string.error_field_required));
            focusView = edTransportRate;
            flag = false;
        }
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

    @Override
    public void onBackPressed() {
        Intent ownerHome = new Intent(getApplicationContext(), Owner_Home.class);
        startActivity(ownerHome);
        AddMachineActivity.this.finish();
    }

    public void addMachine(View view) {
        onBackPressed();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        MakeAddmachineRequest();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        DisplyMessage("Subscription Error");

    }

    @Override
    public void onBillingInitialized() {

    }

    private void DisplyMessage(String message) {

        SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text(message) // text to be displayed
                        .textColor(Color.WHITE) // change the text color
                        .color(Color.RED) // change the background color

                        .actionColor(Color.BLACK) // action button label color
                        // action button's ActionClickListener
                , this); // activity where it is displayed
    }
}
