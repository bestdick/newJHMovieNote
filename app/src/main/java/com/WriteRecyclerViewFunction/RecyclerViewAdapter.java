package com.WriteRecyclerViewFunction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Beans.WriteBean;
import com.parkbros.jhmovienote.R;
import com.parkbros.jhmovienote._WriteActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {
public class RecyclerViewAdapter extends RecyclerView.Adapter implements ItemMoveCallback.ItemTouchHelperContract {
    private final int TYPE_TITLE = 1001;
    private final int TYPE_ADD_MORE = 1002;
    private final int TYPE_TEXT = 1003;
    private final int TYPE_IMAGE =1004;


    private ArrayList<WriteBean> data;
    Context context;
    private final StartDragListener mStartDragListener;



    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView, movieImageView;
        View rowView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            movieImageView = itemView.findViewById(R.id.movieImageView);
            imageView = itemView.findViewById(R.id.dragHandlerImageView);
        }

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView mTitle;
        View rowView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTitle = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.dragHandlerImageView);
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<WriteBean> data,  StartDragListener startDragListener) {
        this.context = context;
        this.data = data;
        mStartDragListener = startDragListener;
    }



    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        String type = data.get(position).getType();
        switch (type){
            case "text":

                return TYPE_TEXT;
            case "image":

                return TYPE_IMAGE;
            case "title":

                return TYPE_TITLE;
            default:

                return TYPE_ADD_MORE;
        }
    }

    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TEXT:
                View textitemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_write_letter_item, parent, false);
                return new MyViewHolder(textitemView);
            case TYPE_IMAGE:
                View imageitemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_write_image_item, parent, false);
                return new ImageViewHolder(imageitemView);
            case TYPE_TITLE:
                View titleItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_write_letter_item, parent, false);
                return new MyViewHolder(titleItemView);
            default:
                View emptyitemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_write_letter_item, parent, false);
                return new MyViewHolder(emptyitemView);

        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        String type = data.get(position).getType();
        switch (getItemViewType(position)){
            case TYPE_TEXT:
//                ;
                ((MyViewHolder) holder).mTitle.setText(data.get(position).getText());
                ((MyViewHolder) holder).imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() ==
                                MotionEvent.ACTION_DOWN) {
                            mStartDragListener.requestDrag(((MyViewHolder) holder));
                        }
                        return false;
                    }
                });
                break;
            case TYPE_IMAGE:
                Bitmap bitmap = getResizedBitmap(data.get(position).getImageUri(), 800);
//                ((ImageViewHolder) holder).movieImageView.setImageResource(R.drawable.icon_back_press);
//                ((ImageViewHolder) holder).movieImageView.setImageURI(data.get(position).getImageUri());
                ((ImageViewHolder) holder).movieImageView.setImageBitmap(bitmap);

                ((ImageViewHolder) holder).imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() ==
                                MotionEvent.ACTION_DOWN) {
                            mStartDragListener.requestDrag(((ImageViewHolder) holder));
                        }
                        return false;
                    }
                });
                break;
            case TYPE_TITLE:
                if(data.get(position).getText() == null){
                    ((MyViewHolder) holder).mTitle.setText("제목을 입력해주세요");
                    ((MyViewHolder) holder).mTitle.setTextColor(ContextCompat.getColor(context, R.color.colorMediumGray));
                }else{
                    ((MyViewHolder) holder).mTitle.setText(data.get(position).getText());
                    ((MyViewHolder) holder).mTitle.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
                }
//                ((MyViewHolder) holder).mTitle.setGravity(Gravity.CENTER);
                ((MyViewHolder) holder).imageView.setVisibility(View.GONE);

                break;
            default:
                ((MyViewHolder) holder).mTitle.setText("터치하여 텍스트나 이미지를 작성해주세요.");
                ((MyViewHolder) holder).mTitle.setTextColor(ContextCompat.getColor(context, R.color.colorMediumGray));
                ((MyViewHolder) holder).mTitle.setGravity(Gravity.CENTER);
                ((MyViewHolder) holder).imageView.setVisibility(View.GONE);
                break;

        }


    }
    public Bitmap getResizedBitmap(Uri resultUri, int maxSize) {
        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(context. getContentResolver(), resultUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(image.getByteCount() < 300000){
            return image;
        }else{
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }

    }


//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, int position) {
//
//        Log.e("in recycle type:: ", " ::: "+getItemViewType(position));
//        if(getItemViewType(position) == 0){
//            holder.mTitle.setText("터치하여 텍스트나 이미지를 작성해주세요.");
//        }else if (getItemViewType(position) == 1){
//            holder.mTitle.setText(data.get(position).getText());
//        }else {
////            holder.getItemViewType() == 2
//            holder.movieImageView.setImageResource(R.drawable.icon_back_press);
//        }
//
//
//
//        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() ==
//                        MotionEvent.ACTION_DOWN) {
//                    mStartDragListener.requestDrag(holder);
//                }
//                return false;
//            }
//        });
//    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        Log.e("TO POSITION ", String.valueOf(toPosition));
        Log.e("FROM POSITION ", String.valueOf(fromPosition));
        if(toPosition == data.size()-1 || toPosition == 0){

        }else{
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(data, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(data, i, i - 1);
                }
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(MyViewHolder myViewHolder) {
//        myViewHolder.rowView.setBackgroundColor(R.color.colorCrimsonRed);
        myViewHolder.rowView.getResources().getColor(R.color.colorCrimsonRed);
    }

    @Override
    public void onRowClear(MyViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }


    @Override
    public void removeItem(int position) {
        if(position == 0 || data.size()-1 == position){

        }else{
            data.remove(position);
        }
        notifyItemRemoved(position);
    }



//    public void restoreItem(String item, int position) {
//        data.add(position, item);
//        notifyItemInserted(position);
//    }
}
