package com.example.hungvu.testpack;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton actionButton;
    private View anchor;

    // 1. float layout in CoordinatorLayout or FrameLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anchor = (View) findViewById(R.id.anchor);

        actionButton  = (FloatingActionButton) findViewById(R.id.float_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(actionButton, "rotation", 0, -90).setDuration(500).start();

                ObjectAnimator moveX = ObjectAnimator.ofFloat(actionButton, "x", 240).setDuration(1000);
                moveX.setStartDelay(500);
                moveX.start();

                ObjectAnimator moveY = ObjectAnimator.ofFloat(actionButton, "y", 120).setDuration(1000);
                moveY.setStartDelay(500);
                moveY.start();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
