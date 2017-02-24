package it.ret.yolo.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.joda.time.DateTime;

import it.ret.yolo.R;
import it.ret.yolo.ui.fragments.VideoCallFragment;

/**
 * Created by Alessandro Frigerio on 27/01/2017.
 */
public class VideoCallActivity extends AppCompatActivity {

    private VideoCallFragment videoCallFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        videoCallFragment = VideoCallFragment.newInstance("John", "Doe", new DateTime(1950, 1, 1, 0, 0, 0), "ABCDEF50A01Z206A", "inventia.factory@inventia.biz", "https://www.google.com");

        getFragmentManager().beginTransaction()
                .replace(R.id.layout_video_call, videoCallFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {

        getFragmentManager().beginTransaction()
                .remove(videoCallFragment)
                .commit();

        videoCallFragment = null;

        super.onDestroy();
    }
}
