package com.creapple.myhelper;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.creapple.myhelper.data.Document;
import com.creapple.myhelper.data.LocationService;
import com.creapple.myhelper.data.MaskstockService;
import com.creapple.myhelper.data.Result;
import com.creapple.myhelper.data.Store;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFrag extends Fragment implements MapView.CurrentLocationEventListener,MapView.POIItemEventListener {
    public MapView mapView;
    public double longitude1;
    public double latitude1;
    EditText editlocation;
    Retrofit retrofit;
    Retrofit retrofit2;
    List<Store> stores;
    List<Document> documents;
    BottomSheetBehavior bottomSheetBehavior;
    TextView name;
    TextView stock;
    TextView address;
    public double dest_lat;
    public double dest_long;

    final String BASE_URL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/";
    MaskstockService maskstockService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_view, container, false);
        final EditText editlocation = (EditText) rootView.findViewById(R.id.edit_location);
        Button search = (Button) rootView.findViewById(R.id.search);

        RelativeLayout bottomSheet =(RelativeLayout) rootView.findViewById(R.id.rl_bottom_sheet) ; //부모를 바로 Coordinatorlayout으로 해야함 layput으로 했다가 개고샡함
         name =(TextView) rootView.findViewById(R.id.storename);
        address =(TextView) rootView.findViewById(R.id.address);
        stock =(TextView) rootView.findViewById(R.id.stock);
        Button navi =(Button) rootView.findViewById(R.id.gonavi);

         bottomSheetBehavior =BottomSheetBehavior.from(bottomSheet);
         bottomSheetBehavior.setState(bottomSheetBehavior.STATE_HIDDEN); //숨김상태

        FloatingActionButton refresh = (FloatingActionButton) rootView.findViewById(R.id.floatingRenew); //floating button 설정 ==>xml에서 다르게 해야함 예제랑

        refresh.setOnClickListener(new refreshclickListener());//flating Onclicklistener 등록
        mapView = new MapView(getActivity());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editlocation.getWindowToken(), 0);

        ViewGroup mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map);


        //현재 좌표값 입력 받기
        LocationManager ln = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); //fragment에서 는 가지고 있찌 않은 메소드 이기에 getTactivity를 해줘야한다.

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            Location location = ln.getLastKnownLocation(LocationManager.GPS_PROVIDER);//기징 최근 정보를 가지고 오기
            if (location != null) {
                String provider = location.getProvider();
                longitude1 = location.getLongitude();
                latitude1 = location.getLatitude();

                ln.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListner);
            }

        }

        mapView.setCurrentLocationEventListener(this);
        mapView.setDaumMapApiKey(" eb7e640b74c2adee8272ec7d6a35b966");
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading); //움직이는 거 없이 크랭킹
        mapView.setPOIItemEventListener(this);
        mapView.zoomIn(true);
        mapView.zoomOut(true);
        requestData(latitude1, longitude1);
        mapViewContainer.addView(mapView);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                String keyword = editlocation.getText().toString();
                searchLocation(keyword);
            }
        });

         navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String current;
                String wantogo=editlocation.getText().toString();
                if(wantogo ==null){
                    Toast.makeText(getActivity(), "도착지를 입력하세요", Toast.LENGTH_SHORT).show();
                }else{
                    Intent webmap =new Intent(Intent.ACTION_VIEW);
                    webmap.setData(Uri.parse("daummaps://route?sp="+latitude1+","+longitude1+"&ep="+dest_lat+","+dest_long+"&by=CAR"));
                    getActivity().startActivity(webmap);
                }

            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    //case  bottomSheetBehavior.STATE_COLLAPSED:

                }

           }

            @Override
            public void onSlide(@NonNull View view, float v) {
                // BottomSheet의 offset 정보를 받을 수 있습니다. 1
            }
        });



        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());


        return rootView;
    }

    //floating button onClicklistener
    class refreshclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude1, longitude1), true);

        }
    }

    public void searchLocation(String keyword) {
        retrofit2 = new Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        LocationService locationService = retrofit2.create(LocationService.class);
        locationService.getLocation(keyword).enqueue(new Callback<com.creapple.myhelper.data.Location>() {
            @Override
            public void onResponse(Call<com.creapple.myhelper.data.Location> call, Response<com.creapple.myhelper.data.Location> response) {
                if (response.isSuccessful()) {
                    Log.d("통신!!!!!!!!!!!! :", "서치 통신성공");
                    documents = response.body().getDocuments();

                    if (documents.size() == 0) {
                        Toast.makeText(getActivity(), "위치를 다시 입력하세요", Toast.LENGTH_SHORT).show();

                    } else {
                        double x = Double.parseDouble(documents.get(0).getX());
                        double y = Double.parseDouble(documents.get(0).getY());
                        Log.d("위도 경로", "위도 , 경도:" + x + "," + y);

                        requestData(y, x);
                    }


                } else {
                    Log.d("통신!!!!!!! :", "서치 에러1:" + response.message());
                }
            }

            @Override
            public void onFailure(Call<com.creapple.myhelper.data.Location> call, Throwable t) {
                Log.d("통신!!!!!!! :", "서치 에러2:" + t.getMessage());

            }
        });


    }

    public void requestData(final double lat, final double lng) {

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .build();

        maskstockService = retrofit.create(MaskstockService.class);
        //서비스와 연결
        maskstockService.getResult(lat, lng, 750).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Log.d("통신!!!!!!!!!!!!! :", "통신성공");

                    stores = response.body().getStores();
                    Log.d("data :", "store 갯수 : " + response.message());


                    customView(lat, lng);

                } else {
                    Log.d("통신!!!!!!!! :", "에러1:" + response);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("통신!!!!!!! :", "에러2:" + t.getMessage());

            }
        });
    }

    //단말의 현위치 좌표값을 통보받을 수 있다.
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        mapView.setMapCenterPoint(mapPoint, true);

    }

    //단말의 방향(Heading) 각도값을 통보받을 수 있다.
    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    // Callout Balloon(말풍선)이 아이콘(마커)위에 나타난다.
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        Store store=stores.get(mapPOIItem.getTag());
        Log.d("터치한!!!!!!!!!!1",store.getName());
        Log.d("터치한!!!!!!!!!!1",store.getAddr());
        Log.d("터치한!!!!!!!!!!1","경도"+store.getLat());

        bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED); //높이만큼 노출
        name.setText(store.getName());
        address.setText(store.getAddr());

        if (store.getRemainStat() != null) {
            if (store.getRemainStat().equals("empty")) {
                stock.setText("1개이하");


            } else if (store.getRemainStat().equals("plenty")) {
                stock.setText("100개 이상");
                stock.setTextColor(Color.parseColor("#008000"));

            } else if (store.getRemainStat().equals("some")) {
                stock.setText("100~30개");
                stock.setTextColor(Color.parseColor("#FFD700"));



            } else if (store.getRemainStat().equals("few")) {
                stock.setText("30개~2개");
                stock.setTextColor(Color.parseColor("#FF4500"));

            } else if (store.getRemainStat().equals("break")) {
                stock.setText("판매 중지");
                stock.setTextColor(Color.parseColor("#778899"));
            }
        } else {
            stock.setText("정보 없음");
            stock.setTextColor(Color.parseColor("#778899"));
        }


        dest_lat=store.getLat();
        dest_long=store.getLng();







    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    //custom마크 띄우
    public void customView(double lat, double lng) {


        //Log.d("정","정"+stores.get(0).getLat()+stores.get(0).getLng()) ;
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lng), true);


        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(lat, lng), // center
                500, // radius
                Color.argb(40, 66, 135, 245), // strokeColor
                Color.argb(40, 66, 135, 245) // fillColor
        );

        mapView.addCircle(circle1);


        for (int i = 0; i < stores.size(); i++) {
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(stores.get(i).getRemainStat());
            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(stores.get(i).getLat(), stores.get(i).getLng()));
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            String stock_remain = stores.get(i).getRemainStat();
            if (stock_remain != null) {
                if (stock_remain.equals("empty")) {
                    marker.setCustomImageResourceId(R.drawable.mask_x);
                    Log.d("status1", "remain:" + stock_remain);

                } else if (stock_remain.equals("plenty")) {
                    marker.setCustomImageResourceId(R.drawable.mask_many);
                    Log.d("status2", "remain:" + stock_remain);

                } else if (stock_remain.equals("some")) {
                    marker.setCustomImageResourceId(R.drawable.mask_some);
                    Log.d("status3", "remain:" + stock_remain);


                } else if (stock_remain.equals("few")) {
                    marker.setCustomImageResourceId(R.drawable.mask_few);
                    Log.d("status4", "remain:" + stock_remain);


                } else if (stock_remain.equals("break")) {
                    marker.setCustomImageResourceId(R.drawable.closed);
                    Log.d("status5", "remain:" + stock_remain);


                }

            } else {
                marker.setCustomImageResourceId(R.drawable.mask_x);
                Log.d("status6", "remain:" + stock_remain);


            }


            marker.setCustomImageAnchor(0.5f, 1.0f);
            // 기본으로 제공하는 BluePin 마커 모양.
            //marker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            //stores.get(i);
            mapView.addPOIItem(marker);

            //stores.
            //stores.put(marker.getTag(),)



        }


    }

    final LocationListener gpsLocationListner = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();
            longitude1 = location.getLongitude();
            latitude1 = location.getLatitude();
            //Log.e("위치::","경도"+longitude1+"위도"+latitude1);


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.customview, null);

        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            /*  Store store=stores.get(poiItem.getTag());

            //완전 바보 같은 실수였음....내가.... cutomview를 math_parrent로 설정해서
            if (store.getRemainStat() != null) {
                if (store.getRemainStat().equals("empty")) {
                    ((TextView) mCalloutBalloon.findViewById(R.id.remain_state)).setText("품절");

                } else if (store.getRemainStat().equals("plenty")) {
                    ((TextView) mCalloutBalloon.findViewById(R.id.remain_state)).setText("충분");

                } else if (store.getRemainStat().equals("some")) {
                    ((TextView) mCalloutBalloon.findViewById(R.id.remain_state)).setText("보통");

                } else if (store.getRemainStat().equals("few")) {
                    ((TextView) mCalloutBalloon.findViewById(R.id.remain_state)).setText("소량");


                } else if (store.getRemainStat().equals("break")) {
                    ((TextView) mCalloutBalloon.findViewById(R.id.remain_state)).setText("정보 없음");
                }
            } else {
                //((TextView) mCalloutBalloon.findViewById(R.id.).setText(store.getRemainStat());
            }*/



            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }
}



