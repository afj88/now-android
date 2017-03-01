package it.ret.yolo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import it.ret.yolo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartVideoCall = (Button) findViewById(R.id.btn_start_video_call);
        Button btnShowPDF = (Button) findViewById(R.id.btn_show_pdf);

        btnStartVideoCall.setOnClickListener(btn -> {
            Intent startVideoCallIntent = new Intent(this, VideoCallActivity.class);
            startActivity(startVideoCallIntent);
        });

        btnShowPDF.setOnClickListener(btn -> {
            Intent showPDFIntent = new Intent(this, PDFScrollActivity.class);
            startActivity(showPDFIntent);
        });
    }
}
