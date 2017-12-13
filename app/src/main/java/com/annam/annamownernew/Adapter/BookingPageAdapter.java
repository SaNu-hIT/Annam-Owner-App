package com.annam.annamownernew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.Model.BookingItem;
import com.annam.annamownernew.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SFT on 15/10/2016.
 */
public class BookingPageAdapter extends RecyclerView.Adapter<BookingPageAdapter.MyHolder>{
    private int prevPosition=0;
    private List<BookingItem> listData;        //if lstData then
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onCancelClick(int p);
        void  onStartTrip(int p);
        void  onEndTrip(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public BookingPageAdapter(List<BookingItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;

        //lstData=listData;// both can be listData.... bt just to knw... so (this) is used other wise it can be avoided.

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.bookings_list_row, parent, false);
        //Log.i("Maro", "CREATED A VIEW");
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        BookingItem item = listData.get(position);
        holder.bukID.setText("Booking Id :"+item.getBOOKING_ID());
        holder.location.setText("Location :"+item.getLOCATION());
        holder.machineName.setText("Machine Name :"+item.getMACHINE_NAME());
        holder.machineType.setText("Machine Type :"+item.getMACHINE_TYPE());
//        holder.machineNo.setText("Machine No :"+item.getMACHINE_NO());
        holder.date.setText("Date :"+item.getDATE());
        holder.estAmount.setText("Estimated Amount :"+item.getEST_AMOUNT());
        holder.expTime.setText("Expected Date :"+item.getEXP_TIME());
        holder.ownerName.setText("Owner Name :"+item.getCUSTOMER_NAME());
        holder.ownerPhno.setText("Phone Number :"+item.getCUSTOMER_PHNO());
//        if (item.getBSTATUS().equals("On Going"))
//        {
////            holder.btstart.setVisibility(View.INVISIBLE);
////            holder.btcancel.setVisibility(View.INVISIBLE);
////            holder.btend.setVisibility(View.VISIBLE);
////            holder.tvDays.setVisibility(View.VISIBLE);
////            holder.tvHours.setVisibility(View.VISIBLE);
////            holder.tvMints.setVisibility(View.VISIBLE);
////            holder.edDays.setVisibility(View.VISIBLE);
////            holder.edHours.setVisibility(View.VISIBLE);
////            holder.edMints.setVisibility(View.VISIBLE);
//        }


        //Log.i("Maro", "ON THE POSITION " + position);
    }
    /*
    private String CUSTOMER_NAME;
    private String CUSTOMER_PHNO;
   */
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyTextView bukID;
        private MyTextView machineName;
        private MyTextView machineType;
        private MyTextView location;
        private MyTextView machineNo;

        private MyTextView date;
        private MyTextView estAmount;
        private MyTextView expTime;
        private MyTextView ownerName;
        private MyTextView ownerPhno;

//        private MyTextView tvDays, tvHours, tvMints;
//        private EditText edDays, edHours, edMints;

        //        private ImageView secondary_icon;
        private View container;
        private Button btcancel;
        private Button btstart;
        private Button btend;

        public MyHolder(View itemView) {
            super(itemView);

            bukID = (MyTextView) itemView.findViewById(R.id.td_bookingid);
            machineName = (MyTextView) itemView.findViewById(R.id.td_machinename);
            machineType = (MyTextView) itemView.findViewById(R.id.td_machinetype);
            location = (MyTextView) itemView.findViewById(R.id.td_location);
            machineNo = (MyTextView) itemView.findViewById(R.id.td_machineno);
            date = (MyTextView) itemView.findViewById(R.id.td_date);
            estAmount = (MyTextView) itemView.findViewById(R.id.td_amount);
            expTime = (MyTextView) itemView.findViewById(R.id.td_exptime);
            ownerName = (MyTextView) itemView.findViewById(R.id.td_ownername);
            ownerPhno = (MyTextView) itemView.findViewById(R.id.td_ownerphone);

//            tvDays = (MyTextView) itemView.findViewById(R.id.td_daystitle);
//            tvHours = (MyTextView) itemView.findViewById(R.id.td_hourstitle);
//            tvMints = (MyTextView) itemView.findViewById(R.id.td_mintstitle);
//
//            edDays = (EditText) itemView.findViewById(R.id.td_days);
//            edHours = (EditText) itemView.findViewById(R.id.td_hours);
//            edMints = (EditText) itemView.findViewById(R.id.td_mints);
//
//            tvDays.setVisibility(View.VISIBLE);
//            tvHours.setVisibility(View.VISIBLE);
//            tvMints.setVisibility(View.VISIBLE);
//            edDays.setVisibility(View.VISIBLE);
//            edHours.setVisibility(View.VISIBLE);
//            edMints.setVisibility(View.VISIBLE);



            btcancel = (Button) itemView.findViewById(R.id.td_btncancel);
            btcancel.setOnClickListener(this);
            btstart = (Button) itemView.findViewById(R.id.td_btnstart);
            btstart.setOnClickListener(this);
            btend = (Button) itemView.findViewById(R.id.td_btnend);
            btend.setOnClickListener(this);
            // secondary_icon = (ImageView) itemView.findViewById(R.id.item_icon_secondary);
            // secondary_icon.setOnClickListener(this);
            //We'll need the container later on, when we add an View.OnClickListener
            container = itemView.findViewById(R.id.item_root);
            // container.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.td_btncancel)
            {
                itemClickCallback.onCancelClick(getAdapterPosition());
            } else if(v.getId() == R.id.td_btnstart)
            {
                int ss=getAdapterPosition();
                itemClickCallback.onStartTrip(getAdapterPosition());
            }else
            {
                itemClickCallback.onEndTrip(getAdapterPosition());

            }
        }
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }//
}

