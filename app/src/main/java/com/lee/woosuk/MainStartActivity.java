package com.lee.woosuk;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lee.woosuk.Dialogs.ExitDialog;


public class MainStartActivity extends AppCompatActivity {

    //권한 목록
    String[] permission_list = {
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private Button btnStart, exitBtn;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainstart);

        btnStart = (Button) findViewById(R.id.btnStart);
        exitBtn = (Button) findViewById(R.id.exitbtn);
        layout = (ConstraintLayout) findViewById(R.id.main_start_layout);

        /* 팝업메뉴 호출 시에 사용
        final SharedPreferences sf = getSharedPreferences("test", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sf.edit();
        */


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainStartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog exitDialog = new ExitDialog(MainStartActivity.this);
                exitDialog.callExitDialogFunction();
            }
        });

        checkPermission();

        /* 팝업 메뉴 호출 시에 사용
        // 키값 "First"를 읽어서
        switch (sf.getString("First","")){
            case "red": //만약 "red" 라면 테마 레드계열로 변경
                layout.setBackgroundResource(R.drawable.background_start_red);
                exitBtn.setBackgroundResource(R.drawable.button_exit_red);
                btnStart.setBackgroundResource(R.drawable.clickbtn_red);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "blue":
                layout.setBackgroundResource(R.drawable.background_start_blue);
                exitBtn.setBackgroundResource(R.drawable.button_exit_blue);
                btnStart.setBackgroundResource(R.drawable.clickbtn_blue);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "green":
                layout.setBackgroundResource(R.drawable.background_start_green);
                exitBtn.setBackgroundResource(R.drawable.button_exit_green);
                btnStart.setBackgroundResource(R.drawable.clickbtn_green);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        */

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (pref.getString("theme","")){
            case "red": //만약 "red" 라면 테마 레드계열로 변경
                layout.setBackgroundResource(R.drawable.background_start_red);
                exitBtn.setBackgroundResource(R.drawable.button_exit_red);
                btnStart.setBackgroundResource(R.drawable.clickbtn_red);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + pref.getString("theme","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "blue":
                layout.setBackgroundResource(R.drawable.background_start_blue);
                exitBtn.setBackgroundResource(R.drawable.button_exit_blue);
                btnStart.setBackgroundResource(R.drawable.clickbtn_blue);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + pref.getString("theme","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "green":
                layout.setBackgroundResource(R.drawable.background_start_green);
                exitBtn.setBackgroundResource(R.drawable.button_exit_green);
                btnStart.setBackgroundResource(R.drawable.clickbtn_green);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + pref.getString("theme","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        //안드로이드6.0 (마시멜로) 이후 버전부터 유저 권한설정 필요
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    //거부 시에 앱 종료
                    Toast.makeText(getApplicationContext(),"앱 권한 설정이 필요합니다.",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    //기기의 뒤로가기 터치 시에 다이얼로그 호출
    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(MainStartActivity.this);
        exitDialog.callExitDialogFunction();
    }

}
