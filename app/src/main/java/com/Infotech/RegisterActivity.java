package com.Infotech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText etemail,etpassword;
    RelativeLayout signinbtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisetr);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        signinbtn = findViewById(R.id.signinbtn);
        mAuth = FirebaseAuth.getInstance();
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    if(!TextUtils.isEmpty(password)){
                        Register(RegisterActivity.this,email,password);
                    }
                }
            }
        });
    }

    private void Register(RegisterActivity registerActivity, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.e("LLLLL","LLLLL ");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String user_id = user.getUid();
                    Utils.setUserCredential(registerActivity, user_id);
                    Intent intent = new Intent(registerActivity, MainActivity.class);
                    intent.putExtra("UUID",user_id);
                    registerActivity.startActivity(intent);
                    RegisterActivity.this.finish();
                }else {
                    Toast.makeText(RegisterActivity.this, "Please Try Once Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
