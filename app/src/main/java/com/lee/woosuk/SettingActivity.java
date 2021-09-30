package com.lee.woosuk;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lee.woosuk.R;

import java.util.Set;

public class SettingActivity extends AppCompatActivity {


    private Button backbtn, clearbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backbtn = (Button) findViewById(R.id.backbtn);
        clearbtn = (Button) findViewById(R.id.clearbtn);

        final Intent intent = new Intent(SettingActivity.this, MainStartActivity.class);


        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetDialog(intent);

            }
        });



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(intent); // Alert다이얼로그 호출
            }
        });


    }


    // Alert다이얼로그 호출 함수 정의 - 설정 적용 다이얼로그
    public void showDialog(final Intent intent){
        AlertDialog.Builder dlg = new AlertDialog.Builder(SettingActivity.this);

        dlg.setTitle("설정을 적용하시겠습니까?");
        dlg.setMessage("적용 시에 앱이 새로고침 됩니다.");
        dlg.setIcon(R.drawable.preferencedialog);

        dlg.setPositiveButton("적용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                startActivity(intent);
                System.exit(0);
            }
        });

        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dlg.show();
    }

    // Alert다이얼로그 호출 함수 정의 - 설정 초기화 다이얼로그
    public void showResetDialog(final Intent intent){
        AlertDialog.Builder dlg = new AlertDialog.Builder(SettingActivity.this);

        dlg.setTitle("설정을 초기화 하시겠습니까?");
        dlg.setMessage("초기화 시에 모든 설정이 초기화 됩니다.");
        dlg.setIcon(R.drawable.preferencedialog);

        dlg.setPositiveButton("초기화", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                finishAffinity();
                startActivity(intent);
                System.exit(0);

            }
        });

        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dlg.show();
    }

    //기기의 뒤로가기 터치 시에 Alert다이얼로그 호출
    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(SettingActivity.this, MainStartActivity.class);
        showDialog(intent);
    }

}
