package com.sadashi.apps.firebase.auth.activities;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sadashi.apps.firebase.auth.R;

public class MainActivity extends AppCompatActivity {

    private static final String PASSWORD_LESS_SAMPLE = "Password Less Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        ListView listView = findViewById(R.id.sample_list);

        String[] samples = {PASSWORD_LESS_SAMPLE};
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, samples));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = (String) adapterView.getItemAtPosition(i);
                switch (text) {
                    case PASSWORD_LESS_SAMPLE:
                        startActivity(new Intent(MainActivity.this, PasswordLessActivity.class));
                        break;
                }
            }
        });
    }
}
