package net.raginggeek.helloandroidsafari;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        String name = getIntent().getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        TextView textView = findViewById(R.id.welcomeTextView);
        textView.setText(String.format("Hello, %s!", name));
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }
}
