package com.example.elimatsim.ui.transactions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TransactionsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TransactionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is transactions fragment. List connecting to AWS database. " +
                "No data available.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}