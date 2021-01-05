package com.MainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.AsyncTasks.BoxOfficeBackgroundTask;
import com.Beans.BoxOfficeBean;
import com.Function.OnFragmentInteractionListener;
import com.Function._ServerCommunicator;
import com.ListViewAdapter.BoxOfficeListAdapter;
import com.parkbros.jhmovienote.MainActivity;
import com.parkbros.jhmovienote.R;
import com.parkbros.jhmovienote._BoxOfficeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.StaticValues.StaticValues.baseURL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
// * {@link BoxOfficeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoxOfficeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoxOfficeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ProgressBar progressBar;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BoxOfficeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoxOfficeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoxOfficeFragment newInstance(String param1, String param2, ProgressBar _progressBar) {
        progressBar = _progressBar;
        BoxOfficeFragment fragment = new BoxOfficeFragment();
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

    ArrayList<BoxOfficeBean> list;
    BoxOfficeListAdapter boxOfficeListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_box_office, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.boxOfficeListView);
        list = new ArrayList<>();
        boxOfficeListAdapter = new BoxOfficeListAdapter(getContext(), list);
        listView.setAdapter(boxOfficeListAdapter);
        /*BoxOfficeBackgroundTask bt = new BoxOfficeBackgroundTask(getContext(), listView, list, progressBar, boxOfficeListAdapter);
        bt.execute();

        OnClickItemOnList(listView);*/




        String url = baseURL;
        String mode = "box";
        String code = "a";
        JSONObject data = new JSONObject();
        String stringified_data = data.toString();
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(getContext(), url);
        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e( "머지? : ", result );
                if( connection == "success" ){
                    try {
                        JSONObject jsonObject = new JSONObject( result );
                        int res = jsonObject.getInt("res");
                        if( res == 0 ){
                            String uptime = jsonObject.getJSONObject("msg").getString("uptime");
                            uptime+="000";
                            //Log.e( "uptime  : ",  uptime ) ;
                            Date date = new Date( Long.parseLong( uptime )  );
                            SimpleDateFormat dateFormat1 =new SimpleDateFormat("yyyy.MM.dd");
                            SimpleDateFormat dateFormat2 =new SimpleDateFormat("HH:mm:ss");
                            String date_str = dateFormat1.format( date );
                            String time_str = dateFormat2.format( date );

                            BoxOfficeBean header = new BoxOfficeBean(1001, date_str, time_str, null,
                                    null, null, null, null, null,
                                    null, null, null,
                                    null, null, null, null);
                            list.add(header);

                            JSONArray boxJSONArray = jsonObject.getJSONObject("msg").getJSONArray("boxinfo");
                            for( int i = 0 ; i < boxJSONArray.length(); i++) {

                                //Log.e( "image ::", boxJSONArray.getJSONObject(i).getString("image") );
                                BoxOfficeBean item = new BoxOfficeBean(1001, null, null,
                                        boxJSONArray.getJSONObject(i).getString("moviename"),
                                        boxJSONArray.getJSONObject(i).getString("rnum"),
                                        null,
                                        boxJSONArray.getJSONObject(i).getString("openDt"),
                                        boxJSONArray.getJSONObject(i).getString("audiAcc"),
                                        null,
                                        null,
                                        boxJSONArray.getJSONObject(i).getString("naverLink"),
                                        boxJSONArray.getJSONObject(i).getString("image"),
                                        boxJSONArray.getJSONObject(i).getString("subTitle"),
                                        boxJSONArray.getJSONObject(i).getString("director"),
                                        boxJSONArray.getJSONObject(i).getString("actor"),
                                        boxJSONArray.getJSONObject(i).getString("userRate"));
                                list.add(item);
                            }

                            boxOfficeListAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

        }, mode, code, "0", stringified_data);



        return rootView;
    }


    private void OnClickItemOnList(ListView listView){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("POSITION :: ", String.valueOf(position));
                if(position != 0){
                    Intent intent = new Intent(getContext(), _BoxOfficeActivity.class);
                    intent.putExtra("rank", position);
                    intent.putExtra("uploadDate", list.get(position).getUploadDate());
                    startActivity(intent);
                }

            }
        });
    }

//    public interface OnFragmentInteractionListener {
//    }
//
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
        Log.e("BoxofficeFragment ::", "Attached");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.e("BoxofficeFragment :: ", "Detached");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
    public interface OnListFragmentInteractionListener<T> {
        void onListFragmentInteraction(String tag, T data);
    }

}
