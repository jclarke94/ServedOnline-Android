package com.servedonline.servedonline_android;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.servedonline.servedonline_android.Database.Database;
import com.servedonline.servedonline_android.Database.DatabaseThread;
import com.servedonline.servedonline_android.Entity.User;
import com.servedonline.servedonline_android.Fragments.Home.HomeFragment;
import com.servedonline.servedonline_android.Fragments.Login.LoginFragment;
import com.servedonline.servedonline_android.Fragments.Login.LogoFragment;
import com.servedonline.servedonline_android.Network.ConnectionHelper;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    public static final String LOGIN_ID = "_loginId";
    public User currentUser;

    public SharedPreferences sp;

    private ImageView navBar;
    private FrameLayout flBlocker;
    private LinearLayout llTopbar;

    private boolean allowBackPress = true;

    private Database database;

    private ConnectionHelper connectionHelper;

    private ArrayList<OnBackPressedListener> onBackPressedListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        flBlocker = findViewById(R.id.flBlocker);
        llTopbar = findViewById(R.id.llTopbar);

        database = new Database(this);
        connectionHelper = new ConnectionHelper(this);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }
        }


        LogoFragment logoFragment = new LogoFragment();
        logoFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, logoFragment).commit();


    }

    @Override
    public void onBackPressed() {
        onBackPressed(false);
    }

    public void onBackPressed(boolean override) {
        if (allowBackPress || override) {
            super.onBackPressed();
        }

        ArrayList<Integer> removeIndexes = new ArrayList<>();
        for (int i = 0; i < onBackPressedListeners.size(); i++) {
            try {
                onBackPressedListeners.get(i).onBackPressed();
            } catch (Exception e) {
                removeIndexes.add(i);
                e.printStackTrace();
            }
        }

        for (int i = onBackPressedListeners.size() - 1; i >= 0; i--) {
            onBackPressedListeners.remove((int) removeIndexes.get(i));
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void showBlocker() {
        flBlocker.setVisibility(View.VISIBLE);
    }

    public void hideBlocker() {
        flBlocker.setVisibility(View.GONE);
    }

    public void clearFocus() {
        getCurrentFocus().clearFocus();
    }

    public void showTopbar() { llTopbar.setVisibility(View.VISIBLE); }

    public void hideTopbar() { llTopbar.setVisibility(View.GONE); }

    public void navigate(Fragment fragment, String backstackTag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if(backstackTag != null) {
            fragmentTransaction.addToBackStack(backstackTag);
        }

        fragmentTransaction.commit();
    }

    /**
     * Removes Fragments in the Stack until we reach the desired Backstack tag
     * @param backUntilTag          Backstack Tag to pop backstack until
     */
    public void voidBackstack(@Nullable String backUntilTag) {
        boolean hasBackstackEntry = false;
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            if (getSupportFragmentManager().getBackStackEntryAt(i).getName().equals(backUntilTag)) {
                hasBackstackEntry = true;
                break;
            }
        }

        if (!hasBackstackEntry) {
            backUntilTag = null;
        }
        getSupportFragmentManager().popBackStackImmediate(backUntilTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Convenience method for moving back through the app backstack hierarchy and immediately presenting a new screen afterwards
     *
     * Much safer than trying to do this in a Fragment instance itself as this all occurs within something that's not as volatile
     *
     * @param backUntilTag      Backstack tag to reverse transactions until
     * @param fragment          Fragment to navigate to after backstack void
     * @param backstackTag      Backstack tag to give to the new Fragment transaction
     */
    public void voidBackstackAndNavigate(@Nullable String backUntilTag, @NonNull Fragment fragment, @Nullable String backstackTag) {
        voidBackstack(backUntilTag);

        navigate(fragment, backstackTag);
    }

    public Database getDatabase() {
        return database;
    }

    public static abstract class OnBackPressedListener {
        public abstract void onBackPressed();
    }

    public ConnectionHelper getConnectionHelper() {
        return connectionHelper;
    }

    public void loginUser(int userId) {
        sp.edit().putInt(LOGIN_ID, userId).commit();
    }

    public void logoutUser() {
        sp.edit().putInt(LOGIN_ID, 0).commit();
    }
}
