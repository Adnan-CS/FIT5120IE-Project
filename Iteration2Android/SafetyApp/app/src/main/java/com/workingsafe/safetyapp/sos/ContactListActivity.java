package com.workingsafe.safetyapp.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.TestimonialActivity;
import com.workingsafe.safetyapp.TestimonialAdapter;
import com.workingsafe.safetyapp.model.ContactPerson;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private ContactHelper contactHelper;
    private List<ContactPerson> contactPersonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerView = findViewById(R.id.contactRecViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactListActivity.this));
        contactHelper = new ContactHelper(this);
        contactPersonList = new ArrayList<>();
        //After getting data
        FetchContactTask fetchContactTask = new FetchContactTask();
        fetchContactTask.execute();
        new ItemTouchHelper(itemTouchHelperCallBck).attachToRecyclerView(recyclerView);
    }
    private class FetchContactTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {

            contactPersonList = contactHelper.getAllCotacts();
            if(contactPersonList!=null && contactPersonList.size()>0){
                return true;
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean hasContact)
        {
            if(hasContact){
                contactListAdapter = new ContactListAdapter(ContactListActivity.this,contactPersonList);
                recyclerView.setAdapter(contactListAdapter);
            }
        }
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallBck = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            contactPersonList.remove(viewHolder.getAdapterPosition());
            contactListAdapter.notifyDataSetChanged();
        }
    };
}