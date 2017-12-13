package com.annam.annamownernew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.annam.annamownernew.Model.KarshagasenaDetailsBean;
import com.annam.annamownernew.customfonts.MyTextView;
import com.annam.annamownernew.R;

import java.util.List;

/**
 * Created by Anjush on 8/10/2016.
 */

public class KarshagasenaAdapter extends RecyclerView.Adapter<KarshagasenaAdapter.FirstPageHolder> {
    private List<KarshagasenaDetailsBean> listData;        //if lstData then
    private LayoutInflater inflater;
    private ItemClickCallback itemClickCallback;
    private Context context;

    public interface ItemClickCallback {
        void onEditClick(int p);

        void onDeleteClick(int p);

        //void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }



    public KarshagasenaAdapter(List<KarshagasenaDetailsBean> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.context = c;
    }

    @Override
    public FirstPageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_karshagasena_design, parent, false);
        return new FirstPageHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstPageHolder holder, int position) {
        KarshagasenaDetailsBean item = listData.get(position);

        holder.karshakasena_id.setText(item.getKarshgasenaid());
        holder.owner_id.setText(item.getOwnerid());
        holder.title.setText(item.getTitile());
        holder.nature_of_work.setText(item.getNatureofwork());
        holder.radious.setText("Radious :"+item.getRadious());
        holder.labours_no.setText("Labour :"+item.getLaborsno());
        holder.location.setText(item.getLocation());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class FirstPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyTextView karshakasena_id;
        private MyTextView owner_id;

        private MyTextView title;
        private MyTextView nature_of_work,radious,labours_no,location;


        private Button buttonDelete;
        private Button buttonEdit;

        private View container;


        public FirstPageHolder(View itemView) {
            super(itemView);
            karshakasena_id = (MyTextView) itemView.findViewById(R.id.karshakasena_id);

            owner_id = (MyTextView) itemView.findViewById(R.id.owner_id);
            title = (MyTextView) itemView.findViewById(R.id.title);
            nature_of_work = (MyTextView) itemView.findViewById(R.id.nature_of_work);


            radious = (MyTextView) itemView.findViewById(R.id.radious);
            labours_no = (MyTextView) itemView.findViewById(R.id.labours_no);
            location = (MyTextView) itemView.findViewById(R.id.location);


            buttonDelete = (Button) itemView.findViewById(R.id.button_delete);
            buttonEdit = (Button) itemView.findViewById(R.id.button_edit);
            buttonDelete.setOnClickListener(this);
            buttonEdit.setOnClickListener(this);
            container = itemView.findViewById(R.id.item_root);
            //container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_edit) {
                itemClickCallback.onEditClick(getAdapterPosition());
            }
            else {
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
