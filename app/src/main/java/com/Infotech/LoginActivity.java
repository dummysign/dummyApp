package com.Infotech;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    
    EditText Etemail,etpassword;
    RelativeLayout loginbtn;
    TextView TvRegister;
    FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.adjustFontScale(getResources().getConfiguration(), LoginActivity.this, 0.82f);
        hideStatusBarNavigationBar();
        setContentView(R.layout.activity_login);
        Etemail = findViewById(R.id.Etemail);
        etpassword = findViewById(R.id.etpassword);
        loginbtn = findViewById(R.id.loginbtn);
        TvRegister = findViewById(R.id.TvRegister);
        mAuth = FirebaseAuth.getInstance();

        TvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CLICK", "CLICK");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Etemail.getText().toString();
                String password = etpassword.getText().toString();
                if(!TextUtils.isEmpty(email)){
                    if(!TextUtils.isEmpty(password)){
                        Login(LoginActivity.this,email,password);
                    }else {
                        Toast.makeText(getApplicationContext(),"Please Enter the Password",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter the Email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Login(LoginActivity activity, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                String user_id = user.getUid();
                Log.e("USERID","USERID"+user_id);
                Utils.setUserCredential(activity, user_id);
                Utils.setLogin(activity, true);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("UUID",user_id);
                activity.startActivity(intent);
                LoginActivity.this.finish();

            } else {
                Toast.makeText(LoginActivity.this, "Please Check Your login Credentials", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void hideStatusBarNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LoginActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LoginActivity.this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            LoginActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            LoginActivity.this.getWindow().setNavigationBarColor(ContextCompat.getColor(LoginActivity.this, R.color.black));
            LoginActivity.this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

}
