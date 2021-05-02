package com.NewMovieNote.Repos;

import androidx.lifecycle.MutableLiveData;

import com.Beans.Test2ActivityModel;
import com.NewMovieNote.models.BoxItemModel;
import com.repository.Test2ActivityRepository;

import java.util.ArrayList;
import java.util.List;

public class BoxItemRepo {
    private static BoxItemRepo instance;
    private ArrayList<BoxItemModel> dataSet = new ArrayList<>() ;

    public static BoxItemRepo getInstance() {
        if( instance == null ){
            instance  = new BoxItemRepo();
        }
        return instance ;
    }
    // --- database --- server ....etc ...
    public MutableLiveData<List<BoxItemModel>> getBoxItemModel(){

        MutableLiveData<List<BoxItemModel>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data ;
    }
}
