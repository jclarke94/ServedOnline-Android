package com.servedonline.servedonline_android.Network.JSON;

import com.servedonline.servedonline_android.Entity.Recipe;

public class RecipeResponse extends BaseResponse {

    private Recipe[] data;

    public Recipe[] getData() {
        return data;
    }
}
