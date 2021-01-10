package com.ListViewAdapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Beans.MainFragBeen;
import com.parkbros.jhmovienote.R;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;

import static com.StaticValues.StaticValues.baseURL;

public class MainfragmentListAdapter extends BaseAdapter {
    Context context;
    ArrayList<MainFragBeen> list;

    public MainfragmentListAdapter(Context context, ArrayList<MainFragBeen> list) {
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
        View viewHeaderLikeView = View.inflate(context, R.layout.container_mainfrag_subtitle_divider, null);
        View viewFooterLikeView = View.inflate(context, R.layout.container_mainfrag_bottom_divider, null);

        int type = list.get(position).getType();
        switch (type){
            case 1001:
                TextView myTitleTextView = (TextView)viewHeaderLikeView.findViewById(R.id.subtitleTextView);
                myTitleTextView.setText("My List");
                return viewHeaderLikeView;
            case 1002:
                View viewMyStatus = View.inflate(context, R.layout.container_mainfragment_my_status, null);
                TextView textView = (TextView) viewMyStatus.findViewById(R.id.myrecordTextView);

                String count = list.get(position).getMyListCount();
                String HtmlStr = "<span size=\"16sp\">내가 본 영화 : <font size=\"18sp\" color=\"red\" face=\"bold\"> "+count+"</font> 개 </span>";
                textView.setText(Html.fromHtml(HtmlStr));



                return viewMyStatus;
            case 1003:
                View viewMyListViewLeft = View.inflate(context, R.layout.container_mainfrag_list_item_left, null);
                TextView titleTextView = (TextView)viewMyListViewLeft.findViewById(R.id.titleTextView);
                TextView authorTextView = (TextView) viewMyListViewLeft.findViewById(R.id.authorTextView);
                //TextView plotTextView = (TextView) viewMyListViewLeft.findViewById(R.id.plotTextView);
                TextView uploaddateTextView = (TextView) viewMyListViewLeft.findViewById(R.id.uploaddateTextView);
                TextView hitTextView = (TextView) viewMyListViewLeft.findViewById(R.id.hitTextView);
                //TextView likeTextView = (TextView) viewMyListViewLeft.findViewById(R.id.likeTextView);

                ImageView imageView = (ImageView) viewMyListViewLeft.findViewById(R.id.itemImageView);

                String titleString = list.get(position).getTitle();
                String authorString = list.get(position).getAuthorName();
                //String plotString = list.get(position).getDescription();
                String dateString = list.get(position).getDate();
                String hitString = list.get(position).getHit();
//                    String likeString = list.get(position).ge

                titleTextView.setText(titleString);
                authorTextView.setText(authorString);
                //plotTextView.setText(plotString);
                uploaddateTextView.setText(dateString);
                hitTextView.setText(hitString);

                titleTextView.setText(titleString);
                if(list.get(position).getThumbnailUrl() != null){
                    String[] imageName = list.get(position).getThumbnailUrl().split("/");
                    Picasso.get()
                            .load(baseURL+"/getThumbnailImages/"+imageName[3]+"/"+imageName[4])
                            .into(imageView);
                }else{
                    Picasso.get()
                            .load(R.drawable.empty_image)
                            .into(imageView);
                }


                return viewMyListViewLeft;
                /*if(position%2 == 0){
                    View viewMyListViewLeft = View.inflate(context, R.layout.container_mainfrag_list_item_left, null);
                    TextView titleTextView = (TextView)viewMyListViewLeft.findViewById(R.id.titleTextView);
                    TextView authorTextView = (TextView) viewMyListViewLeft.findViewById(R.id.authorTextView);
                    TextView plotTextView = (TextView) viewMyListViewLeft.findViewById(R.id.plotTextView);
                    TextView uploaddateTextView = (TextView) viewMyListViewLeft.findViewById(R.id.uploaddateTextView);
                    TextView hitTextView = (TextView) viewMyListViewLeft.findViewById(R.id.hitTextView);
                    TextView likeTextView = (TextView) viewMyListViewLeft.findViewById(R.id.likeTextView);

                    ImageView imageView = (ImageView) viewMyListViewLeft.findViewById(R.id.itemImageView);

                    String titleString = list.get(position).getTitle();
                    String authorString = list.get(position).getAuthorName();
                    String plotString = list.get(position).getDescription();
                    String dateString = list.get(position).getDate();
                    String hitString = list.get(position).getHit();
//                    String likeString = list.get(position).ge

                    titleTextView.setText(titleString);
                    authorTextView.setText(authorString);
                    plotTextView.setText(plotString);
                    uploaddateTextView.setText(dateString);
                    hitTextView.setText(hitString);

                    titleTextView.setText(titleString);
                    if(list.get(position).getThumbnailUrl() != null){
                        String[] imageName = list.get(position).getThumbnailUrl().split("/");
                        Picasso.get()
                                .load(baseURL+"/getThumbnailImages/"+imageName[3]+"/"+imageName[4])
                                .into(imageView);
                    }else{
                        Picasso.get()
                                .load(R.drawable.empty_image)
                                .into(imageView);
                    }


                    return viewMyListViewLeft;
                }else{
                    View viewMyListViewRight = View.inflate(context, R.layout.container_mainfrag_list_item_right, null);
                    TextView titleTextView = (TextView)viewMyListViewRight.findViewById(R.id.titleTextView);
                    TextView authorTextView = (TextView) viewMyListViewRight.findViewById(R.id.authorTextView);
                    TextView plotTextView = (TextView) viewMyListViewRight.findViewById(R.id.plotTextView);
                    TextView uploaddateTextView = (TextView) viewMyListViewRight.findViewById(R.id.uploaddateTextView);
                    TextView hitTextView = (TextView) viewMyListViewRight.findViewById(R.id.hitTextView);
                    TextView likeTextView = (TextView) viewMyListViewRight.findViewById(R.id.likeTextView);

                    ImageView imageView = (ImageView) viewMyListViewRight.findViewById(R.id.itemImageView);

                    String titleString = list.get(position).getTitle();
                    String authorString = list.get(position).getAuthorName();
                    String plotString = list.get(position).getDescription();
                    String dateString = list.get(position).getDate();
                    String hitString = list.get(position).getHit();
//                    String likeString = list.get(position).ge

                    titleTextView.setText(titleString);
                    authorTextView.setText(authorString);
                    plotTextView.setText(plotString);
                    uploaddateTextView.setText(dateString);
                    hitTextView.setText(hitString);

                    if(list.get(position).getThumbnailUrl() != null){
                        String[] imageName = list.get(position).getThumbnailUrl().split("/");
                        Picasso.get()
                                .load(baseURL+"/getThumbnailImages/"+imageName[3]+"/"+imageName[4])
                                .into(imageView);
                    }else{
                        Picasso.get()
                                .load(R.drawable.empty_image)
                                .into(imageView);
                    }


                    return viewMyListViewRight;
                }*/
            case 1004:
                TextView shareTitleTextView = (TextView)viewHeaderLikeView.findViewById(R.id.subtitleTextView);
                shareTitleTextView.setText("Share List");
                return viewHeaderLikeView;
            case 1005:
                /*if(position%2 == 0){
                    View viewShareListViewLeft = View.inflate(context, R.layout.container_mainfrag_list_item_left, null);
                    TextView titleTextView = (TextView)viewShareListViewLeft.findViewById(R.id.titleTextView);
                    TextView authorTextView = (TextView) viewShareListViewLeft.findViewById(R.id.authorTextView);
                    TextView plotTextView = (TextView) viewShareListViewLeft.findViewById(R.id.plotTextView);
                    TextView uploaddateTextView = (TextView) viewShareListViewLeft.findViewById(R.id.uploaddateTextView);
                    TextView hitTextView = (TextView) viewShareListViewLeft.findViewById(R.id.hitTextView);
                    TextView likeTextView = (TextView) viewShareListViewLeft.findViewById(R.id.likeTextView);

                    ImageView imageView = (ImageView) viewShareListViewLeft.findViewById(R.id.itemImageView);


                    String titleString = list.get(position).getTitle();
                    String authorString = list.get(position).getAuthorName();
                    String plotString = list.get(position).getDescription();
                    String dateString = list.get(position).getDate();
                    String hitString = list.get(position).getHit();
//                    String likeString = list.get(position).ge


                    titleTextView.setText(titleString);
                    authorTextView.setText(authorString);
                    plotTextView.setText(plotString);
                    uploaddateTextView.setText(dateString);
                    hitTextView.setText(hitString);


                    if(list.get(position).getThumbnailUrl() != null){
                        String[] imageName = list.get(position).getThumbnailUrl().split("/");
                        Picasso.get()
                                .load(baseURL+"/getThumbnailImages/"+imageName[3]+"/"+imageName[4])
                                .into(imageView);
                    }else{
                        Picasso.get()
                                .load(R.drawable.empty_image)
                                .into(imageView);
                    }

                    return viewShareListViewLeft;
                }else{
                    View viewShareListViewRight = View.inflate(context, R.layout.container_mainfrag_list_item_right, null);
                    TextView titleTextView = (TextView)viewShareListViewRight.findViewById(R.id.titleTextView);
                    TextView authorTextView = (TextView) viewShareListViewRight.findViewById(R.id.authorTextView);
                    TextView plotTextView = (TextView) viewShareListViewRight.findViewById(R.id.plotTextView);
                    TextView uploaddateTextView = (TextView) viewShareListViewRight.findViewById(R.id.uploaddateTextView);
                    TextView hitTextView = (TextView) viewShareListViewRight.findViewById(R.id.hitTextView);
                    TextView likeTextView = (TextView) viewShareListViewRight.findViewById(R.id.likeTextView);

                    ImageView imageView = (ImageView) viewShareListViewRight.findViewById(R.id.itemImageView);

                    String titleString = list.get(position).getTitle();
                    String authorString = list.get(position).getAuthorName();
                    String plotString = list.get(position).getDescription();
                    String dateString = list.get(position).getDate();
                    String hitString = list.get(position).getHit();


                    titleTextView.setText(titleString);
                    authorTextView.setText(authorString);
                    plotTextView.setText(plotString);
                    uploaddateTextView.setText(dateString);
                    hitTextView.setText(hitString);


                    if(list.get(position).getThumbnailUrl() != null){
                        String[] imageName = list.get(position).getThumbnailUrl().split("/");
                        Picasso.get()
                                .load(baseURL+"/getThumbnailImages/"+imageName[3]+"/"+imageName[4])
                                .into(imageView);
                    }else{
                        Picasso.get()
                                .load(R.drawable.empty_image)
                                .into(imageView);
                    }
                    return viewShareListViewRight;
                }*/
            case 1006:
                TextView etcTitleTextView = (TextView)viewHeaderLikeView.findViewById(R.id.subtitleTextView);
                etcTitleTextView.setText("Etc");
                return viewHeaderLikeView;
            case 1007:
                TextView temp03TextView = (TextView)viewHeaderLikeView.findViewById(R.id.subtitleTextView);
                temp03TextView.setText("----etc----------");
                return viewHeaderLikeView;
            default:
                return viewFooterLikeView;
        }
    }


    public  String cutString(String str, int len) {

        byte[] by = str.getBytes();
        int count = 0;
        try  {
            for(int i = 0; i < len; i++) {

                if((by[i] & 0x80) == 0x80) count++; // 핵심 코드

            }

            if((by[len - 1] & 0x80) == 0x80 && (count % 2) == 1) len--; // 핵심코드

            return new String(by, 0, len);

        }
        catch(java.lang.ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e);
            return "";
        }

    }




}
