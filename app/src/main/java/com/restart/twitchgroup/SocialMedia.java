package com.restart.twitchgroup;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class SocialMedia extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, First_Media.class);
        spec = tabHost.newTabSpec("Facebook").setIndicator("Facebook")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Second_Media.class);
        spec = tabHost.newTabSpec("Twitter").setIndicator("Twitter")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Third_Media.class);
        spec = tabHost.newTabSpec("YouTube").setIndicator("Youtube")
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, Fourth_Media.class);
        spec = tabHost.newTabSpec("Tumblr").setIndicator("Tumblr")
                .setContent(intent);
        tabHost.addTab(spec);
    }
}
