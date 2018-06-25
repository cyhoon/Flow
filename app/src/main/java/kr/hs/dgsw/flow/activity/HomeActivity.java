package kr.hs.dgsw.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.database.DatabaseHelper;
import kr.hs.dgsw.flow.helper.MyProfileSingleton;
import kr.hs.dgsw.flow.model.User;
import kr.hs.dgsw.flow.model.response.UserResponseData;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MyProfileSingleton myProfileSingleton = null;

    private TextView userEmailTxt;
    private TextView userNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        setViewId(headerView);
        checkLogin();
    }

    public void setViewId(View headerView) {
        userEmailTxt = headerView.findViewById(R.id.userEmailTxt);
        userNameTxt = headerView.findViewById(R.id.userNameTxt);
    }

    public void goSignInActivity() {
        Intent schoolMealsIntent = new Intent(HomeActivity.this, SignInActivity.class);
        startActivity(schoolMealsIntent);
        finish();
    }

    public void checkLogin() {
        // MY_PROFILE 테이블에 회원 데이터를 가지고 온다.
        // 회원 데이터가 있다면 로그인 된 것으로 확인한다.
        // 없다면 로그인 액티비티로 이동하게 한다.

        try {
            DatabaseHelper dbManager = new DatabaseHelper(getApplicationContext());
            UserResponseData userResponseData = dbManager.getMyProfile();
            if (userResponseData == null) { goSignInActivity(); }

            String token = userResponseData.getToken();
            User user = userResponseData.getUser();

            userEmailTxt.setText(user.getEmail());
            userNameTxt.setText(user.getName());

            myProfileSingleton = MyProfileSingleton.getInstance(token, user.getEmail(), user.getName());
        } catch (Exception e) {
            Log.d("error", e.toString());
            goSignInActivity();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.meal_menu ) {
            Intent schoolMealsIntent = new Intent(HomeActivity.this, MealActivity.class);
            startActivity(schoolMealsIntent);
        } else if (id == R.id.logout) {
            // 로그아웃

            /**
             * 로그아웃 로직
             *
             * 데이터 베이스 삭제 하면 될 듯
             */
            try {
                DatabaseHelper dbManager = new DatabaseHelper(getApplicationContext());
                dbManager.deleteMyProfile();

                Intent goLoginIntent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(goLoginIntent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
