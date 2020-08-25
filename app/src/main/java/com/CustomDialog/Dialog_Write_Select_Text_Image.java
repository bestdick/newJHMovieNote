package com.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parkbros.jhmovienote.R;

public class Dialog_Write_Select_Text_Image extends Dialog {

    ImageView textImageView, imageImageView;
    View.OnClickListener tListener;
    View.OnClickListener iListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_write_select_image);

        //셋팅
        textImageView = findViewById(R.id.textImageView);
        imageImageView = findViewById(R.id.imageImageView);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        textImageView.setOnClickListener(tListener);
        imageImageView.setOnClickListener(iListener);
    }

    //생성자 생성
    public Dialog_Write_Select_Text_Image(@NonNull Context context, View.OnClickListener tListener, View.OnClickListener iListener) {
        super(context);
        this.tListener = tListener;
        this.iListener = iListener;
    }


}
