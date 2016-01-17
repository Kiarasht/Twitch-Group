package com.restart.twitchgroup;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = ".MainActivity";
    private ProgressDialog dialog;
    private Timer timer;
    private WebView webView;
    private Context context;
    private Boolean eightmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);

        eightmin = false;

        context = getApplicationContext();

        webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewController() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Web Url = " + webView.getUrl());
                if (!webView.getUrl().contains("access_token")) {
                    webView.setVisibility(View.VISIBLE);
                } else {
                    webView.loadUrl("https://twitter.com/Twitch?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor");
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String names[] = {"30 seconds", "60 seconds", "90 seconds", "120 seconds",
                            "150 seconds", "180 seconds"};
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    final AlertDialog alert = alertDialog.create();
                    alert.setIcon(R.drawable.ic_action_line_chart);

                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = inflater.inflate(R.layout.list, null);
                    alert.setView(convertView);
                    alert.setTitle("Advertising Length");
                    ListView listView = (ListView) convertView.findViewById(R.id.listView1);
                    listView.setPadding(30, 0, 30, 0);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            String toast = "Running advertisements for ";
                            switch (position) {
                                case 0:
                                    toast += "30";
                                    break;
                                case 1:
                                    toast += "60";
                                    break;
                                case 2:
                                    toast += "90";
                                    break;
                                case 3:
                                    toast += "120";
                                    break;
                                case 4:
                                    toast += "150";
                                    break;
                                case 5:
                                    toast += "180";
                                    break;
                            }
                            final String Final = toast + " seconds.";
                            if (!eightmin) {
                                Toast.makeText(context, Final, Toast.LENGTH_SHORT).show();
                                timer = new Timer();
                                eightmin = true;
                                timer.scheduleAtFixedRate(new TimerTask() {
                                    @Override
                                    public void run() {
                                        eightmin = false;
                                        timer.cancel();
                                        timer.purge();
                                        timer = null;
                                    }
                                }, 480000, 999999999999999999L);
                            } else {
                                Toast.makeText(context, "Must wait 8 minutes before another request!"
                                        , Toast.LENGTH_SHORT).show();
                            }
                            alert.dismiss();
                        }
                    });
                    alert.show();
                }
            });
        } else if (id == R.id.nav_viewers) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.help) {
            Intent intent = new Intent(context, Help.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
