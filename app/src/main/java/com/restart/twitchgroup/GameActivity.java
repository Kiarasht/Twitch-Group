package com.restart.twitchgroup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = ".GameActivity";
    private ProgressDialog dialog;
    private String[] url = new String[40];
    private LinearLayout[] linearLayouts = new LinearLayout[11];
    private int loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        setupActionBar();
        loop = 0;
        dialog = ProgressDialog.show(GameActivity.this, "", "Loading. Please wait...", true);
        linearLayouts[0] = (LinearLayout) findViewById(R.id.linear);
        parsegames("https://api.twitch.tv/kraken/games/top", 0, false, "top", "game", "box", "large");
        linearLayouts[0] = (LinearLayout) findViewById(R.id.linear);
        parsegames("https://api.twitch.tv/kraken/games/top?limit=8&offset=10", 0, true, "top", "game", "box", "large");
        linearLayouts[1] = (LinearLayout) findViewById(R.id.linear2);
        parsegames("https://api.twitch.tv/kraken/streams/featured?limit=9", 1, false, "featured", "stream", "preview", "large");
        linearLayouts[2] = (LinearLayout) findViewById(R.id.linear3);
        parsegames("https://api.twitch.tv/kraken/videos/top?game=Gaming+Talk+Shows&period=month&limit=16", 2, false, "videos", "preview");
        linearLayouts[3] = (LinearLayout) findViewById(R.id.linear4);
        parsegames("https://api.twitch.tv/kraken/teams", 3, true, "teams", "logo");
    }

    private void parsegames(final String link, final int layoutarr, final Boolean second, final String ... params) {
        AsyncTask.execute(new Runnable() {
            public void run() {
                String strContent = "";
                try {
                    URL urlHandle = new URL(link);
                    URLConnection urlconnectionHandle = urlHandle.openConnection();
                    InputStream inputstreamHandle = urlconnectionHandle.getInputStream();

                    try {
                        int intRead;
                        byte[] byteBuffer = new byte[1024];

                        do {
                            intRead = inputstreamHandle.read(byteBuffer);

                            if (intRead == 0) {
                                break;

                            } else if (intRead == -1) {
                                break;
                            }

                            strContent += new String(byteBuffer, 0, intRead, "UTF-8");
                        } while (true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    inputstreamHandle.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (layoutarr != 2 && layoutarr != 3) {
                        JSONArray results = new JSONObject(strContent).getJSONArray(params[0]);
                        int length = results.length();
                        if (layoutarr == 1 || second) {
                            length = 8;
                            url[8] = "";
                            url[9] = "";
                        }
                        for (int i = 0; i < length; ++i) {
                            JSONObject result = results.getJSONObject(i);
                            JSONObject box = result.getJSONObject(params[1]).getJSONObject(params[2]);
                            url[i] = box.getString(params[3]);
                        }
                    } else {
                        JSONArray results = new JSONObject(strContent).getJSONArray(params[0]);

                        for (int i = 0; i < results.length(); ++i) {
                            JSONObject result = results.getJSONObject(i);
                            url[i] = result.getString(params[1]);
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int length = url.length;
                            if (layoutarr == 1 || second) {
                                length = 8;
                            }
                            for (int i = 0; i < length; i++) {
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setId(i);
                                imageView.setPadding(2, 2, 2, 2);
                                Picasso.with(getApplicationContext()).load(url[i]).into(imageView);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                linearLayouts[layoutarr].addView(imageView);
                            }
                            if (loop == 4) {
                                dialog.dismiss();
                            } else {
                                ++loop;
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
