package com.lee.woosuk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends PreferenceFragment {

    private SharedPreferences pref, sf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }
    @Override
    public void onResume() {
        super.onResume();
        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }
    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if(key.equals("theme")){
                String datas = pref.getString("theme", null);
                if(datas.equals("red")){
                    Toast.makeText(getActivity(), "테마 색상 : "+datas, Toast.LENGTH_SHORT).show();

                }
                else if(datas.equals("blue")){
                    Toast.makeText(getActivity(), "테마 색상 : "+datas, Toast.LENGTH_SHORT).show();
                }
                
                else if(datas.equals("green")){
                    Toast.makeText(getActivity(), "테마 색상 : "+datas, Toast.LENGTH_SHORT).show();
                }
            }
            else if(key.equals("chat_theme")){
                String datas = pref.getString("chat_theme", null);
                if(datas.equals("red")){
                    Toast.makeText(getActivity(), "채팅 테마 색상 : "+datas, Toast.LENGTH_SHORT).show();

                }
                else if(datas.equals("blue")){
                    Toast.makeText(getActivity(), "채팅 테마 색상 : "+datas, Toast.LENGTH_SHORT).show();
                }

                else if(datas.equals("green")){
                    Toast.makeText(getActivity(), "채팅 테마 색상 : "+datas, Toast.LENGTH_SHORT).show();
                }
            }
            else if(key.equals("switch")){
                boolean bool = pref.getBoolean("switch", false);
                Toast.makeText(getActivity(), "스위치 is : "+ bool, Toast.LENGTH_SHORT).show();
            }
            else if(key.equals("check")){
                boolean bool = pref.getBoolean("check", false);
                Toast.makeText(getActivity(), "체크박스 is : "+ bool, Toast.LENGTH_SHORT).show();
            }
            else if(key.equals("multi")){
                Set<String> ary = pref.getStringSet(key, null);
                Toast.makeText(getActivity(), "다중선택 is : "+ ary, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
