package it.ret.yolo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import it.ret.yolo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartVideoCall = (Button) findViewById(R.id.btn_start_video_call);
        btnStartVideoCall.setOnClickListener(btn -> {
            Intent startVideoCallIntent = new Intent(this, VideoCallActivity.class);
            startActivity(startVideoCallIntent);
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = super.onCreateView(name, context, attrs);


        return view;
    }
}
