package com.servedonline.servedonline_android.Network.JSON;

import com.servedonline.servedonline_android.Entity.RecipeLikes;

public class RecipeLikesResponse extends BaseResponse {

    private RecipeLikes[] data;

    public RecipeLikes[] getData() {
        return data;
    }
}
