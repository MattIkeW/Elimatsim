package com.example.elimatsim.ui.transactions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.MoMoAPI;
import com.example.elimatsim.R;
import com.example.elimatsim.databinding.FragmentSlideshowBinding;

public class TransactionsFragment extends Fragment {
    private MoMoAPI apiUser;
    private FragmentSlideshowBinding binding;
    private String content;
    private TransactionsViewModel transactionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionsViewModel =
                new ViewModelProvider(this).get(TransactionsViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button access = root.findViewById(R.id.AccessToken);

        access.setOnClickListener(v -> {
            new Thread(() -> {
                Log.i("Transactions Thread: ", "Thread Starting");
                apiUser = MoMoAPI.getInstance();
                content = apiUser.getAccountBalance();
            }
            ).start();
            transactionsViewModel.getText().setValue(content);
        });

        final TextView textView = binding.textSlideshow;
        transactionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}