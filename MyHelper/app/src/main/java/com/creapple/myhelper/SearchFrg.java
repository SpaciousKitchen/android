package com.creapple.myhelper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapView;

public class SearchFrg extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.search_view, container, false);
        String url="daummaps://open?page=placeSearch";
        final EditText destination= (EditText) rootView.findViewById(R.id.edit_destination);
        Button button = (Button) rootView.findViewById(R.id.search_btn);


        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current;
                String wantogo=destination.getText().toString();
                if(wantogo ==null){
                    Toast.makeText(getActivity(), "도착지를 입력하세요", Toast.LENGTH_SHORT).show();
                }else{
                    Intent webmap =new Intent(Intent.ACTION_VIEW);
                    webmap.setData(Uri.parse("https://map.kakao.com/link/search/카카오"));
                    getActivity().startActivity(webmap);

                }


            }
        });


        return rootView;
    }
}
