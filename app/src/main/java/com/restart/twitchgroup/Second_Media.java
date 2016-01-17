package com.restart.twitchgroup;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Second_Media extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        TextView txtView = (TextView) findViewById(R.id.txtDisplayedTab);
        txtView.setText("First Tab is Selected");
    }
}
