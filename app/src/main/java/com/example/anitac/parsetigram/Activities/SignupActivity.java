package com.example.anitac.parsetigram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anitac.parsetigram.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    TextView sUsername;
    TextView sEmail;
    TextView sPassword;
    ParseUser user = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sUsername = findViewById(R.id.enterUsername);
        sPassword = findViewById(R.id.enterPassword);
        sEmail = findViewById(R.id.enterEmail);


    }

    public void onSignupAction(View view) {
        final String username = sUsername.getText().toString();
        final String password = sPassword.getText().toString();
        final String email = sEmail.getText().toString();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d("Signup Activity", "Signup Successfull!");
                    Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_LONG);
                    final Intent intent = new Intent(SignupActivity.this, MainLoginActivity.class);
                    startActivity(intent);
                } else {
                    Log.e("Signup Activity", "Signup failed");
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
