package com.workingsafe.safetyapp.videoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.workingsafe.safetyapp.R;

import info.hoang8f.widget.FButton;

public class GameWon extends Activity {

    private FButton playAgainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_won);
        playAgainBtn = findViewById(R.id.platygaianbutton);
        //it will navigate from this activity to MainGameActivity
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameWon.this, MainGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
