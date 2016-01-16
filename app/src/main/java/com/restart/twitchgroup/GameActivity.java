package com.restart.twitchgroup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GameActivity extends AppCompatPreferenceActivity {
    private static final String TAG = ".GameActivity";
    private ProgressDialog dialog;
    private String[] itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        setupActionBar();

        itemname = new String[]{"", "", "", "", "", "", "", "", "", ""};
        dialog = ProgressDialog.show(GameActivity.this, "", "Loading. Please wait...", true);
        parsegames();

        this.setListAdapter(new ArrayAdapter<>(
                this, R.layout.list_layout,
                R.id.Itemname, itemname));
    }

    private void parsegames() {
        AsyncTask.execute(new Runnable() {
            public void run() {
                String strContent = "";

                try {
                    URL urlHandle = new URL("https://api.twitch.tv/kraken/games/top");
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
                    JSONArray results = new JSONObject(strContent).getJSONArray("top");

                    for (int i = 0; i < results.length(); ++i) {
                        JSONObject result = results.getJSONObject(i);
                        JSONObject gname = result.getJSONObject("game");
                        itemname[i] = gname.getString("name");
                        //Picasso.with(getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(imageView);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }

                    });
                } catch (
                        JSONException e
                        )

                {
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
