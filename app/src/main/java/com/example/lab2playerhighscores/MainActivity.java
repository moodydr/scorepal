package com.example.lab2playerhighscores;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView lst;
    EditText playerid;
    EditText playername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst = (TextView) findViewById(R.id.list);
        playerid = (EditText) findViewById(R.id.playerID);
        playername = (EditText) findViewById(R.id.playerName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(this, R.string.settings, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.wifi:
                Toast.makeText(this, R.string.wi_fi, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addPlayer (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int id = Integer.parseInt(playerid.getText().toString());
        String name = playername.getText().toString();
        Player player = new Player(id,name);
        dbHandler.addHandler(player);
        playerid.setText("");
        playername.setText("");
    }

    public void findPlayer (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Player player = dbHandler.findHandler(playername.getText().toString());
        if (playerid.getText().toString().equals("") || playername.getText().toString().equals("")) {
            playerid.setHint(R.string.not_empty);
            playername.setHint(R.string.not_empty);
        }
        if (player != null) {
            lst.setText(String.format("%s %s", String.valueOf(player.getID()), player.getPlayerName()));
        } else {
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadPlayers(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        lst.setText(dbHandler.loadHandler());
        playerid.setText("");
        playername.setText("");
    }

    public void deletePlayer(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);
        if (playerid.getText().toString().equals("")) {
            playerid.setHint(R.string.not_empty);
        }
        try {
            boolean result = dbHandler.deleteHandler(Integer.parseInt(
                    playerid.getText().toString()));
            if (result) {
                playerid.setHint(R.string.player_id);
                playername.setHint(R.string.player_name);
                playerid.setText("");
                playername.setText("");
                lst.setText(R.string.record_deleted);
            } else {
                Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            playerid.setHint(R.string.not_empty);
        }
    }

    public void updatePlayer(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);
        if (playerid.getText().toString().equals("") || playername.getText().toString().equals("")) {
            playerid.setHint(R.string.not_empty);
            playername.setHint(R.string.not_empty);
        } else {
            boolean result = dbHandler.updateHandler(
                    Integer.parseInt(playerid.getText().toString()), playername.getText().toString());
            if (result) {
                playerid.setHint(R.string.player_id);
                playername.setHint(R.string.player_name);
                playerid.setText("");
                playername.setText("");
                lst.setText(R.string.record_updated);
            } else {
                Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
