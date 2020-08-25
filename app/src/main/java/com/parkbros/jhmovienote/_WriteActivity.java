package com.parkbros.jhmovienote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class _WriteActivity extends AppCompatActivity {
    EditText editText;
    Button submitButton;
    ImageView imageView, backpressImageView;


    String imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___write);
        final String type = getIntent().getStringExtra("type");
        final int position = getIntent().getIntExtra("position", -1);



        editText = (EditText) findViewById(R.id.editText);
        submitButton =  (Button) findViewById(R.id.submitButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        backpressImageView = (ImageView) findViewById(R.id.backpressImageView);
        onCloseButtonPressed();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(type.equals("text")){

                        String text = editText.getText().toString();
                        if(text.isEmpty() || text.length() == 0 || text.equals(null) || text.trim().isEmpty()){
                            Toast.makeText(_WriteActivity.this, "내용을 입력해 주세요", Toast.LENGTH_LONG).show();
                        }else{
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("position", position);
                            returnIntent.putExtra("type", type);
                            returnIntent.putExtra("result", text);
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }

                    }else if(type.equals("title")){

                        String text = editText.getText().toString();
                        if(text.isEmpty() || text.length() == 0 || text.equals(null)|| text.trim().isEmpty()) {
                            Toast.makeText(_WriteActivity.this, "내용을 입력해 주세요", Toast.LENGTH_LONG).show();
                        }else {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("position", position);
                            returnIntent.putExtra("type", type);
                            returnIntent.putExtra("result", text);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    }else{
                        if(imageUri == null){

                        }else{
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("position", position);
                            returnIntent.putExtra("type", type);
                            returnIntent.putExtra("imageUri", imageUri);
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    }


            }
        });

        if(type.equals("image")) {
            //intent type 값이 image 일때 editText 를 지워주고
            editText.setVisibility(View.GONE);
            openImagesDocument();
        }else if(type.equals("title")){
            imageView.setVisibility(View.GONE);
            editText.setHint("제목을 입력해주세요");
        }else{
            // type.equals("text");
            imageView.setVisibility(View.GONE);
            editText.setHint("내용을 입력해주세요");

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG:::", "requestCode : " +  requestCode+  "// resultCode : "+ resultCode);
        if(requestCode == 9999){
            if(resultCode == RESULT_OK){
                Uri sourceUri = data.getData(); // 1
//                Log.e("SourceUri", sourceUri.toString() + " ////  "+ sourceUri.getPath());
                startCrop(sourceUri);
            }else{
                Log.e("REQUEST : ", "CANCELED");
                finish();
            }
        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(_WriteActivity.this. getContentResolver(), resultUri);
//                    Log.e("REAL SIZE :", "w:"+ tempBitmap.getWidth() + "// h:"+tempBitmap.getHeight() + "// byte :"+ tempBitmap.getByteCount());

//                    bitmap = getResizedBitmap(tempBitmap, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                    bitmap = null;
                }
//                Log.e("COMPRESS SIZE :", "w:"+ bitmap.getWidth() + "// h:"+bitmap.getHeight()+ "// byte :"+ bitmap.getByteCount());
                int width = bitmap.getWidth();
                int height =bitmap.getHeight();

                float[] imageSize = ViewSize(width, height);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int)imageSize[0], (int)imageSize[1]);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageBitmap(bitmap);
//                imageView.setImageURI(resultUri);
                imageUri  = resultUri.toString();
            }
        }else {

        }
    }


    private Bitmap compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,25, stream);
        byte[] byteArray = stream.toByteArray();
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        return compressedBitmap;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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


    private void startCrop(Uri uri){
        CropImage
                .activity(uri)
                .start(_WriteActivity.this);
    }

    private float[] ViewSize(int imageWidth, int imageHeight){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        float ratio = (float)width/imageWidth;

        float newHeight = (float) imageHeight*ratio;


        float[] returnSize = new float[2];
        returnSize[0] = (float) width;
        returnSize[1] = newHeight;
        return returnSize;
    }


    private void openImagesDocument() {
        Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pictureIntent.setType("image/*");  // 1
        pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);  // 2
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] mimeTypes = new String[]{"image/jpeg", "image/png"};  // 3
            pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), 9999);  // 4
    }

    private void onCloseButtonPressed(){
        backpressImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(editText.getText().length() == 0){
            finish();
        }else{
            Toast.makeText(this, "작성중인내용이 있습니다", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
