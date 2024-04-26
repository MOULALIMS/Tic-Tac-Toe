package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView tologin;
    Button signup;
    EditText email,password,name;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser !=null){
            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            tologin = findViewById(R.id.redirecttologin);
            name = findViewById(R.id.signup_name);
            email = findViewById(R.id.signupemail);
            password = findViewById(R.id.signuppassword);
            signup = findViewById(R.id.signupbutton);

            tologin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailid = email.getText().toString();
                    String pass = password.getText().toString();
                    String username = name.getText().toString();
                    if(!emailid.isEmpty() && !pass.isEmpty() && !username.isEmpty()){
                        firebaseAuth.createUserWithEmailAndPassword(emailid,pass)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                            FirebaseUser user =firebaseAuth.getCurrentUser();
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Account already exits",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Provide all details",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}