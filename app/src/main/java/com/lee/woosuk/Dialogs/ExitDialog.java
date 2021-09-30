package com.lee.woosuk.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lee.woosuk.MainStartActivity;
import com.lee.woosuk.R;


public class ExitDialog {

    private Context context;

    public ExitDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callExitDialogFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.exit_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button exitButton = (Button) dlg.findViewById(R.id.exitButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);
        final TextView dlgColor = (TextView) dlg.findViewById(R.id.dlgColor);


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        switch (pref.getString("theme","")){
            case "red": //만약 "red" 라면 테마 레드계열로 변경
                dlgColor.setBackgroundResource(R.color.red);
                break;
            case "blue":
                dlgColor.setBackgroundResource(R.color.blue);
                break;
            case "green":
                dlgColor.setBackgroundResource(R.color.green);
                break;
        }



        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //앱을 종료
                ((MainStartActivity)context).finish();
            }
        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }
}