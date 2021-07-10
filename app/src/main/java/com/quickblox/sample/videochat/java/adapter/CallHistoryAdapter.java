package com.quickblox.sample.videochat.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.models.CallInfo;

import java.util.List;

public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.MyViewHolder> {

    List<CallInfo> callInformation;


    private ItemClicked mItemClicked;


    public interface  ItemClicked {

        void call(int itemPosition);
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_call_history, viewGroup, false);

        return new CallHistoryAdapter.MyViewHolder(itemView);
    }

    public CallHistoryAdapter(List<CallInfo> callInformation, ItemClicked itemClicked){
        this.callInformation=callInformation;
        this.mItemClicked=itemClicked;

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {

        final CallInfo callInfo=callInformation.get(i);


        try{

            if(!callInfo.name.equals(""))
                myViewHolder.mUserName.setText(callInfo.name);

        }catch (Exception ex){
            myViewHolder.mNumber.setText(callInfo.name);
            myViewHolder.mUserName.setText("Unknown");

        }


        myViewHolder.mNumber.setText(callInfo.number);

        try {
            myViewHolder.mBtnCall.setOnClickListener(new View.OnClickListener() {

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
        return callInformation.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mUserName,mNumber;
        ImageButton mBtnCall;

        public MyViewHolder(View itemView) {
            super(itemView);

            mUserName=(TextView)itemView.findViewById(R.id.tv_contact_username);
            mNumber=(TextView)itemView.findViewById(R.id.tv_contact_phonenumber);
            mBtnCall=(ImageButton)itemView.findViewById(R.id.btn_contacthistory_call);

        }
    }
}
