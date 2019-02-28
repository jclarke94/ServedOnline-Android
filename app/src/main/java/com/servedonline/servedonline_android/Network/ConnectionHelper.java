package com.servedonline.servedonline_android.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.servedonline.servedonline_android.Entity.Ingredient;
import com.servedonline.servedonline_android.Entity.RecipeSteps;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.Network.JSON.BaseResponse;
import com.servedonline.servedonline_android.Network.JSON.IDResponse;
import com.servedonline.servedonline_android.Network.JSON.IngredientsListResponse;
import com.servedonline.servedonline_android.Network.JSON.RecipeResponse;
import com.servedonline.servedonline_android.Network.JSON.UserResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectionHelper {

    private final static String BASE_URL = "http://www.servedonline-app.com/";

    private Context context;
    private Handler mainThreadHandler;
    private OkHttpClient okHttpClient;
    private Gson gson;
    private SharedPreferences sp;

    public ConnectionHelper(Context context) {
        this.context = context;
        mainThreadHandler = new Handler(context.getMainLooper());
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();

        gson = new Gson();
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public UserResponse createUser(User user) {
        String url = BASE_URL + "User/createUser";

        FormBody body = new FormBody.Builder()
                .add("firstName", user.getFirstName())
                .add("lastName", user.getLastName())
                .add("email", user.getEmail())
                .add("password", user.getPassword())
                .build();

        return performBasicNetworking(url, body, UserResponse.class);
    }

    public UserResponse loginUser(String email, String password) {
        String url = BASE_URL + "User/getUser";

        FormBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        return performBasicNetworking(url, body, UserResponse.class);
    }

    public RecipeResponse getRecipes(int userId) {
        String url = BASE_URL + "Recipe/getRecipes";
        //todo add userId form to specify which recipes to get back
        return performBasicNetworking(url, null, RecipeResponse.class);
    }

    public IDResponse createNewRecipe(int userId, String recipeTitle, String recipeDescription, int timerLength) {
        String url = BASE_URL + "Recipe/createRecipe";

        FormBody body = new FormBody.Builder()
                .add("userId", String.valueOf(userId))
                .add("recipeTitle", recipeTitle)
                .add("recipeDescription", recipeDescription)
                .add("timerLength", String.valueOf(timerLength))
                .build();

        return performBasicNetworking(url, body, IDResponse.class);
    }

    public IDResponse createStep(RecipeSteps step, ArrayList<Ingredient> ingredients) {
        String url = BASE_URL + "RecipeSteps/createStep";

        ArrayList<ArrayMap<String, String>> data = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ArrayMap<String, String> item = new ArrayMap<>();

            item.put("recipeId", String.valueOf(ingredient.getRecipeId()));
            item.put("stepNumber", String.valueOf(ingredient.getStepNumber()));
            item.put("ingredient", ingredient.getIngredient());

            data.add(item);
        }

        FormBody body = new FormBody.Builder()
                .add("recipeId", String.valueOf(step.getRecipeId()))
                .add("stepDescription", step.getStepDescription())
                .add("stepNumber", String.valueOf(step.getStepNumber()))
                .add("finalStep", String.valueOf(step.getFinalStep()))
                .add("timer", String.valueOf(step.getTimer()))
                .add("data", gson.toJson(data))
                .build();

        return performBasicNetworking(url, body, IDResponse.class);
    }

    public IDResponse createNewRecipeStep(int recipeId, String stepDescription, int stepNumber, int finalStep, int timer) {
        String url = BASE_URL + "RecipeSteps/createStep";

        FormBody body = new FormBody.Builder()
                .add("recipeId", String.valueOf(recipeId))
                .add("stepDescription", stepDescription)
                .add("stepNumber", String.valueOf(stepNumber))
                .add("finalStep", String.valueOf(finalStep))
                .build();

        return performBasicNetworking(url, body, IDResponse.class);
    }

    public BaseResponse deleteRecipeStep(int id) {

        String url = BASE_URL + "RecipeSteps/removeStep";

        FormBody body = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .build();

        return performBasicNetworking(url, body, BaseResponse.class);

    }

    public IDResponse createRecipeIngredient(int recipeId, int stepNumber, String ingredient) {
        String url = BASE_URL + "RecipeIngredients/createIngredient";

        FormBody body = new FormBody.Builder()
                .add("recipeId", String.valueOf(recipeId))
                .add("stepNumber", String.valueOf(stepNumber))
                .add("ingredient", ingredient)
                .build();

        return performBasicNetworking(url, body, IDResponse.class);
    }

    public IngredientsListResponse getIngredients(int recipeId) {
        String url = BASE_URL + "RecipeIngredients/getIngredients";

        FormBody body = new FormBody.Builder()
                .add("recipeId", String.valueOf(recipeId))
                .build();

        return performBasicNetworking(url, body, IngredientsListResponse.class);
    }

    public BaseResponse deleteIngredient(int id) {
        String url = BASE_URL + "RecipeIngredients/removeIngredient";

        FormBody body = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .build();

        return performBasicNetworking(url, body, BaseResponse.class);
    }

    /**
     * Performs a very basic Network call, simplifies networking removing a lot of boilerplate
     * @param url               Url to call
     * @param body              RequestBody to send with the request or NULL
     * @param outClass          Output class to return the parsed JSON object as
     * @param <T>               Must be a subclass of BaseResponse
     * @return                  Gson-parsed output of the network call or NULL
     */
    @Nullable
    private <T extends BaseResponse> T performBasicNetworking(@NonNull String url, @Nullable RequestBody body, @NonNull Class<T> outClass) {
        Response response = performNetworking(url, body);
        if (response != null) {
            try {
                return gson.fromJson(response.body().string(), outClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String downloadFile(String url, String downloadDirectory, String filename, DownloadCallback callback) {
        String out = null;

        RespAndCall rac = performNetworkingForCall(url, null);
        if (rac == null) {
            if (callback != null) {
                callback.onError(503, "An error occurred, please try again later.");
            }

            return null;
        }
        Response resp = rac.response;

        if (callback != null) {
            callback.setCall(rac.call);
        }

        if (resp != null) {
            if (resp.code() == 200) {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    long contentLength = resp.body().contentLength();

                    File checkPath = new File(downloadDirectory);
                    if (!checkPath.exists()) {
                        boolean makeResult = checkPath.mkdir();
                        if (!makeResult && !checkPath.exists()) {
                            return null;
                        }
                    }

                    String localFilename = downloadDirectory + "/" + filename;
                    File downloadFile = new File(localFilename);
                    if (!downloadFile.exists()) {
                        boolean createResult = downloadFile.createNewFile();
                        if (createResult) {
                            downloadFile.setReadable(true, false);
                        } else {
                            return null;
                        }
                    }

                    is = resp.body().byteStream();
                    fos = new FileOutputStream(downloadFile);

                    byte[] buffer = new byte[1024];
                    int l;
                    long totalBytes = 0;
                    while ((l = is.read(buffer)) >= 0) {
                        fos.write(buffer, 0, l);
                        totalBytes += l;

                        if (callback != null) {
                            callback.onProgress(totalBytes, contentLength, url);
                        }
                    }

                    out = localFilename;
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onError(999, "Could not download at this time.");
                    }
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (callback != null) {
                    String errorMessage = "Unknown error";
                    if (resp.code() == 404) {
                        errorMessage = "File not found";
                    }

                    callback.onError(resp.code(), errorMessage);
                }
            }
        }

        return out;
    }

    @Nullable
    private Response performNetworking(@NonNull String url, RequestBody requestBody) {
        RespAndCall rc = performNetworkingForCall(url, requestBody);
        if (rc != null) {
            return rc.response;
        }
        return null;
    }

    @Nullable
    private RespAndCall performNetworkingForCall(@NonNull String url, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder().url(url);

        if (requestBody != null) {
            builder.post(requestBody);
        }

        // Send up authentication token if we have it
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        if (sp.contains(SharedPreferenceKeys.USER_TOKEN)) {
//            builder.addHeader("authToken", sp.getString(SharedPreferenceKeys.USER_TOKEN, ""));
//        }

        try {
            Request request = builder.build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            return new RespAndCall(response, call);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static abstract class DownloadCallback {
        public abstract void onProgress(long downloadedBytes, long contentLength, String uri);
        public abstract void onError(int errorCode, String errorMessage);
        public abstract void setCall(Call call);
    }

    public static abstract class UploadProgressListener {
        public abstract void onUpload(long uploadedBytes, long contentLength);
    }

    public class RespAndCall {
        public Response response;
        public Call call;

        public RespAndCall(Response response, Call call) {
            this.response = response;
            this.call = call;
        }
    }
}
