package com.example.cloudcomputing.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cloudcomputing.ApiService;
import com.example.cloudcomputing.R;
import com.example.cloudcomputing.Static;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RelativeLayout rLLogout;
    SharedPreferences sharedPreferences;
    TextView tvUsername;
    ImageView ivAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rLLogout = view.findViewById(R.id.rLLogout);
        sharedPreferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        tvUsername = view.findViewById(R.id.tv_username_profile);
        ivAvatar = view.findViewById(R.id.iv_avatar_profile);

//==================================================================================================

        // Xử lý nút đăng xuất
        rLLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isLogin", false);
                editor.putString("accessToken", "");
                editor.apply();
                Static.restartApp(requireActivity());
            }
        });

        // Lấy thông tin user hiện tại
        String accessToken = "Bearer ";
        accessToken += sharedPreferences.getString("accessToken", null);
        ApiService.apiService.getCurrentUser(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String strResponse = response.body().string();
                    JSONObject jsonObject = new JSONObject(strResponse);
                    String username = jsonObject.getString("username");
                    String avatarUrl = Static.baseUrl;
                    avatarUrl += jsonObject.getString("avatar");
                    tvUsername.setText(username);
                    Glide.with(getContext()).load(avatarUrl).into(ivAvatar);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getContext(), "Không lấy được thông tin người dùng", Toast.LENGTH_SHORT).show();
            }
        });





        return view;
    }
}