package com.ku4irka.giphyapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.ku4irka.giphyapp.R;
import com.ku4irka.giphyapp.app.AppApplication;
import com.ku4irka.giphyapp.util.OnToolbarChangeListener;
import com.ku4irka.giphyapp.view.fragments.DetailsFragment;
import com.ku4irka.giphyapp.view.fragments.ListFragment;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static com.ku4irka.giphyapp.app.Const.DETAILS_SCREEN;
import static com.ku4irka.giphyapp.app.Const.LIST_SCREEN;

public abstract class BaseActivity extends AppCompatActivity implements OnToolbarChangeListener {

    protected ActionBar mActionBar;
    protected Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBar = getSupportActionBar();
        initNavigator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppApplication.getInstance().getNavigatorHolder().setNavigator(mNavigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppApplication.getInstance().getNavigatorHolder().removeNavigator();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onTitleToolbarChanged(@Nullable String title) {
        if (title != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public void onChangeDisplayHomeAsUpEnabled(boolean state) {
        mActionBar.setDisplayHomeAsUpEnabled(state);
    }

    protected void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initNavigator() {
        mNavigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.container) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case LIST_SCREEN:
                        return ListFragment.getInstance();
                    case DETAILS_SCREEN:
                        return DetailsFragment.getInstance(data);
                    default:
                        throw new RuntimeException("Unknown screen key!");
                }
            }

            @Override
            protected void showSystemMessage(String message) {
                showToastMessage(message);
            }

            @Override
            protected void exit() {
                finish();
            }
        };
    }
}
