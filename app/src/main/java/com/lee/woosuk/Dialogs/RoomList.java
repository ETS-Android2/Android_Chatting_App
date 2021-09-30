package com.lee.woosuk.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lee.woosuk.R;


public class RoomList {

    private ListView chat_list;
    private Button backbtn;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private Context context;


    public RoomList(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final EditText user_chat) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        //dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.room_list);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final LinearLayout layout = (LinearLayout) dlg.findViewById(R.id.layout);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button backbtn = (Button) dlg.findViewById(R.id.backbtn);
        final ListView chat_list = (ListView) dlg.findViewById(R.id.chat_list);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });


        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_list.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.getKey());
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //리스트의 아이템 클릭시 발생함수
        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // 문자열변수 selected_item에 리스트의 아이템 텍스트 저장
                String selected_item = (String)adapterView.getItemAtPosition(position);

                //채팅방 리스트 아이템 클릭 시 user_chat(채팅방 입력란)을 채팅방 이름으로 setText
                user_chat.setText(selected_item);

                //다이얼로그 종료
                dlg.dismiss();
            }
        });


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        switch(pref.getString("chat_theme", "")){
            case "red" :
                layout.setBackgroundResource(R.drawable.listbackground_dialog_red);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_red);
                break;
            case "blue" :
                layout.setBackgroundResource(R.drawable.listbackground_dialog_blue);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_blue);
                break;
            case "green" :
                layout.setBackgroundResource(R.drawable.listbackground_dialog_green);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_green);
                break;

        }
    }



}