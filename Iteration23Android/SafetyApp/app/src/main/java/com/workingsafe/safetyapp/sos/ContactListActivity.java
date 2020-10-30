package com.workingsafe.safetyapp.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.workingsafe.safetyapp.MainActivity;
import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.TestimonialActivity;
import com.workingsafe.safetyapp.TestimonialAdapter;
import com.workingsafe.safetyapp.model.ContactPerson;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class ContactListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactListAdapter contactListAdapter;
    private ContactHelper contactHelper;
    private List<ContactPerson> contactPersonList;
    private FButton addContactNavBtn;
    private TextView emgContactTxtMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        recyclerView = findViewById(R.id.contactRecViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactListActivity.this));
        contactHelper = new ContactHelper(this);
        contactPersonList = new ArrayList<>();
        addContactNavBtn = findViewById(R.id.showAddContactBtn);
        emgContactTxtMsg = findViewById(R.id.emgContactEmptyMsg);
        //After getting data
        getSupportActionBar().setTitle("Emergency Contact List");
        FetchContactTask fetchContactTask = new FetchContactTask();
        fetchContactTask.execute();
        new ItemTouchHelper(itemTouchHelperCallBck).attachToRecyclerView(recyclerView);
        addContactNavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, AddContact.class);
                startActivity(intent);
            }
        });
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
                addContactNavBtn.setVisibility(View.GONE);
                emgContactTxtMsg.setVisibility(View.GONE);
                contactListAdapter = new ContactListAdapter(ContactListActivity.this,contactPersonList);
                recyclerView.setAdapter(contactListAdapter);
            }else{
                addContactNavBtn.setVisibility(View.VISIBLE);
                emgContactTxtMsg.setVisibility(View.VISIBLE);
                //Toast.makeText(ContactListActivity.this,"Contact list is empty. Please add contact",Toast.LENGTH_SHORT).show();
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
            String contactNumber = contactPersonList.get(viewHolder.getAdapterPosition()).getNumber();
            int noOfEntriesDeleted = contactHelper.deleteContact(contactNumber);
            if(noOfEntriesDeleted==1){
                contactPersonList.remove(viewHolder.getAdapterPosition());
                contactListAdapter.notifyDataSetChanged();
                Toast.makeText(ContactListActivity.this,"User with contact number "+contactNumber+" has been deleted successfully.",Toast.LENGTH_SHORT).show();
                if(contactPersonList.size()==0){
                    addContactNavBtn.setVisibility(View.VISIBLE);
                    emgContactTxtMsg.setVisibility(View.VISIBLE);
                }
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_additem, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_addContact:
                Intent intent = new Intent(ContactListActivity.this, AddContact.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}