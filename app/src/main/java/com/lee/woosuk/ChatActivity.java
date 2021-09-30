package com.lee.woosuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lee.woosuk.Adapters.ChatAdapter;
import com.lee.woosuk.Adapters.ImgAdapter;
import com.lee.woosuk.DTOs.ChatDTO;
import com.lee.woosuk.DTOs.ImgDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private final int REQ_CODE = 1234;
    private Uri filePath;
    private ImageView imgPreview;

    private RelativeLayout layout;

    private InputMethodManager imm;

    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_view;
    private ChatAdapter adapter;
    private ImgAdapter imgadapter;

    private EditText chat_edit;
    private Button chat_send, backbtn, addbtn;
    private ImageButton sttbtn;
    private TextView room_title;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    //시간 설정, 패턴
    Date today = new Date();
    SimpleDateFormat today_file_format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
    SimpleDateFormat timeNow = new SimpleDateFormat("a K:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        // 위젯 ID 참조
        layout = (RelativeLayout) findViewById(R.id.layout);
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);
        sttbtn = (ImageButton) findViewById(R.id.stt_btn);
        room_title = (TextView) findViewById(R.id.room_title);
        backbtn = (Button) findViewById(R.id.backbtn);
        addbtn = (Button) findViewById(R.id.addbtn);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);

        // 로그인 화면(MainActivity)에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");

        // 채팅 방 입장
        openChat(CHAT_NAME);
        openimg(CHAT_NAME);
        room_title.setText(CHAT_NAME + " 채팅방 입니다.");

        //STT 버튼
        sttbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSTT();
            }
        });

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "메세지를 먼저 입력해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (filePath != null) {
                    uploadFile();

                    //이미지 업로드 후 메세지 입력부분 이미지 프리뷰 제거
                    imgPreview.getLayoutParams().width = 0;
                    imgPreview.getLayoutParams().height = 0;
                    imgPreview.requestLayout();


                }

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString(), timeNow.format(today)); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 서버로 데이터 전송
                chat_edit.setText(""); //입력창 초기화

                imm.hideSoftInputFromWindow(chat_edit.getWindowToken(), 0); // SEND버튼 클릭시 키보드 자동 숨기기

                new Handler().postDelayed(new Runnable() { // 스크롤 작동x 스튜디오 버그 -> 해결: 딜레이시간을 준다.
                    @Override
                    public void run() {
                        chat_view.smoothScrollToPosition(adapter.getCount()-1); // 채팅창 꽉찾을때 추가 채팅입력시 자동으로 아래로 스크롤
                    }
                }, 100);

            }
        });


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        switch(pref.getString("chat_theme", "")){
            case "red" :
                layout.setBackgroundResource(R.drawable.background_chat_red);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_red);
                addbtn.setBackgroundResource(R.drawable.button_add_chat_red);
                sttbtn.setBackgroundResource(R.drawable.clickbtn_chat_red);
                chat_send.setBackgroundResource(R.drawable.clickbtn_chat_red);
                break;
            case "blue" :
                layout.setBackgroundResource(R.drawable.background_chat_blue);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_blue);
                addbtn.setBackgroundResource(R.drawable.button_add_chat_blue);
                sttbtn.setBackgroundResource(R.drawable.clickbtn_chat_blue);
                chat_send.setBackgroundResource(R.drawable.clickbtn_chat_blue);
                break;
            case "green" :
                layout.setBackgroundResource(R.drawable.background_chat_green);
                backbtn.setBackgroundResource(R.drawable.button_back_chat_green);
                addbtn.setBackgroundResource(R.drawable.button_add_chat_green);
                sttbtn.setBackgroundResource(R.drawable.clickbtn_chat_green);
                chat_send.setBackgroundResource(R.drawable.clickbtn_chat_green);
                break;
        }


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void StartSTT() {
        //onActivityResult 관련 코드 라인 323~
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //안드로이드 설정의 기본 언어 값에 따라 STT 인식언어 설정
        //ex) 안드로이드 기본언어가 영어라면 -> STT 인식언어 또한 영어로 인식
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"음성인식 중 ...");
        //intent.putExtra 시에(값을 뿌려줄 때) 리퀘스트 코드(int형 변수 REQ_CODE)로 식별
        //엑티비티가 여러가지 일때 헷갈리지 않도록하기 위해 int 형식의 코드로 엑티비티 구분
        //내 코드에서는 STT를 사용할 엑티비티가 1개뿐이라 큰 의미는 없음 (큰 의미가 없기에 그냥 "1234"로 설정)
        startActivityForResult(intent, REQ_CODE);
    }

    /*
    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO value = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(value.getUserName() + " : " + value.getMessage());
    }
    */
    private void openChat(final String chatName) {


        // 리스트 어댑터 생성 및 세팅
        adapter = new ChatAdapter();
        chat_view.setAdapter(adapter); // 리스트 뷰에 어댑터 연결

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {


                //addMessage(dataSnapshot, adapter);
                ChatDTO value = dataSnapshot.getValue(ChatDTO.class);
                adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), value.getUserName(), "  " + value.getMessage() + "  ", value.getTime());

                chat_view.smoothScrollToPosition(adapter.getCount()-1); // 채팅창 꽉찾을때 추가 채팅입력시 자동으로 아래로 스크롤

                adapter.notifyDataSetChanged();//리스트(채팅내역) 목록 새로고침

                //Log.e("LOG", "s:"+s);

                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popup = new PopupMenu(getApplicationContext(), view);

                        getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.m1:
                                        //이미지를 선택
                                        Intent intent = new Intent();
                                        intent.setType("image/*");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        //호출한 엑티비티에서 결과받기
                                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
                                        break;
                                    case R.id.m2: //채팅 로그 저장
                                        databaseReference.child("chat").child(chatName).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                //서버에 저장된 채팅 내역을 줄바꿔가면서 String에 저장
                                                //replaceAll 을 사용해서 줄을 바꿔주지 않으면 전체 내용이 한줄로 출력되어버림.

                                                String replace = "채팅방 이름 : "+chatName + dataSnapshot.getValue().toString();
                                                String replace1 = replace.replaceAll(", ", "\n");
                                                String replace2 = replace1.replaceAll("\\{-", "\n\n"); //chatName
                                                String replace3 = replace2.replaceAll("=\\{", "\n"); //time부분
                                                String replace4 = replace3.replaceAll("\\}", "\n");//마지막줄바꿈

                                                //외부 저장소(External Storage)가 마운트(인식) 되었을 때 동작
                                                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                                                    //Environment.DIRECTORY_DOWNLOADS - 기기의 기본 다운로드 폴더
                                                    //다운로드 폴더에 오늘날짜+시간 이름으로 txt 파일 저장
                                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), today_file_format.format(today) + ".txt");
                                                    try{
                                                        FileWriter fw = new FileWriter(file, false);
                                                        fw.write(replace4);
                                                        fw.close();
                                                    } catch (IOException e){
                                                        e.printStackTrace();
                                                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                                                }
                                            }


                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        Toast.makeText(getApplicationContext(),"대화 내용 저장 완료!",Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });
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
    }


    //결과 처리, 액티비티 A에서 B로 갔다가 다시 A로 넘어올 때 사용하는 매소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){ // RESULT_OK = 설정한 액티비티에서 설정 성공 (즉, 탐색기에서 파일 선택을 성공했다면..)
            filePath = data.getData();
            //Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPreview.setImageBitmap(bitmap);
                imgPreview.getLayoutParams().width = 200;
                imgPreview.getLayoutParams().height = 400;
                imgPreview.requestLayout();
                chat_edit.setText("이미지 삽입");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //STT관련 코드
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    chat_edit.setText(result.get(0));
                }
                break;
            }

        }
    }

    //upload the file 다이얼로그
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //파일명 설정
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_mmss");
            Date now = new Date();
            final String filename = formatter.format(now) + ".png";

            //파이어베이스 서버 storage
            final FirebaseStorage storage = FirebaseStorage.getInstance();

            //storage 주소와 폴더 파일명을 지정해 준다.
            final StorageReference storageRef = storage.getReferenceFromUrl("gs://macos-dceae.appspot.com").child("images/" + filename);

            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기

                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();

                            //Glide 옵션 설정
                            RequestOptions requestOptions = new RequestOptions()
                                    // 캐시 사용 해제, 파이어베이스 사용 시 느리기 때문에 사용
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE);

                            //서버 스토리지에 업로드된 이미지 Url을 받아 어뎁터 내의 Glide로 이미지 뿌려주기.
                            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        ImgDTO chat = new ImgDTO(USER_NAME, chat_edit.getText().toString(), timeNow.format(today), task.getResult().toString()); //ImgDTO를 이용하여 데이터를 묶는다.
                                        databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 서버로 데이터 전송
                                        chat_view.smoothScrollToPosition(adapter.getCount()-1); // 채팅창 꽉찾을때 추가 채팅입력시 자동으로 아래로 스크롤
                                        imgadapter.notifyDataSetChanged();//리스트(채팅내역) 목록 새로고침
                                        filePath = null; //이미지 삽입 후 filePath 내의 데이터 값 초기화
                                    }
                                }
                            });

                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다.
                            double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openimg (String CHAT_NAME){
        imgadapter = new ImgAdapter();
        chat_view.setAdapter(imgadapter);
        databaseReference.child("chat").child(CHAT_NAME).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ImgDTO value = dataSnapshot.getValue(ImgDTO.class);
                imgadapter.addimg(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), value.getUserName(), "  " + value.getMessage() + "  ", value.getTime(), value.getImg());

                new Handler().postDelayed(new Runnable() { // 스크롤 작동x 스튜디오 버그 -> 해결: 딜레이시간을 준다.
                    @Override
                    public void run() {
                        chat_view.smoothScrollToPosition(adapter.getCount()-1); // 채팅창 꽉찾을때 추가 채팅입력시 자동으로 아래로 스크롤
                    }
                }, 200);

                imgadapter.notifyDataSetChanged();//리스트(채팅내역) 목록 새로고침

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}