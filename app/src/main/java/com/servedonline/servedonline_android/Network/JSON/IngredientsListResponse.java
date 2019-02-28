package com.servedonline.servedonline_android.Network.JSON;

import com.servedonline.servedonline_android.Entity.Ingredient;

public class IngredientsListResponse extends BaseResponse {

    private Ingredient[] data;

    public Ingredient[] getData() {
        return data;
    }
}
