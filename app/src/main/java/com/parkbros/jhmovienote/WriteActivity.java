package com.parkbros.jhmovienote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Beans.WriteBean;

import com.CustomDialog.Dialog_Write_Select_Text_Image;
import com.CustomDialog.Dialog_Write_Submit;
import com.Function._ServerCommunicator;
import com.WriteRecyclerViewFunction.ItemMoveCallback;
import com.WriteRecyclerViewFunction.RecyclerViewAdapter;
import com.WriteRecyclerViewFunction.StartDragListener;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.StaticValues.StaticValues.WRITE_CONTENT_STATUS_PRIVATE;
import static com.StaticValues.StaticValues.WRITE_CONTENT_STATUS_PUBLIC;
import static com.StaticValues.StaticValues.baseURL;
import static com.parkbros.jhmovienote.MainActivity.LoginType;
import static com.parkbros.jhmovienote.MainActivity.deviceId;

public class WriteActivity extends AppCompatActivity implements StartDragListener {

    private final int TYPE_TITLE = 1001;
    private final int TYPE_ADD_MORE = 1002;
    private final int TYPE_TEXT = 1003;
    private final int TYPE_IMAGE =1004;


    int tabPosition;

    Dialog_Write_Select_Text_Image dialog;
    Dialog_Write_Submit submitDialog;

    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    public ArrayList<WriteBean> list;
    ItemTouchHelper touchHelper;

    ProgressBar progressBar;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = getIntent();
        tabPosition = intent.getIntExtra("tabPosition", -1);


        list = new ArrayList<WriteBean>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        populateRecyclerView();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        submitButton = (Button) findViewById(R.id.submitButton);


        uploadSubmitClick();
        onCloseButtonPressed();

    }
    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
    private void populateRecyclerView() {
        WriteBean titleItem = new WriteBean(
                "title", null,null,null,null,null,null,null,null
        );
        list.add(titleItem);
        mAdapter = new RecyclerViewAdapter(WriteActivity.this, list, this);
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(mAdapter);
        touchHelper  = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);


        final GestureDetector gestureDetector = new GestureDetector(WriteActivity.this, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView _rv, @NonNull MotionEvent e) {
                final RecyclerView rv = _rv;
                final View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child!=null&& gestureDetector.onTouchEvent(e))
                {
//                    Log.d("TAG","onInterceptTouchEvent");
//                    Log.d("TAG", "getChildAdapterPosition=>" + rv.getChildAdapterPosition(child));
//                    Log.d("TAG","getChildLayoutPosition=>"+rv.getChildLayoutPosition(child));
//                    Log.d("TAG","getChildViewHolder=>" + rv.getChildViewHolder(child));


                    if(rv.getChildAdapterPosition(child) == 0){
                        Intent intent = new Intent(WriteActivity.this, _WriteActivity.class);
                        intent.putExtra("type", "title");
                        intent.putExtra("position", rv.getChildAdapterPosition(child));
                        startActivityForResult(intent, 9009);

                    }else{
                        dialog = new Dialog_Write_Select_Text_Image(WriteActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //text
                                Intent intent = new Intent(WriteActivity.this, _WriteActivity.class);
                                intent.putExtra("type", "text");
                                intent.putExtra("position", rv.getChildAdapterPosition(child));
                                startActivityForResult(intent, 9009);
                                dialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //image
                                Intent intent = new Intent(WriteActivity.this, _WriteActivity.class);
                                intent.putExtra("type", "image");
                                intent.putExtra("position", rv.getChildAdapterPosition(child));
                                startActivityForResult(intent, 9009);

//                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                            photoPickerIntent.setType("image/*");
//                            String[] mimeTypes = {"image/jpeg", "image/png"};
//                            photoPickerIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
//                            photoPickerIntent.putExtra("position", rv.getChildAdapterPosition(child));
//                            startActivityForResult(photoPickerIntent, 9999);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
//

                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    private boolean preventFalseUpload(){
        if(list.size()  == 2 && list.get(1).getType().equals("empty")){
            return false;
        }else {
            for(int i = 0 ; i < list.size(); i++){
                if((list.get(i).getType().equals("txt") || list.get(i).getType().equals("title")) && (list.get(i).getText() == null || list.get(i).getText().isEmpty()) ){
                    return false;
                }
            }
            return true;
        }
    }
    private void uploadSubmitClick() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDialog();
            }
        });
    }
    private void uploadDialog() {
        submitDialog = new Dialog_Write_Submit(WriteActivity.this,
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProcess(WRITE_CONTENT_STATUS_PUBLIC);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProcess(WRITE_CONTENT_STATUS_PRIVATE);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        submitDialog.show();
    }

    private void uploadProcess(String status) {

        boolean preventor = preventFalseUpload();
        if (preventor) {
            progressBar.setVisibility(View.VISIBLE);
            String url = baseURL + "/upload";
            String request_type = "";
            String request_data = ArrayListtoJSONArray(status).toString();
            _ServerCommunicator serverCommunicator = new _ServerCommunicator(WriteActivity.this, url);
            serverCommunicator.Communicator(new _ServerCommunicator.VolleyCallback() {
                @Override
                public void onSuccess(String result, String connection) {
                    Log.e("upload:::::", result);

                    if (result.equals("upload_success")) {
                        submitDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(WriteActivity.this, "업로드 완료", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("result", "success");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        submitDialog.dismiss();
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(WriteActivity.this, "업로드 실패", Toast.LENGTH_LONG).show();
                    }
                }
            }, request_type, request_data);
        } else {
            Toast.makeText(WriteActivity.this, "작성하지 않은 항목이 있습니다.", Toast.LENGTH_LONG).show();
        }
    }



    private JSONObject ArrayListtoJSONArray(String status){
        JSONObject jsonObject =new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
        for(int i = 0 ; i < list.size(); i++){
//            String type = (list.get(i).getType().equals(null))? null : list.get(i).getType() ;
            String type = list.get(i).getType();
            JSONObject _jsonObject = new JSONObject();

                _jsonObject.put("type", type);
                _jsonObject.put("text", list.get(i).getText());
                _jsonObject.put("align", list.get(i).getAlign());
                _jsonObject.put("fontsize", list.get(i).getFontsize());
                if(list.get(i).getImageUri() == null){
                    _jsonObject.put("imageString", null);
                    _jsonObject.put("imageName", null);
                    _jsonObject.put("originalImageString", null);
                }else{
                    _jsonObject.put("imageString", ImageToBase64(i));
                    _jsonObject.put("imageName", list.get(i).getImageName());
                    _jsonObject.put("originalImageString", OriginalImageToBase64(i));
                }
                _jsonObject.put("rotation", list.get(i).getRotation());
                _jsonObject.put("width", list.get(i).getWidth());
                _jsonObject.put("height", list.get(i).getHeight());

                jsonArray.put(_jsonObject);
        }
            jsonObject.put("loginType", LoginType);
            jsonObject.put("deviceId", deviceId);
            jsonObject.put("content", jsonArray);
            jsonObject.put("status", status);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String ImageToBase64(int position){

        RecyclerView.ViewHolder vh = recyclerView.findViewHolderForAdapterPosition(position);
        ImageView movieImageView = (ImageView) vh.itemView.findViewById(R.id.movieImageView);

        Bitmap bitmap = ((BitmapDrawable)movieImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();

        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;

    }
    private String OriginalImageToBase64(int position){
        Uri imageUri = list.get(position).getImageUri();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(WriteActivity.this. getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] imageBytes = baos.toByteArray();

        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9009) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                // Do something with the contact here (bigger example below)

                String type = data.getStringExtra("type");
                int position = data.getIntExtra("position", -1);


//                Log.e("RESULT INTENT ", type +"// "+ position+ "//"+result);


                WriteBean item;
                String result;
                Uri imageUri;
                switch (type){
                    case "text":
                         result = data.getStringExtra("result");
                         item = new WriteBean(type, result, null, null,null,null,null, null, null);
                        list.set(position, item);
                        break;
                    case "image":
                        imageUri = Uri.parse(data.getStringExtra("imageUri"));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());
                        String imageName = deviceId + "_"+ currentDateandTime+"_"+position+".jpeg";
                         item = new WriteBean(type, null, null, null,imageUri,imageName, null,null, null);
                        list.set(position, item);
                        break;
                    case "title":
                        result = data.getStringExtra("result");
                        item = new WriteBean(type, result, null, null,null,null, null,null, null);
                        list.set(position, item);
                        break;
                    default:
                         item = new WriteBean(type, null, null, null,null,null, null,null, null);
                        list.set(position, item);
                        break;

                }




                WriteBean newitem = new WriteBean(
                        "empty", null,null,null,null,null,null,null,null
                );
                list.add(newitem);
                mAdapter.notifyDataSetChanged();


//                for (int i = 0 ; i < list.size(); i ++){
//                    Log.e("WHAT IN LIST :", list.get(i).getType());
//                }


            }else{
                Log.e("NO INTENT RESULT", "NO RESULT");
            }
        }else{

        }
    }



    ImageView backpressImageView;
    private void onCloseButtonPressed(){
        backpressImageView = (ImageView) findViewById(R.id.backpressImageView);
        backpressImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("result", "cancel");
        intent.putExtra("tabPosition", tabPosition);
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
}
