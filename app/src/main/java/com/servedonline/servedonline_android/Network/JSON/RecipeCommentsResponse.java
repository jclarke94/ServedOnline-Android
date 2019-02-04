package com.servedonline.servedonline_android.Network.JSON;

import com.servedonline.servedonline_android.Entity.RecipeComments;

public class RecipeCommentsResponse extends BaseResponse {

    private RecipeComments[] data;

    public RecipeComments[] getData() {
        return data;
    }
}
