package com.example.elimatsim.ui.farmers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.MoMoAPI;
import com.example.elimatsim.databinding.FragmentReflowBinding;

public class FarmersFragment extends Fragment {
    private FragmentReflowBinding binding;
    private MoMoAPI apiUser;
    private String content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FarmersViewModel farmersViewModel = new ViewModelProvider(this).get(FarmersViewModel.class);

        binding = FragmentReflowBinding.inflate(inflater, container, false);
        binding.investNow.setOnClickListener(v -> new Thread(() -> {
            Log.i("Farmers Thread: ", "Thread Starting");
            apiUser = MoMoAPI.getInstance();
            content = apiUser.getAccountBalanceCurr();
        }).start());
        if(content == null)
            content = "Failed to change content before thread ends";
        farmersViewModel.getText().setValue(content);
        // final TextView textView =
        farmersViewModel.getText().observe(getViewLifecycleOwner(), binding.textReflow::setText);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}