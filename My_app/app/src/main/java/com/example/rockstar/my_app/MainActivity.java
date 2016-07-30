package com.example.rockstar.my_app;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Long2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {

//    public class Timer{
//    Timer t;
//    volatile boolean a = false;
//
//        public Timer(_)
//        {
//            t= new Timer();
//        }
}
    private TextView songTimer;
    private int counter = 0;
    private ProgressBar progressBar;
    private ImageButton playButton;
    private MediaPlayer mediaPlayer;
    private Handler handler= new Handler();
    private long songStartTime;
    private ImageView imageView2;
    private ImageView nowplaying;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHandler();
        initView();
        initListeners();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void initHandler()
    {
        handler.postDelayed(updateUI,1000);
    }
    private Runnable updateUI = new Runnable() {
        @Override
        public void run() {
            double seekPercentage = 100 * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
            progressBar.setProgress((int) seekPercentage);
            handler.postDelayed(this,1000);
            long seconds = (System.currentTimeMillis() -songStartTime) / 1000;
        songTimer.setText(String.format("%02d:%02d", seconds/60, seconds % 60));
        }
    };

    private void initListeners() {

        playButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mediaPlayer.isPlaying())
                        {

                            handler.removeCallbacks(updateUI);
                           // Log.i("Hey");
                            mediaPlayer.pause();


                            //playButton.("Play");

                        }
                        else {
                            initHandler();
                            mediaPlayer.start();
                          //  playButton.setText("Stop");
                            songStartTime = System.currentTimeMillis();


                        }

                    }
                }
        );


    }

    private void initView() {

        songTimer = (TextView) findViewById(R.id.songTimer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        playButton = (ImageButton) findViewById(R.id.playButton);
        mediaPlayer = MediaPlayer.create(this,R.raw.newsong);
        songTimer.setText(String.format("%02d:%02d",0,0));
        imageView2 = (ImageView) findViewById(R.id.imageView2) ;
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.low));
        nowplaying = (ImageView) findViewById(R.id.nowplaying);
        nowplaying.setImageDrawable(getResources().getDrawable(R.drawable.nowplaying));

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
