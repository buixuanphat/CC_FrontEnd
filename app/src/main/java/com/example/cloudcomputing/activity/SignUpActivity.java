package com.example.cloudcomputing.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cloudcomputing.ApiService;
import com.example.cloudcomputing.R;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        edtUsername = findViewById(R.id.edtUsernameLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        lnSignUp = findViewById(R.id.ln_SignUp);

//==================================================================================================

        lnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString()))
                {
                    String inputUsername = edtUsername.getText().toString();
                    String inputPassword = edtPassword.getText().toString();
                    RequestBody username = RequestBody.create(MediaType.parse("text/plain"), inputUsername);
                    RequestBody password = RequestBody.create(MediaType.parse("text/plain"), inputPassword);

                    ApiService.apiService.registerUser(username, password).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()&&response.code()==201)
                            {
                                Toast.makeText(SignUpActivity.this, "Đăng kí thành công.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "Đã có lỗi xảy ra, đăng kí không thành công.", Toast.LENGTH_SHORT).show();
                                Log.e("api", String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                            Toast.makeText(SignUpActivity.this, "Đã có lỗi xảy ra, đăng kí không thành công.", Toast.LENGTH_SHORT).show();
                            Log.e("api", throwable.getMessage());
                        }
                    });

                }
                else {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}