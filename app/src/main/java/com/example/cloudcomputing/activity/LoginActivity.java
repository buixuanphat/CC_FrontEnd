package com.example.cloudcomputing.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cloudcomputing.ApiService;
import com.example.cloudcomputing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvSignUp;
    EditText edtUsername, edtPassword;
    LinearLayout lnLogin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvSignUp = findViewById(R.id.tvSignUp);
        edtUsername = findViewById(R.id.edtUsernameLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        lnLogin = findViewById(R.id.ln_Login);

        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//==================================================================================================

        // Chuyển qua màn hình đăn kí
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý đăng nhập
        lnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = edtUsername.getText().toString();
                String inputPassword = edtPassword.getText().toString();
                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), inputUsername);
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), inputPassword);
                RequestBody clientId = RequestBody.create(MediaType.parse("text/plain"), "sWAvXj7BkLdoxUjD1g4gUUsQCupd9Ts4CCxe6vNk");
                RequestBody clientSecret = RequestBody.create(MediaType.parse("text/plain"), "FCYobelmzcqOErqpxD4eygOvS8VfakQjgf7yX7tM7WV4iesf0icjPyJadaVftUNYEMPmEy3Dwa7hrkSBDl4GXyZ3ymp5benUR3QwJ9IUTiUtNO5kagPBMcGxVT7XEblJ");
                RequestBody grantType = RequestBody.create(MediaType.parse("text/plain"), "password");

                ApiService.apiService.logIn(username, password, clientId, clientSecret, grantType).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()&&response.code()==200)
                        {
                            try {
                                String jsonResponse = response.body().string();
                                JSONObject jsonObject = new JSONObject(jsonResponse);
                                String accessToken = jsonObject.getString("access_token");
                                editor.putBoolean("isLogin", true);
                                editor.putString("accessToken", accessToken);
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                                finish();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Đã có lỗi xảy ra, đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                            Log.e("api", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "Đã có lỗi xảy ra, đăng nhập không thành công.", Toast.LENGTH_SHORT).show();
                        Log.e("api", throwable.getMessage());
                    }
                });
            }
        });

    }
}