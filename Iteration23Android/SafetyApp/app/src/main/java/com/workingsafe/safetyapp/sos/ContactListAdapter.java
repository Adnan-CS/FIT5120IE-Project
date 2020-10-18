package com.workingsafe.safetyapp.sos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.TestimonialAdapter;
import com.workingsafe.safetyapp.model.ContactPerson;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListVH> {

    private Context context;
    private List<ContactPerson> contactPeople;
    public ContactListAdapter(Context context, List<ContactPerson> contactPersonArrayList){
        this.context = context;
        this.contactPeople = contactPersonArrayList;
    }

    @NonNull
    @Override
    public ContactListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contactitem_row, parent, false);
        return new ContactListAdapter.ContactListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListVH holder, int position) {
        TextView contactNameTxt = holder.contactName;
        TextView contactNumbTxt = holder.contactNumber;

        contactNameTxt.setText(contactPeople.get(position).getName());
        contactNumbTxt.setText(contactPeople.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return contactPeople.size();
    }

    public class ContactListVH extends RecyclerView.ViewHolder {

        TextView contactName;
        TextView contactNumber;

        ConstraintLayout rowLayout;

        public ContactListVH(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactNameId);
            contactNumber = itemView.findViewById(R.id.contactNumberId);
            rowLayout = itemView.findViewById(R.id.contactItemRowLayout);
        }
    }
}
