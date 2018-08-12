package net.raginggeek.helloandroidsafari;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class WelcomeActivity extends Activity {
    private TextView greetingText;
    private DatabaseAdapter adapter;
    private List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String name = getIntent().getStringExtra(MainActivity.MAIN_ACTIVITY_EXTRA);
        greetingText = findViewById(R.id.welcomeTextView);
        greetingText.setText(String.format(getString(R.string.greeting), name));

        adapter = new DatabaseAdapter(this);
        adapter.open();
        if (!adapter.exists(name)) {
            adapter.insertName(name);
        }

        names = adapter.getAllNames();
        ListView listView = findViewById(R.id.nameListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "Item at " + position + "clicked");
                greetingText.setText(String.format(getString(R.string.greeting), names.get(position)));
            }
        });




    }
}
