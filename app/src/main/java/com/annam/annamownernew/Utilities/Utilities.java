package com.annam.annamownernew.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.annam.annamownernew.R;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by SFT on 9/10/2016.
 */
public class Utilities extends Fragment {
    private static Utilities mUtilities;
    public static int admin_id = 5;
    private static Context mContext;
    public  static String PREFS_LOGIN_STATUS = "LOGIN";
    public static Utilities getInstance(Context context) {

        mContext = context;
        if (mUtilities == null) {
            mUtilities = new Utilities();

        }
        return mUtilities;
    }

    public void changeHomeFragment(Fragment fragment, String tag,
                                   FragmentActivity act) {
        if (fragment != null) {
            FragmentManager fragmentManager = act.getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null);
            fragmentManager.beginTransaction()

                    .replace(R.id.homemaincontainer, fragment, null)
                    .commit();
        } else {
            // error in creating fragment
            Log.e("HomeActt",
                    "Error in creating fragment");
        }
    }


    public void clearBackStack(FragmentManager fm, FragmentActivity act) {
        int backStackCount = act.getSupportFragmentManager()
                .getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id.
            int backStackId = act.getSupportFragmentManager()
                    .getBackStackEntryAt(i).getId();

            // Log.e("popBackStack", getSupportFragmentManager()
            // .getBackStackEntryAt(i).getName());

            fm.popBackStack(backStackId,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }
//
//    public void changeChildFragment(Fragment fragment, String tag,
//                                    FragmentActivity act) {
//        if (fragment != null) {
//
//
//            FragmentManager fragmentManager = act.getSupportFragmentManager();
//            fragmentManager.beginTransaction().addToBackStack(null);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_signup_loginpage, fragment, null)
//                    .commit();
//
//        } else {
//            // error in creating fragment
//            Log.e("HomeActt",
//                    "Error in creating fragment");
//        }
//    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String getTempFile() {
        return Environment.getExternalStorageDirectory().getPath() + "/Androidhub4you/";

    }

    public static File createFileInSDCard(String path, String fileName) {
        File dir = new File(path);
        try {
            if (!dir.exists() && dir.mkdirs()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = null;
        try {
            if (dir.exists()) {
                file = new File(dir, fileName);
                file.createNewFile();
            } else {

            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return file;
    }

    public static Bitmap decodeFile(File f) {
        Bitmap b = null;
        final int IMAGE_MAX_SIZE = 400;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2.0, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (Exception e) {
        }
        return b;
    }


    public Boolean isNetAvailable() {

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
