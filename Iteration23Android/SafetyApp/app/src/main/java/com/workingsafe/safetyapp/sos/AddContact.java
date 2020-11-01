package com.workingsafe.safetyapp.sos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.workingsafe.safetyapp.CounselingActivity;
import com.workingsafe.safetyapp.R;
import com.workingsafe.safetyapp.TestimonialAdapter;
import com.workingsafe.safetyapp.model.ContactPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContact extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private Button saveBtn;
    private ContactHelper contactHelper;
    private EditText name;
    private EditText number;
    private EditText message;
    private Switch switchCase;
    private boolean isChecked;
    private Button sendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        saveBtn = findViewById(R.id.saveButton);
        contactHelper = new ContactHelper(this);
        name = findViewById(R.id.contactNameId);
        number = findViewById(R.id.contactNumbId);
        message = findViewById(R.id.editTextTextMultiLine);
        switchCase = findViewById(R.id.switch1);
        sendSms = findViewById(R.id.testSmsBtnId);
        getSupportActionBar().setTitle("Add Contact");
        isChecked = false;

        if(switchCase!=null)
        {
            switchCase.setOnCheckedChangeListener(AddContact.this);
        }
        ArrayList<ContactPerson> contactPersonList = contactHelper.getAllCotacts();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validPattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";
                Pattern r = Pattern.compile(validPattern);

                if(name.getText()!=null && name.getText().toString().trim().length()>0 && number.getText()!=null && number.getText().toString().trim().length()>0 && message.getText()!=null && message.getText().toString().length()>0){
                    Matcher m = r.matcher(number.getText().toString());
                    if(!m.find()){
                        Toast.makeText(AddContact.this,"Please provide valid phone number.",Toast.LENGTH_SHORT).show();
                    }else{
                        saveData();
                    }
                }else{
                    Toast.makeText(AddContact.this,"Please provide the required fields",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Dexter.withContext(AddContact.this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendSingleSms();
                //sendSMSService.startActionSMS(getApplicationContext(),contactPersonArrayList);
                if(contactPersonList!=null) {
                    try{
                        for(int j=0;j<contactPersonList.size();j++) {

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(contactPersonList.get(j).getNumber(), null, contactPersonList.get(j).getMessage(), null, null);
                            //sendBroadcastMessage("Result:"+ (j+1) + " "+ contactPersonList.get(j).getNumber());

                        }
                    }catch (Exception e){
                        Log.d("ExceptionHappens",e.getMessage().toString());
                    }
                }
            }
        });
        IntentFilter intent = new IntentFilter("my.own.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver,intent);
    }

    private void saveData() {
        if (contactHelper.insertContact(name.getText().toString(), number.getText().toString(),
                message.getText().toString(), String.valueOf(isChecked))) {
            Toast.makeText(getApplicationContext(), "Contact has been saved successfully.",
                    Toast.LENGTH_SHORT).show();
            name.setText("");
            number.setText("");
            message.setText("");
            switchCase.setChecked(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AddContact.this, ContactListActivity.class);
                    startActivity(intent);
                }
            }, 2000);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
           isChecked = true;
        } else {
            isChecked = false;
        }
    }
    private BroadcastReceiver myLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result= intent.getStringExtra("result");
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    };

}