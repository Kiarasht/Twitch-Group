package com.restart.twitchgroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = ".MainActivity";
    private ProgressDialog dialog;
    private WebView webView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

        context = getApplicationContext();

        webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewController() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Web Url = " + webView.getUrl());
                if (!webView.getUrl().contains("access_token")) {
                    webView.setVisibility(View.VISIBLE);
                } else {
                    webView.setVisibility(View.GONE);
                    ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                    ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
                    imageView2.setBackgroundColor(Color.RED);
                    imageView3.setBackgroundColor(Color.BLACK);
                }
                dialog.dismiss();
            }
        });

        webView.loadUrl("https://api.twitch.tv/kraken/oauth2/authorize?response_type=token&" +
                "client_id=fggf2ntfyarqxw7sl16vwrq36rzcs26&" +
                "redirect_uri=https://www.twitch.tv/&" +
                "scope=user_read+user_blocks_edit+user_blocks_read+user_follows_edit+channel_read+" +
                "channel_editor+channel_commercial+channel_stream+channel_subscriptions+" +
                "user_subscriptions+channel_check_subscription+chat_login");
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_games) {
            Intent intent = new Intent(context, GameActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_user) {
            Intent intent = new Intent(context, SwipeDeck.class);
            startActivity(intent);
        } else if (id == R.id.nav_stream) {

        } else if (id == R.id.nav_commercial) {

        } else if (id == R.id.nav_viewers) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
