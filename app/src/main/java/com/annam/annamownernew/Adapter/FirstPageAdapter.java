package com.annam.annamownernew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.annam.annamownernew.Model.MachineDetails;
import com.annam.annamownernew.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Anjush on 8/10/2016.
 */

public class FirstPageAdapter extends RecyclerView.Adapter<FirstPageAdapter.FirstPageHolder> {
    private List<MachineDetails> listData;        //if lstData then
    private LayoutInflater inflater;
    private ItemClickCallback itemClickCallback;
    private Context context;

    public FirstPageAdapter(List<MachineDetails> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.context = c;
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public FirstPageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.machanary_row, parent, false);
        return new FirstPageHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstPageHolder holder, int position) {
        MachineDetails item = listData.get(position);

        String imageURL = "http://annamagrotech.com/upload/vtype/" + item.getIMAGE();
        Log.e("IMAG URL", imageURL);
//        String imageURL =item.getIMAGE();
        URI uri = null;
        try {
            uri = new URI(imageURL.replaceAll(" ", "%20"));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        imageURL = String.valueOf(uri);
//
//
//        //String subtitle;
        String Avail;
        if (item.getMAVAILABLE().equals("1")) {
            Avail = "Available";
        } else {
            Avail = "Not Available";
        }
        /*subtitle = "Machine No   : " + item.getMachineNo() + System.getProperty("line.separator") +
                   "Machine Desc : " + item.getMachineDesc() + System.getProperty("line.separator") +
                   "Available    : " + Avail + System.getProperty("line.separator")
                    ;*/

        holder.macName.setText(item.getMachineName());
        holder.macNo.setText(item.getMachineNo());
        holder.macDesc.setText(item.getMachineDesc());
        holder.macAvail.setText(Avail);

        //Log.e("desc", item.getMachineDesc());


        Picasso.with(context)
                .load(imageURL)
                .error(R.drawable.error)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .resize(400, 400)
                .into(holder.macImage);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface ItemClickCallback {
        void onEditClick(int p);

        void onDeleteClick(int p);

        //void onSecondaryIconClick(int p);
    }

    class FirstPageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView macName;
        private TextView macNo;
        private ImageView macImage;
        private ImageView editImageview;
        private ImageView deleteImageview;
        private TextView macDesc;
        private TextView macAvail;
        private Button buttonDelete;
        private Button buttonEdit;

        private View container;


        public FirstPageHolder(View itemView) {
            super(itemView);
            macName = (TextView) itemView.findViewById(R.id.machine_name);
            macImage = (ImageView) itemView.findViewById(R.id.item_icon);
            macNo = (TextView) itemView.findViewById(R.id.machine_no);
            macDesc = (TextView) itemView.findViewById(R.id.machine_desc);
            macAvail = (TextView) itemView.findViewById(R.id.machine_avail);
            buttonDelete = (Button) itemView.findViewById(R.id.button_delete);

            editImageview = (ImageView) itemView.findViewById(R.id.delete_image);
            deleteImageview = (ImageView) itemView.findViewById(R.id.edit_image);

            buttonEdit = (Button) itemView.findViewById(R.id.button_edit);

                      editImageview.setOnClickListener(this);
                      deleteImageview.setOnClickListener(this);
            
            buttonDelete.setOnClickListener(this);
            buttonEdit.setOnClickListener(this);
            container = itemView.findViewById(R.id.item_root);
            //container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int s=getAdapterPosition();
//            if (v.getId() == R.id.button_edit) {
//
//
//                Log.e("POSITION",""+s);
//                itemClickCallback.onEditClick(getAdapterPosition());
//            } else {
//                Log.e("POSITION2",""+s);
//                itemClickCallback.onDeleteClick(getAdapterPosition());
//            }
//
 if (v.getId() == R.id.edit_image) {


                Log.e("POSITION",""+s);
                itemClickCallback.onEditClick(getAdapterPosition());
            } else if (v.getId() == R.id.delete_image){
                Log.e("POSITION2",""+s);
                itemClickCallback.onDeleteClick(getAdapterPosition());
            }
        }
    }
}
