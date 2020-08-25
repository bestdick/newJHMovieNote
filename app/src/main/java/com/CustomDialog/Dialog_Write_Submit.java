package com.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parkbros.jhmovienote.R;

public class Dialog_Write_Submit extends Dialog {

    TextView dialogMessageTextView;
    Button publicButton, privateButton,cancelButton;

    View.OnClickListener publicListener;
    View.OnClickListener privateListener;
    View.OnClickListener cancelListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_write_submit);

        //셋팅
        dialogMessageTextView = findViewById(R.id.dialogMessageTextView);
        publicButton = findViewById(R.id.publicButton);
        privateButton = findViewById(R.id.privateButton);
        cancelButton = findViewById(R.id.cancelButton);


        String message = "작성하신 내용을 공개 혹은 비공개 설정하여 업로드 해주세요.";
        String publicMes ="공개";
        String privateMes = "비공개";
        String cancelMes = "취소";


        dialogMessageTextView.setText(message);
        publicButton.setText(publicMes);
        privateButton.setText(privateMes);
        cancelButton.setText(cancelMes);


        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        publicButton.setOnClickListener(publicListener);
        privateButton.setOnClickListener(privateListener);
        cancelButton.setOnClickListener(cancelListener);
    }

    //생성자 생성
    public Dialog_Write_Submit(@NonNull Context context, View.OnClickListener publicListener, View.OnClickListener privateListener, View.OnClickListener cancelListener) {
        super(context);
        this.publicListener = publicListener;
        this.privateListener = privateListener;
        this.cancelListener = cancelListener;
    }

    private int widthSize(){
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x * (int).9f;
        return width;
    }
}
