package com.example.instantchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Login_Activity extends AppCompatActivity {
    //Widgets
    EditText userETLogin, passETLogin;
    Button LoginBtn;
    Button RegisterBtn;

    //Firebase Auth
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Checking for users existance
        if(firebaseUser != null){
            Intent i = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        // Initiating Widgets:
        userETLogin = findViewById(R.id.editText);
        passETLogin = findViewById(R.id.passText);
        LoginBtn = findViewById(R.id.buttontext);
        RegisterBtn = findViewById(R.id.registerBtn);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();



        // Register Button
        RegisterBtn.setOnClickListener(v -> {
            Intent i = new Intent(Login_Activity.this, RegisterActivity.class);
            startActivity(i);
        });

        //Login Button
        LoginBtn.setOnClickListener(v -> {
            String email_text = userETLogin.getText().toString();
            String pass_text = passETLogin.getText().toString();

            //Checking if it's empty

            if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)) {
                Toast.makeText(Login_Activity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            } else {
                auth.signInWithEmailAndPassword(email_text, pass_text)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(Login_Activity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(Login_Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        //Logout Button

    }

}
