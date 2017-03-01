package it.ret.yolo.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.tuyenmonkey.mkloader.MKLoader;

import it.ret.yolo.R;

public class PDFScrollActivity extends AppCompatActivity implements OnPageScrollListener, OnLoadCompleteListener {

    public static final int PERMISSION_CODE = 42042;
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String SAMPLE_FILE = "sample.pdf";
    private final static int REQUEST_CODE = 42;
    String pdfFileName;
    private Button button;
    private PDFView pdfView;
    private MKLoader mkLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfscroll);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        button = (Button) findViewById(R.id.btn_pdfView_ok);
        mkLoader = (MKLoader) findViewById(R.id.mk_loader);

        Uri uri = Uri.parse("http://bayes.wustl.edu/etj/articles/random.pdf");
    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {
        if (positionOffset == 1F) {
            button.setEnabled(true);
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        pdfView.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) pdfView.getLayoutParams();
        layoutParams.weight = 100F;
        mkLoader.setVisibility(View.GONE);
    }

    void afterViews() {

        displayFromAsset(SAMPLE_FILE);
        setTitle(pdfFileName);
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .scrollHandle(new DefaultScrollHandle(this))
                .onPageScroll(this)
                .onLoad(this)
                .load();
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, android.R.string.no, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }
}
