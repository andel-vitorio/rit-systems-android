package com.example.rit_system.models;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> deletedItem = new MutableLiveData<>();

    public void setItemIndex(int position) {
        deletedItem.setValue(position);
    }

    public LiveData<Integer> getItemIndex() {
        return deletedItem;
    }
}
