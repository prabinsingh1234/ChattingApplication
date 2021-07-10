package com.quickblox.sample.videochat.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.models.ContactModels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactHistoryAdapter extends RecyclerView.Adapter<ContactHistoryAdapter.MyViewHolder> {

    HashSet<ContactModels> mContacts;

    List<ContactModels> contactsFiltered;


    ContactAdapterInterface mContactInterface;

    List<ContactModels> mContacts2;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_contacts_info, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    public ContactHistoryAdapter(List<ContactModels> contactData, ContactAdapterInterface mContactInterface) {
        this.mContactInterface = mContactInterface;

        mContacts = new HashSet<ContactModels>();
        mContacts.addAll(contactData);

         SortedSet s=new TreeSet();
         s.addAll(mContacts);

        contactsFiltered=new ArrayList<>(mContacts);

    }

    public void filter(List<ContactModels> contactsList){

        mContacts = new HashSet<ContactModels>();
        mContacts.addAll(contactsList);

        contactsFiltered=new ArrayList<>(mContacts);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        if (i<contactsFiltered.size()) {
            try {
                final ContactModels contactModels=contactsFiltered.get(i);

                if(contactModels.name.equals("") || contactModels.name.equals("Username")) {
                    myViewHolder.mTextUsername.setVisibility(View.GONE);
                }
                if(contactModels.mobileNumber.equals("")) {
                    myViewHolder.mUserPhoneNumber.setVisibility(View.GONE);
                }else{
                    myViewHolder.mUserPhoneNumber.setVisibility(View.VISIBLE);
                    myViewHolder.mUserPhoneNumber.setText(contactModels.mobileNumber);
                }
                myViewHolder.mUserPhoneNumber.setVisibility(View.VISIBLE);
                myViewHolder.mUserPhoneNumber.setText(contactModels.mobileNumber);

                myViewHolder.mTextUsername.setText(contactModels.name);
                //  myViewHolder.mImage.setImageURI(contactModels.photoURI);
                myViewHolder.mImage.setImageBitmap(contactModels.photo);
                System.out.println(contactModels.name);
                // myViewHolder.mImage.setImageURI(contactModels.photoURI);

                myViewHolder.mButtonDial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mContactInterface.clickedItem(contactModels);


                    }
                });

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextUsername,mUserPhoneNumber;
        ImageButton mButtonDial;
        CircleImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage =(CircleImageView)itemView.findViewById(R.id.img);
            mTextUsername=(TextView)itemView.findViewById(R.id.tv_rwcontactinfo_etUsername);
            mUserPhoneNumber=(TextView)itemView.findViewById(R.id.tv_rwcontactinfo_number);
            mButtonDial=(ImageButton)itemView.findViewById(R.id.btn_rwcontactinfo_dial);

        }
    }

    public interface  ContactAdapterInterface{

        void clickedItem(ContactModels contactModels);
    }
}
