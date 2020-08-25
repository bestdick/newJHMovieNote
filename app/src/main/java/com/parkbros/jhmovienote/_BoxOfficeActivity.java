package com.parkbros.jhmovienote;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Beans.BoxOfficeBean;
import com.Function._ServerCommunicator;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.StaticValues.StaticValues.baseURL;

public class _BoxOfficeActivity extends AppCompatActivity {

    BoxOfficeBean item;

    LinearLayout container;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___box_office);

        container = (LinearLayout) findViewById(R.id.linearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        int rank = getIntent().getIntExtra("rank", -1);
        String uploadDate = getIntent().getStringExtra("uploadDate");
        String requestData = requestData(rank, uploadDate);
        GetSelectedBoxOffice(requestData);
    }


    private void GetSelectedBoxOffice(String requestData){
        String url = baseURL + "/boxOfficelist";
        String request_type = "select";
        String request_data = requestData;
        _ServerCommunicator serverCommunicator = new _ServerCommunicator(this, url);
        serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
            @Override
            public void onSuccess(String result, String connection) {
                Log.e("RESULT", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject innerJson = jsonObject.getJSONObject("selectInfo");
                    item = new BoxOfficeBean(-1,
                        jsonObject.getString("uploadDate"),
                            jsonObject.getString("uploadTime"),
                            innerJson.getString("movieName"),
                            innerJson.getString("boxRank"),
                            innerJson.getString("boxRankChange"),
                            innerJson.getString("openDate"),
                            innerJson.getString("audiAcc"),
                            innerJson.getString("videoID"),
                            innerJson.getString("videoThumbnailUrl"),
                            innerJson.getString("naverLink"),
                            innerJson.getString("naverImage"),
                            innerJson.getString("naverSubTitle"),
                            innerJson.getString("naverDirector"),
                            innerJson.getString("naverActor"),
                            innerJson.getString("naverRating")
                            );

                    View v  = getLayoutInflater().inflate(R.layout.container_boxoffice_select_item, null);
                    ImageView boxOfficeImageView = (ImageView) v.findViewById(R.id.itemImageView);
                    TextView titleTextView = (TextView) v.findViewById(R.id.titleTextView);
                    TextView bodirectorTextView = (TextView) v.findViewById(R.id.authorTextView);
                    TextView boactorTextView = (TextView) v.findViewById(R.id.plotTextView);
                    TextView boopendateTextView = (TextView) v.findViewById(R.id.uploaddateTextView);
                    TextView bonaverrateTextView = (TextView) v.findViewById(R.id.bonaverrateTextView);
                    TextView boaudaccTextView = (TextView) v.findViewById(R.id.boaudaccTextView);


                    String rank = item.getBoxRank();
                    String rankChange = item.getBoxRankChange();
                    String movieName = "BoxOffice Rank "+rank + "\n" +item.getMovieName();
                    String naverImage = item.getNaverImage();
                    String director = "<strong><font color='#990000'>감독</font></strong>  " + item.getNaverDirector();
                    String actor = "<strong><font color='#990000'>배우</font></strong>  "+item.getNaverActor();
                    String openDate = "<strong><font color='#990000'>개봉일</font></strong>  "+item.getOpenDate();
                    String naverRate = "<strong><font color='#990000'>네이버 평점</font></strong>  "+ item.getNaverRating() + " 점";
                    String audacc = "<strong><font color='#990000'>누적 관객</font></strong>  " + item.getAudiAcc() + " 명";

                    float[] imageSize =ImageSize();

                    Picasso.get()
                            .load(naverImage)
                            .resize((int) imageSize[0], (int) (imageSize[1]))
                            .into(boxOfficeImageView);

                    titleTextView.setText(Html.fromHtml(movieName));
                    bodirectorTextView.setText(Html.fromHtml(director));
                    boactorTextView.setText(Html.fromHtml(actor));
                    boopendateTextView.setText(Html.fromHtml(openDate));
                    bonaverrateTextView.setText(Html.fromHtml(naverRate));
                    boaudaccTextView.setText(Html.fromHtml(audacc));

                    container.addView(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, request_type, request_data);
    }


    private String requestType(String requestType){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("requestType", requestType);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject.toString();
        }
    }
    private String requestData(int rank, String uploadDate){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rank", rank);
            jsonObject.put("uploadDate", uploadDate);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject.toString();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private float[] ImageSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        float newWidth = width * .4f;
        float newHeight = width * .7f;

        float[] returnSize = new float[2];
        returnSize[0] = (float) newWidth;
        returnSize[1] = (float) newHeight;
        return returnSize;
    }
}
