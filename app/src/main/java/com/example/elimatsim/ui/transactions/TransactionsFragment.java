package com.example.elimatsim.ui.transactions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.elimatsim.R;
import com.example.elimatsim.databinding.FragmentSlideshowBinding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class TransactionsFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private String content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransactionsViewModel transactionsViewModel =
                new ViewModelProvider(this).get(TransactionsViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button access = root.findViewById(R.id.AccessToken);

        access.setOnClickListener(v -> {
            new Thread(() -> {
                Log.i("Thread: ", "Thread Starting");
                content = createAccessToken();
            }
            ).start();
        });

        final TextView textView = binding.textSlideshow;
        textView.setText(content);
        //transactionsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public String createAccessToken(){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/remittance/token/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("Authorization", "mattikewax@gmail.com");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "2b6ecd81af2a4e999557cc9192e2dd00");

            connection.setRequestMethod("POST");

            int status = connection.getResponseCode();
            Log.i("HTTP Status", String.valueOf(status));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            // System.out.println(content);
            connection.disconnect();
            return content.toString();
        } catch (Exception ex) {
            Log.e("exception:" , "Failed to connect");
        }
        return "Failed to connect";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}