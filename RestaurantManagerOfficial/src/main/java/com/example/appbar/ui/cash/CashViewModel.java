package com.example.appbar.ui.cash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CashViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CashViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is staff_signin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
