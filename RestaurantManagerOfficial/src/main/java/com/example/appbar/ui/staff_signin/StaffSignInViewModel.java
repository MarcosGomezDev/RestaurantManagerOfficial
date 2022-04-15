package com.example.appbar.ui.staff_signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StaffSignInViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StaffSignInViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is staff_signin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}