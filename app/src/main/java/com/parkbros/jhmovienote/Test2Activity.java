package com.parkbros.jhmovienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.Beans.Test2ActivityModel;
import com.ViewModel.Test2ActivityViewModel;

import java.util.List;

public class Test2Activity extends AppCompatActivity {
    final private String TAG = "Test2Activity";
    final private boolean debugMode = true ;
    private Test2ActivityViewModel test2ActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


        test2ActivityViewModel = ViewModelProviders.of(this).get(Test2ActivityViewModel.class); // call view model
        test2ActivityViewModel.init(this ); // ---call to set the data set into ......
        test2ActivityViewModel.getTest2ActivityModel().observe(this, new Observer<List<Test2ActivityModel>>() {
            @Override
            public void onChanged(List<Test2ActivityModel> test2ActivityModels) {
                LogE( "view model live data change ");

                List<Test2ActivityModel> list = test2ActivityViewModel.getTest2ActivityModel().getValue();
                for( int i = 0 ; i < list.size() ; i ++ ){
                    LogE( i + "  [title] :  " + list.get(i).getTitle() + " [desciption]  : "+ list.get(i).getDescription() ) ;
                }

            }
        });
        test2ActivityViewModel.setTest2ActivityModel();

    }


    private void LogE( String msg ){
        if( debugMode )  Log.e( TAG , msg );
    }
}