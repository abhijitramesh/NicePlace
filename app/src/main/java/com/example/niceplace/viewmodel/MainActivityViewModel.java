package com.example.niceplace.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.niceplace.model.NicePlace;
import com.example.niceplace.repository.NicePlaceRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NicePlace>> mNicePlaces;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    private NicePlaceRepository mNicePlaceRepository;

    public void  init(){
        if(mNicePlaces != null){
            return;
        }
        mNicePlaceRepository = NicePlaceRepository.getInstance();
        mNicePlaces = mNicePlaceRepository.getNicePlaces();

    }
    public void addNewValue(final NicePlace nicePlace){
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<NicePlace> currentPlaces = mNicePlaces.getValue();
                currentPlaces.add(nicePlace);
                mNicePlaces.postValue(currentPlaces);
                mIsUpdating.postValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<List<NicePlace>> getNicePlaces(){
        return mNicePlaces;
    }


    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }
}
