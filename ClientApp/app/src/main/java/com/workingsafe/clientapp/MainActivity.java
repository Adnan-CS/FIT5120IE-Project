package com.workingsafe.clientapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.workingsafe.clientapp.model.Registration;
import com.workingsafe.clientapp.restapi.RestApi;
import com.workingsafe.clientapp.utility.Hashing;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText firstNameEdt;
    private EditText surNameEdt;
    private EditText userEmailEdt;
    private TextView dobAddTxtVw;
    private Button clickDobBtn;
    private EditText userPasswordEdt;
    private EditText userConfPassEdt;
    private Button loginButton;
    private Button signUpButton;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RestApi restApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNameEdt = findViewById(R.id.firstName);
        surNameEdt = findViewById(R.id.surName);
        userEmailEdt = findViewById(R.id.userEmail);
        dobAddTxtVw = findViewById(R.id.dateDobViewId);
        clickDobBtn = findViewById(R.id.dobPickerBtn);
        userPasswordEdt = findViewById(R.id.userPassword);
        userConfPassEdt = findViewById(R.id.userConfPass);
        signUpButton = findViewById(R.id.signupBtnId);
        loginButton = findViewById(R.id.signInBtnIdFrmSngUp);
        restApi = new RestApi();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration registration = fetchRegistrationModelData();
                if (registration != null) {
                    AddCredTask addCredTask = new AddCredTask();
                    addCredTask.execute(registration);
                }
            }
        });

        clickDobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserLogin.class);
                startActivity(intent);
            }
        });
    }
    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("yyyy-MM-dd", calendar1).toString();

                dobAddTxtVw.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }
    private class AddCredTask extends AsyncTask<Registration, Void, String> {
        @Override
        protected String doInBackground(Registration... registrations) {
            return restApi.addCredentials(registrations[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

    private Registration fetchRegistrationModelData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(formatter.format(date));
        String signUpDate = formatter.format(date);


        Registration registration = new Registration();
        String firstname = firstNameEdt.getText().toString().trim();
        String lastname = surNameEdt.getText().toString().trim();

        String dob = dobAddTxtVw.getText().toString() ;
        String email = userEmailEdt.getText().toString().trim();
        String password = userPasswordEdt.getText().toString().trim();
        String confPassword = userConfPassEdt.getText().toString().trim();


        if (firstname.length() == 0 || lastname.length() == 0 || dob.length() == 0 || password.length() == 0 || confPassword.length() == 0 || email.length() == 0) {
            Toast.makeText(MainActivity.this, "firstname, lastname, dob, email, password or confirm password can not be empty", Toast.LENGTH_LONG).show();
            return null;
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(MainActivity.this, "Please provide valid email address format", Toast.LENGTH_LONG).show();
            return null;
        }
        else if(!password.equals(confPassword)){
            Toast.makeText(MainActivity.this, "Password and confirm password must be similar.", Toast.LENGTH_LONG).show();
            return null;
        }

        registration.setFirstname(firstname);
        registration.setLastname(lastname);

        registration.setEmail(email);
        registration.setDob(dob);


        String hashedVal = "";
        try {
            hashedVal = Hashing.toHexString(Hashing.getSHA(userPasswordEdt.getText().toString().trim()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        registration.setPassword(hashedVal);
        return registration;
    }
}
