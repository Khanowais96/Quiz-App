package com.example.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText edEmail,edPass;
    private Button btnSubmit;
    private Button btnsignup;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);

        btnSubmit = findViewById(R.id.btn_login);
        btnsignup=findViewById(R.id.signup);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent= new Intent(Login.this,SignUp.class);
                startActivity(signupintent);
                finish();
            }
        });
    }

    private void login(){
        String email= Utils.getText(edEmail);
        String password= Utils.getText(edPass);

        if(Utils.isValidated(email,password)){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                Utils.showMessage(Login.this,"Email Already Registered");
                            }
                        }
                    });
        }
        else{
            Utils.showMessage(Login.this,"Please enter your credentials");
        }
    }
}
