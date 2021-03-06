package com.example.anitac.parsetigram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anitac.parsetigram.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainLoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Bundle SaveInstanteState saves what u haave wrtten down when avtivity is destroyed i.e. orientation
        super.onCreate(savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) { //if else statement remembers last user logged in, and auto-logs the user in
            setContentView(R.layout.activity_main_login);
            mUsername = findViewById(R.id.vUsername);
            mPassword = findViewById(R.id.vPass);
            mLogin = findViewById(R.id.vLogin);

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = mUsername.getText().toString();
                    final String password = mPassword.getText().toString();

                    Login(username, password);
                }
            });
        }
        else{
            final Intent intent = new Intent(MainLoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void Login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.d("LogInActivity", "Log in successful!");
                    final Intent intent = new Intent(MainLoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.e("LogInActivity", "Log in failure.");
                    e.printStackTrace();
                }
            }
        });
    }

    public void onSignupAction(View view) {
        final Intent intent = new Intent(MainLoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
