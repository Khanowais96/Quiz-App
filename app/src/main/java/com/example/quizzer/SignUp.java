package com.example.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzer.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SignUp extends AppCompatActivity {

    private EditText edFn,edLn,edEmail,edPass,edConfPass;
    private Button btnSubmit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView already;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



        edFn = findViewById(R.id.ed_fname);
        edLn = findViewById(R.id.ed_lname);
        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
        edConfPass = findViewById(R.id.ed_conf_pass);

        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRegistration();
            }
        });

        already= findViewById(R.id.already);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
                finish();

            }
        });
    }


    private void makeRegistration(){
        final String fName= Utils.getText(edFn);
        final String lName= Utils.getText(edLn);
        final String email= Utils.getText(edEmail);
        String password= Utils.getText(edPass);
        String confirmPassword= Utils.getText(edConfPass);

        if(Utils.isValidated(fName,lName,email,password,confirmPassword)){
            if(Utils.isPasswordValidated(password,confirmPassword)){
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                                    user.getUid();
                                    //Profile Create
                                    FirebaseInstanceId.getInstance().getInstanceId()
                                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                    User u1 = new User(user.getUid(),fName,lName,email);
                                                    if (!task.isSuccessful()) {
                                                        u1.token = null;
                                                    }
                                                    u1.token = task.getResult().getToken();

                                                    databaseReference.child("users").child(user.getUid()).setValue(u1);
                                                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });



                                } else {
                                    Utils.showMessage(SignUp.this,"Email Already Registered");
                                }
                            }
                        });
            }
            else{
                Utils.showMessage(SignUp.this,"Password must be same");
                edPass.setText("");
                edConfPass.setText("");
            }
        }
        else{
            Utils.showMessage(SignUp.this,"Please fill the form");
        }
    }

}
