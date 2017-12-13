package com.annam.annamownernew;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.annam.annamownernew.Constants.ConsAddMachine;
import com.annam.annamownernew.Constants.ConsBooking;
import com.annam.annamownernew.Constants.ConsCheckBooking;
import com.annam.annamownernew.Constants.ConsLogin;
import com.annam.annamownernew.Fragments.AboutFragment;
import com.annam.annamownernew.Fragments.Bill_Fragment;
import com.annam.annamownernew.Fragments.BookingFragment;
import com.annam.annamownernew.Fragments.ChangePassword_Fragment;
import com.annam.annamownernew.Fragments.KarshagasenaFragment;
import com.annam.annamownernew.Fragments.Owner_Home_Fragment;
import com.annam.annamownernew.Fragments.TripDetails_Fragment_new;
import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.Model.MachineDetails_List;
import com.annam.annamownernew.Model.OwnerInfo;
import com.annam.annamownernew.Ui.AddKarshakasenaActivity;
import com.annam.annamownernew.Ui.AddMachineActivity;
import com.annam.annamownernew.Utilities.CustomTypefaceSpan;
import com.annam.annamownernew.Utilities.Utilities;

import com.annam.annamownernew.Utilities.Utils2;
import com.annam.annamownernew.customfonts.MyTextViewHead;
import com.nex3z.notificationbadge.NotificationBadge;
import com.securepreferences.SecurePreferences;
import com.annam.annamownernew.Fragments.Profile_Fragment;

import com.annam.annamownernew.Ui.AboutActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SFT on 8/10/2016.
 */
public class Owner_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BillingProcessor.IBillingHandler {

    public static Toolbar toolbar;
    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    Fragment fragment;
    LayerDrawable icon;
    public static String base64encoded="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA70SZETzrxi13Opu3DGpvlNKFnCfXL0zPjh+l8JAqSJFWH6HtgHG7jY+EWpv+VCt42jK+itxG/cH8e2DkM7du/j1BJAt7EgFmfW1Kxsn7IgScFXAQRSqDg6ZbEZrEZg1gFtl8Z3C476aHHOX5b+uovZEF3tPwK1snd+C7fGDCNjGMjI45cvGDI7uxciK8IbxxxO0QSTx6fV9wrClnLi0bSid4lrsUIioKptmepdIE/QpEWFRQS5XgAXwMhhacVcJUPUkgtFoil0f17Hir861Spj3TlCO15/nEAewsG57HYt1EJTXZnBtAWExQpBWZ6tcRomP1Q4vpLu+RKqSdnpBYxwIDAQAB";
    private CircleImageView addimagebutton;

    public void addMachine(View view){

        //do subscription here
        Log.e( "addMachine: ","click" );

//        bp.purchase(Owner_Home.this,"a2_.");
        Intent i = new Intent(this, AddMachineActivity.class);
        startActivity(i);
        Owner_Home.this.finish();
    }

    public void addKarshakasena(View view){

        Intent i = new Intent(this, AddKarshakasenaActivity.class);
        startActivity(i);
//        Owner_Home.this.finish();
    }
    FrameLayout homemaincontainer;

    MyTextViewHead MyTextViewHead;
    ImageView notification_image;
    BillingProcessor bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_owner_home);

        bp = new BillingProcessor(this, base64encoded, this);

homemaincontainer= (FrameLayout) findViewById(R.id.homemaincontainer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Annam");
        notification_image= (ImageView) toolbar.findViewById(R.id.action_notifications);
//        getMachaneDetails();
//        mBadge= (NotificationBadge) findViewById(R.id.badge);
        checkBooking();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }else
                {
                    drawer.isDrawerOpen(GravityCompat.START);
                }
            }
        });
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        MyTextViewHead =(MyTextViewHead)headerView.findViewById(R.id.personname_id);
        addimagebutton =(CircleImageView)headerView.findViewById(R.id.addimagebutton);
        MyTextViewHead.setText(OwnerInfo.getOwnerName());
//        MyTextViewHead.setText("hai");


        fragment = new Owner_Home_Fragment();


        addimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                fragment = new Profile_Fragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "Profile_Fragment", Owner_Home.this);

            }
        });


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
//        Utilities.getInstance(Owner_Home.this).changeHomeFragment(
//                fragment, "Owner_Home_Fragment", Owner_Home.this);


        if (savedInstanceState == null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.add(homemaincontainer.getId(), fragment,"HomeFragment").commit();
        }

//
//        notification_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fragment = new TripDetails_Fragment_new();
//                Bundle args = new Bundle();
//                args.putString("bookingID", "123");
//                fragment.setArguments(args);
//                Utilities.getInstance(Owner_Home.this).changeHomeFragment(
//                        fragment, "BookingFragment", Owner_Home.this);
//
//            }
//        });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        //Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, "myFragmentName", fragment);
//    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.homemaincontainer);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ((f instanceof Bill_Fragment)
                || (f instanceof ChangePassword_Fragment)
                || (f instanceof Profile_Fragment)
                || (f instanceof TripDetails_Fragment_new)
                || (f instanceof BookingFragment)
                )
        {
                    Fragment fragment = new Owner_Home_Fragment();
                    Utilities.getInstance(this).changeHomeFragment(
                    fragment, "Owner_Home_Fragment", this);
        }
        else if ((f instanceof Owner_Home_Fragment)) {
            showalert();
        } else {
            super.onBackPressed();
        }
    }

    private void showalert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        MenuItem item = menu.findItem(R.id.action_notifications);

         icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                fragment = new TripDetails_Fragment_new();
                Bundle args = new Bundle();
                //dummy data booking id ,it is used for argument passing in fragments
                args.putString("bookingID", "123");
                fragment.setArguments(args);
                Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                        fragment, "BookingFragment", Owner_Home.this);
                return false;
            }
        });
        Utils2.setBadgeCount(this, icon, 2);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+917560812281"));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 124);
            } else {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return true;
                }
                startActivity(intent);
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 124) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.CALL_PHONE &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finish();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+917560812281"));
                startActivity(intent);
            }
        }
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Amaranth-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home){
            fragment = new Owner_Home_Fragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "Owner_Home_Fragment", Owner_Home.this);

        }
//        else if (id == R.id.nav_profilesettings){
//            fragment = new Profile_Fragment();
//            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
//                    fragment, "Profile_Fragment", Owner_Home.this);
//        }
        else if (id == R.id.nav_changepassword){
            fragment = new ChangePassword_Fragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "ChangePassword_Fragment", Owner_Home.this);
        }
        else if (id == R.id.nav_bookings){
            fragment = new BookingFragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "BookingFragment", Owner_Home.this);
        }else if (id == R.id.nav_about){

            fragment = new AboutFragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "BookingFragment", Owner_Home.this);
//            Intent i = new Intent(this, AboutActivity.class);
//            startActivity(i);
//            Owner_Home.this.finish();
        }

        else if (id == R.id.nav_karshagasena){
            fragment = new KarshagasenaFragment();
            Utilities.getInstance(Owner_Home.this).changeHomeFragment(
                    fragment, "KarshgasenaFragment", Owner_Home.this);

        }
        else if (id == R.id.nav_logout){
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Sign out ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                String PREFS_LOGIN = "login";
                SharedPreferences.Editor editor = new SecurePreferences(getApplicationContext(), "AnnamAgroTech", PREFS_LOGIN).edit();
                editor.putBoolean("hasLoggedIn", false);
                editor.remove("username");
                editor.remove("password");
                //editor.putString("name", login_model.getUsername());
                editor.commit();
                Intent intent=new Intent(getBaseContext(),Login.class);
                startActivity(intent);
                finish();
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

    private void getMachaneDetails() {
        String url = ConsBooking.VIEWMACHANE + OwnerInfo.getOwnerId();
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONCE", response);
                setJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                JSONArray JA_machine_details = jsonObject.getJSONArray(ConsAddMachine.JSON_ARRAY_MACHINE_DETAILS);
                MachineDetails_List.serverData = new ArrayList<MachineDetails>();
                for (int i = 0; i < JA_machine_details.length(); i++) {
                    JSONObject machine_details = JA_machine_details.getJSONObject(i);
                    setMachineDetails(machine_details);
                }

            } else {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkBooking() {
        String url = ConsCheckBooking.URL + OwnerInfo.getOwnerId();


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONCE", response);
                if (!response.equals("0")) {

                    showNotficationCount(1);
                } else {
                    showNotficationCount(0);


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
//CHange the methord also
    private void showNotficationCount(int count) {
        Utils2.setBadgeCount(this, icon, count);
//        if(count<1)
//        {
////            Utils2.setBadgeCount(this, icon, count);
//        }
//        else
//        {
//            Utils2.setBadgeCount(this, icon, count);
////            mBadge.setVisibility(View.GONE);
//        }




    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                Intent i = new Intent(this, AddMachineActivity.class);
        startActivity(i);
        Owner_Home.this.finish();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Log.e( "onBillingError: ", ""+errorCode);
    }

    @Override
    public void onBillingInitialized() {
        Log.e( "onBillingError: ", "onBillingInitialized");

    }
    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
