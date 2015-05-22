package maxichen.progressbartest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;


public class MainActivity extends Activity {

    private CheckBox chkIndeterminatep;
    private ProgressBar _CurrentProgressBar;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Reset ProgressBar Visibility
        resetProgressBarVisibility();
        chkIndeterminatep = (CheckBox)this.findViewById(R.id.chkindeterminate);
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

    public void btnGo_Click(View view) {
        mProgressStatus = 0;
        //Reset ProgressBar Visibility
        resetProgressBarVisibility();
        RadioGroup rg = (RadioGroup)this.findViewById(R.id.radioGroup1);
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);

        switch (radioButton.getId()) {
            case R.id.RPL:
                _CurrentProgressBar = (ProgressBar)this.findViewById(R.id.progressBarLarge);
                break;
            case R.id.RPN:
                _CurrentProgressBar = (ProgressBar)this.findViewById(R.id.progressBarNormal);
                break;
            case R.id.RPS:
                _CurrentProgressBar = (ProgressBar)this.findViewById(R.id.progressBarSmall);
                break;
            case R.id.RPH:
                _CurrentProgressBar = (ProgressBar)this.findViewById(R.id.progressBarHorizontal);
                break;
        }
        _CurrentProgressBar.setVisibility(View.VISIBLE);
        if (chkIndeterminatep.isChecked()) {
            _CurrentProgressBar.setIndeterminate(true);
        }
        else
        {
            _CurrentProgressBar.setIndeterminate(false);
        }
        // Start lengthy operation in a background thread
        Thread t1= new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    Log.d("test", String.valueOf(mProgressStatus));
                    mProgressStatus ++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            _CurrentProgressBar.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        });

        t1.start();
    }

    private void resetProgressBarVisibility()
    {
        this.findViewById(R.id.progressBarLarge).setVisibility(View.INVISIBLE);
        this.findViewById(R.id.progressBarNormal).setVisibility(View.INVISIBLE);
        this.findViewById(R.id.progressBarSmall).setVisibility(View.INVISIBLE);
        this.findViewById(R.id.progressBarHorizontal).setVisibility(View.INVISIBLE);
    }
}
