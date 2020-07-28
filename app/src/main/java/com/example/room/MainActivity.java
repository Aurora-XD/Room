package com.example.room;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_main_arch)
    void goArchActivity() {
        Intent intent = new Intent(this, ArchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_main_submit)
    void goSubmitActivity() {
        Intent intent = new Intent(this, SubmitActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_main_view)
    void goViewActivity() {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_main_network)
    void goNetworkActivity(){
        Intent intent = new Intent(this, NetworkActivity.class);
        startActivity(intent);
    }
}