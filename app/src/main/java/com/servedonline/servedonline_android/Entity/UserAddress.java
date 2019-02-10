package com.servedonline.servedonline_android.Entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.servedonline.servedonline_android.Database.DatabaseColumns;
import com.servedonline.servedonline_android.Database.DatabaseGoverned;
import com.servedonline.servedonline_android.Database.DatabaseTables;
import com.servedonline.servedonline_android.util.CursorUtils;

public class UserAddress extends DatabaseGoverned implements Parcelable {

    private int id, userId, primaryAddress;
    private String address1, address2, address3, city, county, country;

    public UserAddress(int id, int userId, int primaryAddress, String address1, String address2, String address3, String city, String county, String country) {
        this.id = id;
        this.userId = userId;
        this.primaryAddress = primaryAddress;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.city = city;
        this.county = county;
        this.country = country;
    }

    public UserAddress(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        primaryAddress = in.readInt();
        address1 = in.readString();
        address2 = in.readString();
        address3 = in.readString();
        city = in.readString();
        county = in.readString();
        country = in.readString();
    }

    public UserAddress(Cursor cursor) {
        id = CursorUtils.getCursorValue(cursor, DatabaseColumns.ID, id);
        userId = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.USER_ID, userId);
        primaryAddress = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.PRIMARY_ADDRESS, primaryAddress);
        address1 = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.ADDRESS_1, address1);
        address2 = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.ADDRESS_2, address2);
        address3 = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.ADDRESS_3, address3);
        city = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.CITY, city);
        county = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.COUNTY, county);
        country = CursorUtils.getCursorValue(cursor, DatabaseColumns.UserAddress.COUNTRY, country);
    }

    @Override
    public String getDatabaseTable() {
        return DatabaseTables.USER_ADDRESS;
    }

    @Override
    public String getDatabaseId() {
        return String.valueOf(id);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues out = new ContentValues();
        out.put(DatabaseColumns.ID, id);
        out.put(DatabaseColumns.UserAddress.USER_ID, userId);
        out.put(DatabaseColumns.UserAddress.PRIMARY_ADDRESS, primaryAddress);
        out.put(DatabaseColumns.UserAddress.ADDRESS_1, address1);
        out.put(DatabaseColumns.UserAddress.ADDRESS_2, address2);
        out.put(DatabaseColumns.UserAddress.ADDRESS_3, address3);
        out.put(DatabaseColumns.UserAddress.CITY, city);
        out.put(DatabaseColumns.UserAddress.COUNTY, county);
        out.put(DatabaseColumns.UserAddress.COUNTRY, country);
        return out;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPrimaryAddress() {
        return primaryAddress;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public static final Creator<UserAddress> CREATOR = new Creator<UserAddress>() {
        @Override
        public UserAddress createFromParcel(Parcel in) {
            return new UserAddress(in);
        }

        @Override
        public UserAddress[] newArray(int size) {
            return new UserAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeInt(primaryAddress);
        parcel.writeString(address1);
        parcel.writeString(address2);
        parcel.writeString(address3);
        parcel.writeString(city);
        parcel.writeString(county);
        parcel.writeString(country);
    }
}
