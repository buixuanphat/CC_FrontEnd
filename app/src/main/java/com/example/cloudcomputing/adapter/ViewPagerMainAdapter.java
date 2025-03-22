package com.example.cloudcomputing.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cloudcomputing.fragment.HomeFragment;
import com.example.cloudcomputing.fragment.LikedFragment;
import com.example.cloudcomputing.fragment.ProfileFragment;
import com.example.cloudcomputing.fragment.SharedFragment;

public class ViewPagerMainAdapter extends FragmentStateAdapter {
    public ViewPagerMainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new LikedFragment();
            case 2: return  new SharedFragment();
            case 3: return new ProfileFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
