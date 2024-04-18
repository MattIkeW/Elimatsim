package com.example.elimatsim.ui.farmers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FarmersViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FarmersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Yello, I am Kato. I run a fish farm in Namayingo district on the " +
                "shores of lake Victoria. I would like to expand my pond to contain 50 tilapia. " +
                "To accomplish this, I need a $2570 investment.");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}