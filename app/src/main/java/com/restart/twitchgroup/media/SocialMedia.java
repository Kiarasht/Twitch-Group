package com.restart.twitchgroup.media;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.restart.twitchgroup.R;

public class SocialMedia extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, First_Media.class);
        spec = tabHost.newTabSpec("Facebook")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_action_facebook))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Second_Media.class);
        spec = tabHost.newTabSpec("Twitter")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_action_twitter))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Third_Media.class);
        spec = tabHost.newTabSpec("YouTube")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_action_youtube))
                        .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Fourth_Media.class);
        spec = tabHost.newTabSpec("Tumblr")
                .setIndicator("", getResources().getDrawable(R.drawable.ic_action_tumblr))
                .setContent(intent);
        tabHost.addTab(spec);
    }
}
