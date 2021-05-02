package com.NewMovieNote.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.NewMovieNote.ViewModel.BoxItemViewModel;
import com.NewMovieNote.models.BoxItemModel;
import com.ViewModel.Test2ActivityViewModel;
import com.google.android.datatransport.cct.internal.LogEvent;
import com.parkbros.jhmovienote.MainActivity;
import com.parkbros.jhmovienote.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_ONE;
import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_THREE;
import static com.StaticValues.StaticValues.__TOOLBAR_MENU_BOX_TWO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoxFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoxFragment newInstance(String param1, String param2) {
        BoxFragment fragment = new BoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private final String TAG = "BoxFragment";

    private int menuSelection = 0 ;

    private final String __BOX_FETCH_ALL = "a";
    private final String __BOX_FETCH_KOREA = "k";
    private final String __BOX_FETCH_FORIGEN = "f";

    private BoxItemViewModel boxItemViewModel ;
    private BoxItemAdapter boxItemAdapter ;
    private List<BoxItemModel> boxItemList ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        boxItemAdapter = new BoxItemAdapter(getContext(), boxItemList);
        boxItemViewModel = ViewModelProviders.of(this).get(BoxItemViewModel.class); // call view model
        boxItemViewModel.init( getContext() );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_box, container, false);
        int[] screenInfo = GetDeviceScreenSize();
        boxItemAdapter.setScreenInfo( screenInfo[0] , screenInfo[1] ) ;

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext() , 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(boxItemAdapter);


        boxItemViewModel.getBoxItemModel().observe(this, new Observer<List<BoxItemModel>>() {
            @Override
            public void onChanged(List<BoxItemModel> list) {
//                for(int i = 0 ; i < list.size() ; i++){
//                    LogE("Title : " +  list.get(i).getTitle() );
//                    LogE("ImageUrl : " +  list.get(i).getImageUrl() );
//                }
                if( list != null ){
                    boxItemList = list;
                    boxItemAdapter.setList( list );
                }
            }
        });
        boxItemViewModel.fetchBoxInfo(__BOX_FETCH_ALL);
        return rootView ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void LogE(String msg ){
        Log.e(TAG, msg);
    }

    private int[] GetDeviceScreenSize(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int imageWidth = width/2 ;
        int imageHeight = (int) ( imageWidth*1.4 ) ;
        int[] rtn_val = new int[2];
        rtn_val[0] = imageWidth ;
        rtn_val[1] =imageHeight ;
        return rtn_val ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        LogE( "From the fragment : " + item.getItemId() );
        switch( item.getItemId() ){
            case __TOOLBAR_MENU_BOX_ONE :
                if( menuSelection != 0 ) {
                    menuSelection = 0;
                    boxItemViewModel.fetchBoxInfo(__BOX_FETCH_ALL);
                }
                break;
            case __TOOLBAR_MENU_BOX_TWO :
                if( menuSelection != 1 ) {
                    menuSelection = 1;
                    boxItemViewModel.fetchBoxInfo(__BOX_FETCH_KOREA);
                }
                break;
            case __TOOLBAR_MENU_BOX_THREE :
                if( menuSelection != 2 ) {
                    menuSelection = 2;
                    boxItemViewModel.fetchBoxInfo(__BOX_FETCH_FORIGEN);
                }
                break;

        }
        return false;
    }

}

class BoxItemAdapter extends  RecyclerView.Adapter<BoxItemAdapter.MyViewHolder>{

    Context context ;
    List<BoxItemModel> list ;

    private int screenWidth ;
    private int screenHeight ;

    public BoxItemAdapter(Context context, List<BoxItemModel> list) {
        this.context = context;
        this.list = list;
     //   Log.e("Adapter" , "init , Size : " + list.size() );
    }

    public void setList(List<BoxItemModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setScreenInfo(int screenWidth , int screenHeight){
        this.screenWidth = screenWidth ;
        this.screenHeight = screenHeight ;
    }

    @NonNull
    @Override
    public BoxItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_in_container_box, parent,false );
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BoxItemAdapter.MyViewHolder holder, int position) {
        //Log.e("Adapter" , "list : position  " + position + " / what title "  + this.list.get(position).getTitle() );
        holder.imageView.setLayoutParams( new ConstraintLayout.LayoutParams(screenWidth, screenHeight ) );
        holder.textView.setText(this.list.get(position).getTitle());
        if( ! CheckNullEmpty( list.get(position).getImageUrl() ) ){
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            Picasso.get()
                    .load( this.list.get(position).getImageUrl() )
                    .fit()
                    .into( holder.imageView );
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG" , "item clicked the position is  " + position );
                // ---  item click event ...
            }
        });
    }

    @Override
    public int getItemCount() {
        if( this.list != null ){
            return this.list.size();
        }
        return 0;
    }

    private boolean CheckNullEmpty( String tarStr ) {
        if( tarStr.trim().equals( "null" )){
            return true;
        }
        if( tarStr.trim().equals( "" )){
            return true;
        }
        if( tarStr == null ){
            return true;
        }
        if( tarStr.equals(null) ){
            return true;
        }
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public MyViewHolder( View itemView ) {
            super(itemView);
            imageView =  (ImageView) itemView.findViewById(R.id.imageView2);
            textView = (TextView) itemView.findViewById(R.id.textView6);

        }

    }




}



