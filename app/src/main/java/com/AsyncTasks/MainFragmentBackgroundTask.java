package com.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.Beans.BoxOfficeBean;
import com.Beans.MainFragBeen;
import com.Function._ServerCommunicator;
import com.ListViewAdapter.BoxOfficeListAdapter;
import com.ListViewAdapter.MainfragmentListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.StaticValues.StaticValues.baseURL;
import static com.parkbros.jhmovienote.MainActivity.LoginType;
import static com.parkbros.jhmovienote.MainActivity.deviceId;

public class MainFragmentBackgroundTask extends AsyncTask<String, Integer, String> {
    final int PLOT_MAX_LENGTH = 30;

    Context context;
    ListView listView;
    ArrayList<MainFragBeen> list;
    ProgressBar progressBar;
    MainfragmentListAdapter mainfragmentListAdapter;


    public MainFragmentBackgroundTask(Context context, ListView listView, ArrayList<MainFragBeen> list, ProgressBar progressBar, MainfragmentListAdapter mainfragmentListAdapter) {
        this.context = context;
        this.listView = listView;
        this.list = list;
        this.progressBar = progressBar;
        this.mainfragmentListAdapter = mainfragmentListAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings){


        String url = baseURL + "/mainFragment";
        String request_type = "";
        String request_data = requestData();
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(context, url);
        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("MAINFRAGMENT RESULT", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    // Header like My
                    MainFragBeen headerLikeMyItem = new MainFragBeen(1001, null,null,null,null,null, null, null,null,null,null);
                    list.add(headerLikeMyItem);
                    // My Status Container
                    String myListCount = jsonObject.getString("myListCount");
                    MainFragBeen myStatusItem = new MainFragBeen(1002, myListCount,null,null,null,null,null,null,null,null,null);
                    list.add(myStatusItem);

                    // My list
                    JSONArray mylist_jsonArray = jsonObject.getJSONArray("myList");
                    String thumbnailUrl;
                    for(int i = 0 ; i <  mylist_jsonArray.length(); i++){
                        String title = mylist_jsonArray.getJSONObject(i).getJSONArray("content").getJSONObject(0).getString("text");
                        String author = mylist_jsonArray.getJSONObject(i).getString("user");
                        String uploadTime = mylist_jsonArray.getJSONObject(i).getString("uploadTime");
                        String uploadDate = mylist_jsonArray.getJSONObject(i).getString("uploadDate");
                        String hit = mylist_jsonArray.getJSONObject(i).getString("hit");
                        String status = mylist_jsonArray.getJSONObject(i).getString("status");

                        String imageName = getContentThumbnail(mylist_jsonArray.getJSONObject(i).getJSONArray("content"));
                        String fullContent = strCut(makeFullContent(mylist_jsonArray.getJSONObject(i).getJSONArray("content")), PLOT_MAX_LENGTH);

                        MainFragBeen myListItem = new MainFragBeen(
                                1003, null,null,null,status, imageName,author,title,fullContent,hit, uploadDate+" "+ uploadTime);
                        list.add(myListItem);

                    }
                    MainFragBeen footer01 = new MainFragBeen(2001, null,null,null,null,null, null,null, null,null,null);
                    list.add(footer01);

                    //Header like share
                    MainFragBeen headerLikeShareItem = new MainFragBeen(1004, null,null,null,null, null,null,null,null,null,null);
                    list.add(headerLikeShareItem);

                    //Share List
                    JSONArray sharelist_jsonObject = jsonObject.getJSONArray("globalList");
                    for(int i = 0 ; i <  sharelist_jsonObject.length(); i++){
                        String title = sharelist_jsonObject.getJSONObject(i).getJSONArray("content").getJSONObject(0).getString("text");
                        String author = sharelist_jsonObject.getJSONObject(i).getString("user");
                        String uploadTime = sharelist_jsonObject.getJSONObject(i).getString("uploadTime");
                        String uploadDate = sharelist_jsonObject.getJSONObject(i).getString("uploadDate");
                        String hit = sharelist_jsonObject.getJSONObject(i).getString("hit");
                        String status = sharelist_jsonObject.getJSONObject(i).getString("status");

                        String imageName = getContentThumbnail(sharelist_jsonObject.getJSONObject(i).getJSONArray("content"));
//                        Log.e("THUMBNAIL IMAGENAME:", imageName);
                        String fullContent = strCut(makeFullContent(sharelist_jsonObject.getJSONObject(i).getJSONArray("content")), PLOT_MAX_LENGTH);

                        MainFragBeen myListItem = new MainFragBeen(
                                1005, null,null,null,status, imageName, author, title, fullContent, hit, uploadDate+" "+ uploadTime);
                        list.add(myListItem);
                    }
                    MainFragBeen footer02 = new MainFragBeen(2001, null,null,null,null, null, null,null,null,null,null);
                    list.add(footer02);
                    mainfragmentListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, request_type, request_data);

//            for(int i=0; i< 10000; i++)
//            {
//                publishProgress(i);
//            }

        return null;
    }

    private String makeFullContent(JSONArray jsonArray) throws JSONException{
        String returnString = "";
        for(int i = 0 ; i < jsonArray.length(); i++){
            String type = jsonArray.getJSONObject(i).getString("type");
            if(type.equals("text")){
                returnString += jsonArray.getJSONObject(i).getString("text");
            }
        }
        returnString = returnString.replaceAll("\n", " ");
        return returnString;
    }

    protected String strCut(String szText, int nLength)
    { // 문자열 자르기
        String r_val = szText;
        int oF = 0, oL = 0, rF = 0, rL = 0;
        int nLengthPrev = 0;
        try
        {
            byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관
            // x부터 y길이만큼 잘라낸다. 한글안깨지게.
            int j = 0;
            if (nLengthPrev > 0)
                while (j < bytes.length)
                {
                    if ((bytes[j] & 0x80) != 0)
                    {
                        oF += 2;
                        rF += 3;
                        if (oF + 2 > nLengthPrev)
                        {
                            break;
                        }
                        j += 3;
                    }
                    else
                    {
                        if (oF + 1 > nLengthPrev)
                        {
                            break;
                        }
                        ++oF;
                        ++rF;
                        ++j;
                    }
                }
            j = rF;
            while (j < bytes.length)
            {
                if ((bytes[j] & 0x80) != 0)
                {
                    if (oL + 2 > nLength)
                    {
                        break;
                    }
                    oL += 2;
                    rL += 3;
                    j += 3;
                }
                else
                {
                    if (oL + 1 > nLength)
                    {
                        break;
                    }
                    ++oL;
                    ++rL;
                    ++j;
                }
            }
            r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return r_val;
    }


    private String getContentThumbnail(JSONArray jsonArray) throws JSONException {

        for(int i = 0 ; i < jsonArray.length(); i++){
            String type = jsonArray.getJSONObject(i).getString("type");
            if(type.equals("image")){
                String imageName = jsonArray.getJSONObject(i).getString("imageName");
                return imageName;
            }
        }
        return null;
    }
    private String requestData(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginType", LoginType);
            jsonObject.put("deviceId", deviceId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
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
