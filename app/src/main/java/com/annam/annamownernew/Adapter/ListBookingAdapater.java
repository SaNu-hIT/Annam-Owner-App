package com.annam.annamownernew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.annam.annamownernew.Model.TripModel;
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.R;

import java.util.List;

/**
 * Created by SFT on 15/10/2016.
 */
public class ListBookingAdapater extends RecyclerView.Adapter<ListBookingAdapater.MyHolder>{
    private int prevPosition=0;
    private List<TripModel> listData;        //if lstData then
    private LayoutInflater inflater;

    AcceptDeclAin acceptDeclAin;
    public void setItemClickCallback(AcceptDeclAin acceptDeclAin) {
        this.acceptDeclAin = acceptDeclAin;
    }


    public ListBookingAdapater(List<TripModel> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        //lstData=listData;// both can be listData.... bt just to knw... so (this) is used other wise it can be avoided.
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tripitem, parent, false);
        //Log.i("Maro", "CREATED A VIEW");
        return new MyHolder(view);


    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        TripModel item = listData.get(position);
        holder.bukID.setText(item.getBooking_id());
        holder.machineName.setText(item.getMachine_name());
        holder.machineType.setText(item.getMachine_type());
        holder.location.setText(item.getFarmer_loc());
        holder.machineNo.setText(item.getMachine_id());
        holder.date.setText(item.getDate_and_time());
        if(item.getRate().equals("0") || item.getRate().equals(""))
        {
            holder.estmate_amount_layot.setVisibility(View.GONE);

        }   if(item.getLand_condition().equals("0") || item.getLand_condition().equals(""))
        {
            holder.land_condition_lyout.setVisibility(View.GONE);

        }
        holder.estAmount.setText(item.getRate());
        holder.trip_land_condition.setText(item.getLand_condition());
        if(item.getExptime().equals(""))
        {
            holder.expTime.setVisibility(View.GONE);
        }
        else
        {
            holder.expTime.setText(item.getExptime());
        }

        if(item.getLand_area().equals("0") || item.getLand_area().equals(""))
        {
            holder.landarea_layout.setVisibility(View.GONE);

        }

        holder.trip_land_area.setText(item.getLand_area()+"Cents");
        holder.ownerName.setText(item.getCustomer_name());
        holder.ownerPhno.setText(item.getCustomer_phone());


        holder.td_btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                acceptDeclAin.acceptClick(listData.get(position).getBooking_id(),listData.get(position).getMachine_id());
            }
        });

    holder.td_btndecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptDeclAin.declainClick(listData.get(position).getBooking_id(),listData.get(position).getMachine_id());



            }
        });




    }
    /*
    private String CUSTOMER_NAME;
    private String CUSTOMER_PHNO;
   */
    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private MyTextView bukID;
        private MyTextView machineName;
        private MyTextView machineType;
        private MyTextView location;
        private MyTextView machineNo;
        private MyTextView trip_land_area;

        private MyTextView date;
        private MyTextView estAmount;
        private MyTextView expTime;
        private MyTextView ownerName;
        private MyTextView ownerPhno;
        private MyTextView trip_land_condition;
        LinearLayout landarea_layout,estmate_amount_layot,land_condition_lyout;
Button td_btnaccept;
Button td_btndecline;
//        private MyTextView tvDays, tvHours, tvMints;
//        private EditText edDays, edHours, edMints;

        //        private ImageView secondary_icon;


        public MyHolder(View rootView) {
            super(rootView);


            bukID = (MyTextView) itemView.findViewById(R.id.td_bookingid);
            machineName = (MyTextView) itemView.findViewById(R.id.td_machinename);
            machineType = (MyTextView) itemView.findViewById(R.id.td_machinetype);
            location = (MyTextView) itemView.findViewById(R.id.td_location);
            machineNo = (MyTextView) itemView.findViewById(R.id.td_machineno);
            date = (MyTextView) itemView.findViewById(R.id.td_date);
            trip_land_area = (MyTextView) itemView.findViewById(R.id.trip_land_area);
            estAmount = (MyTextView) itemView.findViewById(R.id.td_amount);
            expTime = (MyTextView) itemView.findViewById(R.id.td_exptime);
            ownerName = (MyTextView) itemView.findViewById(R.id.td_ownername);
            ownerPhno = (MyTextView) itemView.findViewById(R.id.td_ownerphone);
            td_btnaccept = (Button) itemView.findViewById(R.id.td_btnaccept);
            td_btndecline = (Button) itemView.findViewById(R.id.td_btndecline);
            trip_land_condition = (MyTextView) itemView.findViewById(R.id.trip_land_condition);
            landarea_layout = (LinearLayout) itemView.findViewById(R.id.landarea_layout);
            estmate_amount_layot = (LinearLayout) itemView.findViewById(R.id.estmate_amount_layot);
            land_condition_lyout = (LinearLayout) itemView.findViewById(R.id.land_condition_lyout);

        }


    }




    public interface AcceptDeclAin {
        void acceptClick(String bookingID,String machaneid);

        void declainClick(String bookingID,String machaneid);
        //void onSecondaryIconClick(int p);
    }

}

