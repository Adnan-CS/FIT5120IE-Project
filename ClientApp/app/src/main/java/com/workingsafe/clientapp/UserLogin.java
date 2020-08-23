package com.workingsafe.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.workingsafe.clientapp.home.HomeActivity;

import java.security.NoSuchAlgorithmException;

public class UserLogin extends AppCompatActivity {
    private EditText userNameEditText;
    private EditText passwdEditText;
    private Button loginBtn;
    private String username;
    private String password;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        userNameEditText = findViewById(R.id.userName);
        passwdEditText = findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.loginBtn);
        signUpButton = findViewById(R.id.signUpBtn);
        //lyrichassan100@gmail.com
        userNameEditText.setText("");
        //lyrichasan
        passwdEditText.setText("");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, HomeActivity.class);
                startActivity(intent);
/*                try {
                    validateUserNamePasswd();

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }*/
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void validateUserNamePasswd() throws NoSuchAlgorithmException {
        username = userNameEditText.getText().toString().trim();
        password = passwdEditText.getText().toString().trim();
        if(username.length()==0 || password.length()==0){
            Toast.makeText(UserLogin.this,"Username or password can not be empty",Toast.LENGTH_LONG).show();
        }else{
            if(username.matches(emailPattern)){
/*                GetAllStudentsTask getAllStudentsTask = new GetAllStudentsTask ();
                getAllStudentsTask.execute();*/
            }else{
                Toast.makeText(UserLogin.this,"Please provide valid email address format",Toast.LENGTH_LONG).show();
            }
        }
        //Add some validation
        //Email format
        //Email and password not empty
        //password length must be greater than 4
    }
}
