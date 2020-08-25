package com.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.Beans.BoxOfficeBean;
import com.Function._ServerCommunicator;
import com.ListViewAdapter.BoxOfficeListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.StaticValues.StaticValues.baseURL;

public class BoxOfficeBackgroundTask extends AsyncTask<String, Integer, String> {
    Context context;
    ListView listView;
    ArrayList<BoxOfficeBean> list;
    ProgressBar progressBar;
    BoxOfficeListAdapter boxOfficeListAdapter;

//hello world
    public BoxOfficeBackgroundTask(Context context, ListView listView, ArrayList<BoxOfficeBean> list, ProgressBar progressBar, BoxOfficeListAdapter boxOfficeListAdapter) {
        this.context = context;
        this.listView = listView;
        this.list = list;
        this.progressBar = progressBar;
        this.boxOfficeListAdapter = boxOfficeListAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings){


        String url = baseURL + "/protocol/protocol_main.php";

        String req = "box";
        String code ="a"; // a : all f: foreign  k  :korea

        JSONObject jsonObject_rd = new JSONObject();
        try {
            jsonObject_rd.put("type", "k");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = jsonObject_rd.toString();
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(context, url);
        serverCommunicator._Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("BOXOFFICE RESULT", result);
                try {
                    JSONObject jsonObject =new JSONObject(result);
                    boolean res = jsonObject.getBoolean("res");
                    JSONObject temp = jsonObject.getJSONObject("msg");

                    String uploadDate = temp.getString("uptime");
                    String uploadTime = temp.getString("uptime");

                    BoxOfficeBean headerItem = new BoxOfficeBean(-1001, uploadDate, uploadTime, null,null,null,null,null,null,
                            null,null,null,null,null,null,null);
                    list.add(headerItem);

                    JSONArray jsonArray = temp.getJSONArray("boxinfo");
                    for(int i = 0 ; i <  jsonArray.length(); i++){
                        BoxOfficeBean item = new BoxOfficeBean(
                                1001,
                                uploadDate,
                                uploadTime,
                                jsonArray.getJSONObject(i).getString("moviename"),
                                jsonArray.getJSONObject(i).getString("rank"),
                                jsonArray.getJSONObject(i).getString("rankInten"),
                                jsonArray.getJSONObject(i).getString("openDt"),
                                jsonArray.getJSONObject(i).getString("audiAcc"),
                                jsonArray.getJSONObject(i).getString("videoId"),
                                jsonArray.getJSONObject(i).getString("url"),
                                jsonArray.getJSONObject(i).getString("naverLink"),
                                jsonArray.getJSONObject(i).getString("image"),
                                jsonArray.getJSONObject(i).getString("subTitle"),
                                jsonArray.getJSONObject(i).getString("director"),
                                jsonArray.getJSONObject(i).getString("actor"),
                                jsonArray.getJSONObject(i).getString("userRate")
                        );
                        list.add(item);
                    }

                    boxOfficeListAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, req, code, data);

//            for(int i=0; i< 10000; i++)
//            {
//                publishProgress(i);
//            }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //** INPUT VALUSE IS THE MIDDLE VOID **
//            textView.setText(values[0].toString());
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}