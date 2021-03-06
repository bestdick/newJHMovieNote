package com.MainFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.AsyncTasks.MainFragmentBackgroundTask;
import com.Beans.MainFragBeen;
import com.Function.OnFragmentInteractionListener;
import com.Function._ServerCommunicator;
import com.ListViewAdapter.BoxOfficeListAdapter;
import com.ListViewAdapter.MainfragmentListAdapter;
import com.parkbros.jhmovienote.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.StaticValues.StaticValues.baseURL;
import static com.parkbros.jhmovienote.MainActivity.deviceId;
import static com.parkbros.jhmovienote.MainActivity.deviceToken;
import static com.parkbros.jhmovienote.MainActivity.uid;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
// * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2, ProgressBar _progressBar) {
        progressBar = _progressBar;
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.mainListView);
        ArrayList<MainFragBeen> list= new ArrayList<>();
        MainfragmentListAdapter adapter = new MainfragmentListAdapter(getContext(), list);
        listView.setAdapter(adapter);

        //MainFragmentBackgroundTask bt = new MainFragmentBackgroundTask(getContext(), listView, list, progressBar, adapter);
        //bt.execute();
        _initialLoader();


        return rootView;
    }

    public void _initialLoader(){
        String mode = "main";
        String code = "my_list";
        JSONObject data = new JSONObject();
        try {
            data.put("uid", uid ) ;
            data.put("device_id", deviceId);
            data.put("device_token", deviceToken);
        } catch (JSONException e) {
             e.printStackTrace();
        }

        String stringified_data = data.toString();

        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), baseURL);
        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("mainfrag result : ", result);
                if( connection.equals("success")){
                    try {
                        JSONObject jsonObject = new JSONObject( result );
                        int res = jsonObject.getInt("res");
                        if( res == 0 ){
                            int page = jsonObject.getJSONObject("msg").getInt("pg_start" ); 
                            JSONArray list = jsonObject.getJSONObject("msg").getJSONArray("list" ) ;
                            for( int i = 0 ; i < list.length() ; i++ ){

                            }
                        }
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, mode, code, "0", stringified_data ) ;
    }



//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        Log.e("MainFragment", " Attached");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.e("MainFragment", " Detached");
    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
public interface OnListFragmentInteractionListener<T> {

    void onListFragmentInteraction(String tag, T data);
}

}
