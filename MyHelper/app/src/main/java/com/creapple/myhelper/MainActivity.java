package com.creapple.myhelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.creapple.myhelper.data.MaskstockService;
import com.creapple.myhelper.data.Result;
import com.creapple.myhelper.data.Store;
import com.google.android.material.tabs.TabLayout;

import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Result result;
    MaskstockService maskstockService;
    Store store;
    MapFrag mapFrag;
    SearchFrg searchFrg;
    SettingFrag settingFrag;
    TabLayout tabLayout;

    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout =(TabLayout) findViewById(R.id.tab_layout);

        mapFrag=new MapFrag();
        searchFrg =new SearchFrg();
        settingFrag =new SettingFrag();
        //requestData();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mapFrag).commit();
        //tab선택시 작용하는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab의 상태가 선택되지 않음에서 선택 상태로 변경됨. (주로 활용)
                int pos =tab.getPosition();
                Fragment current=null;
                if(pos==0){
                    current=mapFrag;

                }else if(pos==1){
                    current=searchFrg;


                }else if(pos==2){
                    current=settingFrag;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,current).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab의 상태가 선택 상태에서 선택되지 않음으로 변경됨.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            //이미 선택된 상태의 tab이 사용자에 의해 다시 선택됨.
            }
        });

    }







}
