package com.example.appbar.ui.cash_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CashHistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CashHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is staff_signin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
