//package com.creapple.movieapp;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class ParcelDate implements Parcelable {
////
////    String id;
////    String time;
////    float rating;
////    String comment;
////
////    public ParcelDate(String id, String time, float rating, String comment) {
////        this.id = id;
////        this.time = time;
////        this.rating = rating;
////        this.comment = comment;
////    }//생성자 만듦
////
////
////    public  static final  Parcelable.Creator CREATOR =new  Parcelable.Creator(){
////
////        public ParcelDate createFromParcel(Parcel src){
////            return new ParcelDate(src); //parcel 호출 후 리턴
////        }
////
////        public ParcelDate[] newArray(int size){
////            return  new ParcelDate[size];
////        }
////
////
////    };
////
////    @Override
////    public int describeContents() {
////        return 0;
////    }
////
////    @Override
////    public void writeToParcel(Parcel dest, int flags) {
////
////        dest.writeString(id);
////        dest.writeString(time);
////        dest.writeFloat(rating);
////        dest.writeString(comment);
////
////    }
////
////    private void readFromParcel(Parcel in){
////       id=in.readString();
////        time=in.readString();
////        rating=in.readFloat();
////        comment=in.readString();
////
////
////
////    }
//}
