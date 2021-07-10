package com.quickblox.sample.videochat.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.models.NotiInfo;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    List<NotiInfo> notiInfos;


    private ItemClicked mItemClicked;


    public interface  ItemClicked {

        void call(int itemPosition);
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_noti_list, viewGroup, false);

        return new NotificationListAdapter.MyViewHolder(itemView);
    }

    public NotificationListAdapter(List<NotiInfo> callInformation, ItemClicked itemClicked){
        this.notiInfos =callInformation;
        this.mItemClicked=itemClicked;

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {

        final NotiInfo notiInfo= notiInfos.get(i);


        try{

            if(!notiInfo.name.equals(""))
                myViewHolder.notiText.setText(notiInfo.name);

        }catch (Exception ex){
            myViewHolder.notiText.setText("Unknown");

        }


        try {
            myViewHolder.readImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    mItemClicked.call(i);
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return notiInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notiText;
        ImageView readImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            notiText =(TextView)itemView.findViewById(R.id.notification_text);
            readImage =(ImageView) itemView.findViewById(R.id.notification_background);

        }
    }
}
