package com.tony.helen.flick;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalibrateActivity extends Activity implements GestureManager.GestureListener{
    private Float[][] calibrations;
    private int currentStage;
    private GestureManager manager;
    private TextView instruct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        instruct = (TextView) findViewById(R.id.instructions);
        manager = GestureManager.getInstance(this);
        manager.unlock();
        manager.setListener(this);
        currentStage = 0;
        calibrations = new Float[3][3];

    }

    @Override
    public void onNewGesture(int newGesture) {
        if (newGesture == 1) {
            Float[] temp = manager.getGyro();

            for (int i = 0; i < 3; i++) {
                calibrations[currentStage][i] = temp[i];
                Log.d("myo", Float.toString(temp[i]));
            }
            if (currentStage == 0) {
                instruct.setText("Please hold your hand to your chest and make a fist");
            } else if (currentStage == 1) {
                instruct.setText("Please lower your hand and make a fist");
            } else if (currentStage == 2) {
                manager.setGyroCalibrations(calibrations);
                manager.lock();
                finish();
            }
            currentStage++;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calibrate, menu);
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
