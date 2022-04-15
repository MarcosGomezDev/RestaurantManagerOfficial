package com.example.appbar.ui.bookings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookingsViewmodel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BookingsViewmodel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is staff_signin fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
