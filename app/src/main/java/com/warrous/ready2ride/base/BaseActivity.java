package com.warrous.ready2ride.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Ready2RideApp;
import com.warrous.ready2ride.framework.AndroidUtil;
import butterknife.ButterKnife;



public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {


    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//
//            // This method will trigger on item Click of navigation menu
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                drawer.closeDrawers();
//                resetAllMenuItemsTextColor(navigationView);
//                setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                //Check to see which item was being clicked and perform appropriate action
//                switch (menuItem.getItemId()) {
//                    //Replacing the main content with ContentFragment Which is our Inbox View;
//
//                    case R.id.nav_dashboard:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                        break;
//
//                    case R.id.nav_consumers:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                        ActivityUtils.startActivity(BaseActivity.this, ConsumersActivity.class, null);
//                        break;
////                    case R.id.nav_warranty:
////                        ActivityUtils.startActivity(DashBoardActivity.this, WarrantyTipsActivity.class, null);
////                        break;
//                    case R.id.nav_emails:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
////                        tvHeader.setText("Emails");
////                        tvTopBarItem.setVisibility(View.VISIBLE);
////                        recyclerViewTopBar.setVisibility(View.VISIBLE);
////                        llMainHead.setVisibility(View.VISIBLE);
////                        ivGrid.setBackgroundResource(R.drawable.ic_filter);
////                        setEmails();
//
//
//                        // if (PermissionUtils.isUserLogedIn(getApplicationContext())) {
//                        // ActivityUtils.startActivity(DashBoardActivity.this, .class, null);
////                        } else {
////                            AlertUtil.showSignInDialog(DashBoardActivity.this);
////                        }
//                        break;
//                    case R.id.nav_help:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
////                        if (PermissionUtils.isUserLogedIn(getApplicationContext())) {
////                            if (DataManager.getInstance().getMyProducts(getContext()) != null && !DataManager.getInstance().getMyProducts(getContext()).isEmpty()) {
////                                ActivityUtils.startActivity(DashBoardActivity.this, ReviewListActivity.class, null);
////                            } else {
////                                AndroidUtil.showSnackBarSafe(DashBoardActivity.this, "No Products to add a Review");
////                            }
////                        } else {
////                            ActivityUtils.startActivity(DashBoardActivity.this, ReviewListActivity.class, null);
////
////                        }
////                        return true;
//                        break;
//                    case R.id.nav_library:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//
////                        tvHeader.setText("Library");
////                        tvTopBarItem.setVisibility(View.GONE);
////                        recyclerViewTopBar.setVisibility(View.GONE);
////                        llMainHead.setVisibility(View.GONE);
////                        if (showingFirst) {
////                            setGridView();
////                        } else {
////
////                            setListView();
////                        }
//
//                        ActivityUtils.startActivity(BaseActivity.this, LibraryActivity.class, null);
//                        return true;
//                    case R.id.nav_settings:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                      //  setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                        ActivityUtils.startActivity(BaseActivity.this, AccountPreferencesActivity.class, null);
//                        return true;
////                    case R.id.nav_about_us:
////                        ActivityUtils.startActivity(DashBoardActivity.this, AboutUsActivity.class, null);
////                        return true;
////                    case R.id.nav_settings:
////                        ActivityUtils.startActivity(DashBoardActivity.this, SettingsActivity.class, null);
////                        break;
//                    case R.id.nav_signout:
//                        setTextColorForMenuItem(menuItem, R.color.colorPrimary);
//                        PreferenceManager.clearAllPreferences(BaseActivity.this);
//                        ActivityUtils.startActivity(BaseActivity.this, LoginActivity.class, null);
//                        finishAffinity();
//
//                        // if (!PermissionUtils.isUserLogedIn(getApplicationContext())) {
////                            PreferenceManager.storeValue(DashBoardActivity.this, PreferenceManager.KEY_FIRST_LOGIN, true);
////                            ActivityUtils.startActivity(DashBoardActivity.this, GetStartedActivity.class, null);
////                            finishAffinity();
////                        } else {
////                            String fcmToken = PreferenceManager.getStringValue(DashBoardActivity.this, PreferenceManager.KEY_FCM_TOKEN);
////                            PreferenceManager.clearAllPreferences(DashBoardActivity.this);
////                            PreferenceManager.storeValue(getContext(), PreferenceManager.KEY_FIRST_LOGIN, true);
////                            PreferenceManager.storeValue(getContext(), PreferenceManager.KEY_FCM_TOKEN, fcmToken);
////                            ActivityUtils.startActivity(DashBoardActivity.this, SignInActivity.class, null);
////                            finishAffinity();
////                        }
//
//                }
//
//                return true;
//            }
//        });
//
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
//                super.onDrawerOpened(drawerView);
//            }
//        };
//
//        //Setting the actionbarToggle to drawer layout
//        drawer.setDrawerListener(actionBarDrawerToggle);
//
//        //calling sync state is necessary or else your hamburger icon wont show up
//        actionBarDrawerToggle.syncState();
    }
    @Override
    public Context getContext() {
        return this;
    }
    @Override
    public Ready2RideApp getReady2RideApp() {
        return (Ready2RideApp) getApplication();
    }
    @Override
    public Activity getAppActivity() {
        return this;
    }
    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, color)), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }
    private void resetAllMenuItemsTextColor(NavigationView navigationView) {
        for (int i = 0; i < navigationView.getMenu().size(); i++)
            setTextColorForMenuItem(navigationView.getMenu().getItem(i), R.color.colorLightGrey);
    }

    @Override
    public void showLoader() {
        AndroidUtil.hideKeyBoard(this);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.ProgressBarSmall);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);

        }
        progressDialog.show();
    }

    @Override
    public void dismissLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        AndroidUtil.showSnackBarSafe(this, message);
    }

//    @Override
//    public ContactPointApplication getAvailApp() {
//        return (ContactPointApplication) getApplication();
//    }

    protected void injectButterKnife(Activity activity) {
        ButterKnife.bind(activity);
    }


    protected void setToolBarBackClick() {
//        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AndroidUtil.hideKeyBoard(getAppActivity());
//                finish();
//            }
//        });
    }

    protected void setRobotoMedium(int resId){
        TextView textView =  findViewById(resId);
        setRobotoMedium(textView);
    }

    protected void setRobotoRegular(int resId){
        TextView textView =  findViewById(resId);
        setRobotoRegular(textView);
    }

    protected void setRobotoMedium(TextView textView){
      //  FontsUtil.getInstance(this).setMediumFont(textView);
    }

    protected void setRobotoRegular(TextView textView){
        //FontsUtil.getInstance(this).setRegularFont(textView);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        AndroidUtil.hideKeyBoard(this);
        super.onBackPressed();
    }

    public void setToolBar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }


}
