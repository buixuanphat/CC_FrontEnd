package com.example.cloudcomputing.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cloudcomputing.R;

public class SignUpActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText edtUsername, edtPassword, edtConfirmPassword;
    LinearLayout lnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        lnSignUp = findViewById(R.id.ln_SignUp);

//==================================================================================================
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);

    }
}