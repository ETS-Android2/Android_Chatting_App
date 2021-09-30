package com.lee.woosuk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.woosuk.Dialogs.CustomDialog;
import com.lee.woosuk.Dialogs.RoomList;

public class MainActivity extends AppCompatActivity {

    //SettingFragment 사용 시
    //private SharedPreferences pref;

    private String datas;

    private ConstraintLayout layout;

    private EditText user_chat, user_edit;
    private Button user_next, btn_list, backbtn, qbtn;
    private ImageButton themebtn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.main_layout);

        user_chat = (EditText) findViewById(R.id.user_chat);
        user_edit = (EditText) findViewById(R.id.user_edit);
        user_next = (Button) findViewById(R.id.user_next);
        btn_list = (Button) findViewById(R.id.btn_list);
        backbtn = (Button) findViewById(R.id.backbtn);
        qbtn = (Button) findViewById(R.id.qbtn);
        themebtn = (ImageButton) findViewById(R.id.menu_theme);



        /*
        final Intent intent = new Intent(MainActivity.this, MainStartActivity.class);
        final SharedPreferences sf = getSharedPreferences("test", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sf.edit();


        //테마 선택 버튼 클릭 시 발생 함수 (팝업 메뉴 호출 방식)
        themebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getApplicationContext(), v); //v 는 클릭된 View를 의미
                getMenuInflater().inflate(R.menu.theme_menu, popup.getMenu());

                //팝업 메뉴
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            //팝업메뉴 red 선택 시
                            case R.id.red :
                                datas = "red";  // String datas 에 "red" 대입
                                editor.putString("First", datas); // 고유한 Key값 "First"에 String datas 삽입 ("red")
                                editor.commit(); // commit
                                // 색상 변경은 액티비티 페이지 Reload 를 해주거나 또는 앱을 재시작(껏다켜줌)으로 적용 가능.
                                // 아래 코드는 테마 변경을 위한 앱 재시작 로직
                                finishAffinity(); // Root 액티비티 종료(MainStartActivity) - 즉, 루트 액티비티를 finish 했기 때문에 앱이 종료.
                                startActivity(intent); // 정의한 intent 실행 (MainStartActivity 실행)
                                System.exit(0); // System.exit(0)은 앱과 관련한 프로세스나 블루투스 등과같은 기기들이 전부 해제된다.
                                Toast.makeText(getApplicationContext(), "테마 색상 " + sf.getString("First","") + " 변경", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.blue :
                                datas = "blue";
                                editor.putString("First", datas);
                                editor.commit();
                                finishAffinity();
                                startActivity(intent);
                                System.exit(0);
                                Toast.makeText(getApplicationContext(), "테마 색상 " + sf.getString("First","") + " 변경", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.green :
                                datas = "green";
                                editor.putString("First", datas);
                                editor.commit();
                                finishAffinity();
                                startActivity(intent);
                                System.exit(0);
                                Toast.makeText(getApplicationContext(), "테마 색상 " + sf.getString("First","") + " 변경", Toast.LENGTH_SHORT).show();
                                break;

                        }
                        return false;
                    }
                });
                popup.show();

            }
        });
        */



        /*
        // 키값 "First"를 읽어서 (팝업 호출 방식 연동)
        switch (sf.getString("First","")){
            case "red": //만약 "red" 라면 테마 레드계열로 변경
                layout.setBackgroundResource(R.drawable.background_main_red);
                btn_list.setBackgroundResource(R.drawable.clickbtn2);
                backbtn.setBackgroundResource(R.drawable.button_back_red);
                qbtn.setBackgroundResource(R.drawable.button_q_red);
                user_next.setBackgroundResource(R.drawable.clickbtn_red);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "blue":
                layout.setBackgroundResource(R.drawable.background_main_blue);
                btn_list.setBackgroundResource(R.drawable.clickbtn_blue);
                backbtn.setBackgroundResource(R.drawable.button_back_blue);
                qbtn.setBackgroundResource(R.drawable.button_q_blue);
                user_next.setBackgroundResource(R.drawable.clickbtn_blue);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
            case "green":
                layout.setBackgroundResource(R.drawable.background_main_green);
                btn_list.setBackgroundResource(R.drawable.clickbtn_green);
                backbtn.setBackgroundResource(R.drawable.button_back_green);
                qbtn.setBackgroundResource(R.drawable.button_q_green);
                user_next.setBackgroundResource(R.drawable.clickbtn_green);
                Toast.makeText(getApplicationContext(), "현재 테마 색상은 " + sf.getString("First","") + " 입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        */

        //PreferenceFragment 호출
        themebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        /*PreferenceFragment 사용 시
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pref.getString("theme", "") 사용으로 값 읽어들이기.
        Toast.makeText(getApplicationContext(), "SettingFragment 값: " + pref.getString("theme", ""),Toast.LENGTH_SHORT).show();
        */

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (pref.getString("theme","")){
            case "red": //만약 "red" 라면 테마 레드계열로 변경
                layout.setBackgroundResource(R.drawable.background_main_red);
                btn_list.setBackgroundResource(R.drawable.clickbtn2);
                backbtn.setBackgroundResource(R.drawable.button_back_red);
                qbtn.setBackgroundResource(R.drawable.button_q_red);
                user_next.setBackgroundResource(R.drawable.clickbtn_red);
                break;
            case "blue":
                layout.setBackgroundResource(R.drawable.background_main_blue);
                btn_list.setBackgroundResource(R.drawable.clickbtn_blue);
                backbtn.setBackgroundResource(R.drawable.button_back_blue);
                qbtn.setBackgroundResource(R.drawable.button_q_blue);
                user_next.setBackgroundResource(R.drawable.clickbtn_blue);
                break;
            case "green":
                layout.setBackgroundResource(R.drawable.background_main_green);
                btn_list.setBackgroundResource(R.drawable.clickbtn_green);
                backbtn.setBackgroundResource(R.drawable.button_back_green);
                qbtn.setBackgroundResource(R.drawable.button_q_green);
                user_next.setBackgroundResource(R.drawable.clickbtn_green);
                break;
        }


        user_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_edit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (user_chat.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "입장할 채팅방 이름을 입력/선택 해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }


                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("chatName", user_chat.getText().toString());
                intent.putExtra("userName", user_edit.getText().toString());

                startActivity(intent);
            }
        });

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다.
                RoomList roomList = new RoomList(MainActivity.this);
                // 커스텀 다이얼로그를 호출한다.
                roomList.callFunction(user_chat);

            }
        });

        //ChatRoomList 에서 받은 리스트 String을 채팅방 이름에 대입
        String room_name = getIntent().getStringExtra("chatlistitem");
        user_chat.setText(room_name);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        qbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다.
                CustomDialog customDialog = new CustomDialog(MainActivity.this);
                // 커스텀 다이얼로그를 호출한다.
                customDialog.callFunction();
            }
        });


    }


}