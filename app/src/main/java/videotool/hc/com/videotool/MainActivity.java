package videotool.hc.com.videotool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.video)
SurfaceView video;
@BindView(R.id.image1)
    ImageView imageView1;

    private static final int REQ_PER = 10;
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, mPermissions, REQ_PER);
        }

        initEvent();
//        String path = "/storage/emulated/0/DCIM/test.mp4";
//        video.setVideoPath(path);
//        MediaController mediaController = new MediaController(this);
//        video.setMediaController(mediaController);
//        video.requestFocus();
    }

    private void initEvent() {
        imageView1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image1:
                startActivity(new Intent().setClass(getApplicationContext(),ToolActivity.class));
                break;
        }
    }
}
