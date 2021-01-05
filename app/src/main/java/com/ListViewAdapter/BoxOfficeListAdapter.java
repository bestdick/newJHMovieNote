package com.ListViewAdapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Beans.BoxOfficeBean;
import com.parkbros.jhmovienote.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BoxOfficeListAdapter extends BaseAdapter {
    Context context;
    ArrayList<BoxOfficeBean> list;

    public BoxOfficeListAdapter(Context context, ArrayList<BoxOfficeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View headerView = View.inflate(context, R.layout.container_boxoffice_header, null);
        View v = View.inflate(context, R.layout.container_boxoffice_item, null);
        View v2 = View.inflate(context, R.layout.container_boxoffice_item_right, null);
        //View footerView = View.inflate(context, R.layout.container_boxoffice_header, null);
        if(position == 0){
             headerView.setEnabled(false);
             TextView headerTextView = (TextView)headerView.findViewById(R.id.headerTextView);
             TextView uploadDateTextView = (TextView) headerView.findViewById(R.id.uploadDateTextView);
             headerTextView.setText("BOX OFFICE RANK");
             uploadDateTextView.setText("업데이트 날짜 : " +list.get(position).getUploadDate() + "  " + list.get(position).getUploadTime());
            return headerView;
        }else{
            if((position%2) == 0){
                ImageView boxOfficeImageView = (ImageView) v2.findViewById(R.id.itemImageView);
                TextView titleTextView = (TextView) v2.findViewById(R.id.titleTextView);
                TextView bodirectorTextView = (TextView) v2.findViewById(R.id.authorTextView);
                TextView boactorTextView = (TextView) v2.findViewById(R.id.plotTextView);
                TextView boopendateTextView = (TextView) v2.findViewById(R.id.uploaddateTextView);
                TextView bonaverrateTextView = (TextView) v2.findViewById(R.id.bonaverrateTextView);
                TextView boaudaccTextView = (TextView) v2.findViewById(R.id.boaudaccTextView);


                String rank = list.get(position).getBoxRank();
                String rankChange = list.get(position).getBoxRankChange();
                String movieName = "box "+rank + " " +list.get(position).getMovieName();
                String naverImage = list.get(position).getNaverImage();
                String director = "<strong><font color='#990000'>감독</font></strong>  " + list.get(position).getNaverDirector();
                String actor = "<strong><font color='#990000'>배우</font></strong>  "+list.get(position).getNaverActor();
                String openDate = "<strong><font color='#990000'>개봉일</font></strong>  "+list.get(position).getOpenDate();
                String naverRate = "<strong><font color='#990000'>네이버 평점</font></strong>  "+ list.get(position).getNaverRating();
                String audacc = "<strong><font color='#990000'>누적 관객</font></strong>  " + list.get(position).getAudiAcc() + " 명";


                if( !naverImage.equals("")){
                    Picasso.get().load(naverImage).into(boxOfficeImageView);
                }

                titleTextView.setText(Html.fromHtml(movieName));
                bodirectorTextView.setText(Html.fromHtml(director));
                boactorTextView.setText(Html.fromHtml(actor));
                boopendateTextView.setText(Html.fromHtml(openDate));
                bonaverrateTextView.setText(Html.fromHtml(naverRate));
                boaudaccTextView.setText(Html.fromHtml(audacc));


                return v2;
            }else{
                ImageView boxOfficeImageView = (ImageView) v.findViewById(R.id.itemImageView);
                TextView titleTextView = (TextView) v.findViewById(R.id.titleTextView);
                TextView bodirectorTextView = (TextView) v.findViewById(R.id.authorTextView);
                TextView boactorTextView = (TextView) v.findViewById(R.id.plotTextView);
                TextView boopendateTextView = (TextView) v.findViewById(R.id.uploaddateTextView);
                TextView bonaverrateTextView = (TextView) v.findViewById(R.id.bonaverrateTextView);
                TextView boaudaccTextView = (TextView) v.findViewById(R.id.boaudaccTextView);


                String rank = list.get(position).getBoxRank();
                String rankChange = list.get(position).getBoxRankChange();
                String movieName = "box "+rank + " " +list.get(position).getMovieName();
                String naverImage = list.get(position).getNaverImage();
                String director = "<strong><font color='#990000'>감독</font></strong>  " + list.get(position).getNaverDirector();
                String actor = "<strong><font color='#990000'>배우</font></strong>  "+list.get(position).getNaverActor();
                String openDate = "<strong><font color='#990000'>개봉일</font></strong>  "+list.get(position).getOpenDate();
                String naverRate = "<strong><font color='#990000'>네이버 평점</font></strong>  "+ list.get(position).getNaverRating();
                String audacc = "<strong><font color='#990000'>누적 관객</font></strong>  " + list.get(position).getAudiAcc()+ " 명";


                if( !naverImage.equals("") ){
                    Picasso.get().load(naverImage).into(boxOfficeImageView);
                }
                titleTextView.setText(Html.fromHtml(movieName));
                bodirectorTextView.setText(Html.fromHtml(director));
                boactorTextView.setText(Html.fromHtml(actor));
                boopendateTextView.setText(Html.fromHtml(openDate));
                bonaverrateTextView.setText(Html.fromHtml(naverRate));
                boaudaccTextView.setText(Html.fromHtml(audacc));


                return v;
            }
        }

    }
}
