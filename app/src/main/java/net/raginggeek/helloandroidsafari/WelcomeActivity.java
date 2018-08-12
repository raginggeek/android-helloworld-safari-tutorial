package net.raginggeek.helloandroidsafari;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeActivity extends Activity implements NameFragment.Rateable {
    private TextView greetingText;
    private DatabaseAdapter adapter;
    private List<String> names;
    private Map<String, Integer> ratings = new HashMap<>();

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
                String name = parent.getItemAtPosition(position).toString();
                DialogFragment fragment = new NameFragment();
                greetingText.setText(
                        String.format(getString(R.string.greeting), name));
                Bundle arguments = new Bundle();
                arguments.putString("name", name);
                fragment.setArguments(arguments);
                fragment.show(getFragmentManager(), "nothing");
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        greetingText.setText(savedInstanceState.getString("display"));
        String[] names = savedInstanceState.getStringArray("names");
        for (String name : names) {
            ratings.put(name, savedInstanceState.getInt(name));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("display", greetingText.getText().toString());
        String[] names = ratings.keySet().toArray(new String[ratings.keySet().size()]);
        outState.putStringArray("names", names);
        for (String name : names) {
            outState.putInt(name, ratings.get(name));
        }
    }

    @Override
    public void modifyRating(String name, int amount) {
        if (ratings.get(name) != null) {
            ratings.put(name, ratings.get(name) + amount);
        } else {
            ratings.put(name, amount);
        }
        Toast.makeText(this,
                String.format("%s has rating %d", name, ratings.get(name)),
                Toast.LENGTH_SHORT).show();
    }
}
