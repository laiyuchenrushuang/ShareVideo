package videotool.hc.com.videotool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, AdapterView.OnItemClickListener {
    @BindView(R.id.video)
    SurfaceView mSurfaceView;
    //    VideoView video;
    @BindView(R.id.image1)
    ImageView imageView1;
    @BindView(R.id.horizontal_lv)
    HorizontalListView horizontalListView;
    @BindView(R.id.button_play)
    Button bt_play;
    @BindView(R.id.button_pause)
    Button bt_pause;
    MediaPlayer mediaPlayer;
    private HorizontalListViewAdapter mHorizontalListViewAdapter;
    private static final int REQ_PER = 10;
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int currentPosition;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, mPermissions, REQ_PER);
        }
        initView();
        initEvent();
//        String path = "/storage/emulated/0/DCIM/test.mp4";
//        video.setVideoPath(path);
//        MediaController mediaController = new MediaController(this);
//        video.setMediaController(mediaController);
//        video.requestFocus();
    }

    private void initView() {
        final int[] ids = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        mHorizontalListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(), ids);
        horizontalListView.setAdapter(mHorizontalListViewAdapter);

        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(callback);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mediaPlayer = new MediaPlayer();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i("lylog", "SurfaceHolder 创建");
            playVideo();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("lylog", "SurfaceHolder 变化时");
            mediaPlayer.setDisplay(surfaceHolder);
            if (currentPosition > 0) {
                // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
                play(currentPosition);
            }
        }

        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i("lylog", "SurfaceHolder 被销毁");
            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }
    };

    private void play(int currentPosition) {
        Log.d("lylog", "currentPosition =  " + currentPosition);
        mediaPlayer.seekTo(currentPosition);
        mediaPlayer.start();
    }

    private void playVideo() {
        // 重置mediaPaly,建议在初始滑mediaplay立即调用。
        mediaPlayer.reset();
        // 设置声音效果
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(this);

        // 设置媒体加载完成以后回调函数。
        mediaPlayer.setOnPreparedListener(this);
        // 错误监听回调函数
        mediaPlayer.setOnErrorListener(this);
        // 设置缓存变化监听
        mediaPlayer.setOnBufferingUpdateListener(this);

        String path = "/storage/emulated/0/DCIM/test.mp4";
        try {
            // mediaPlayer.reset();
            //mediaPlayer.setDataSource(media.this, uri);
            mediaPlayer.setDataSource(path);
            // 设置异步加载视频，包括两种方式 prepare()同步，prepareAsync()异步
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEvent() {
        imageView1.setOnClickListener(this);
        bt_play.setOnClickListener(this);
        bt_pause.setOnClickListener(this);
        mSurfaceView.setOnClickListener(this);
        horizontalListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image1:
                startActivity(new Intent().setClass(getApplicationContext(), ToolActivity.class));
                break;
            case R.id.button_play:
                playButtonClick();
                break;
            case R.id.video:
                if (mediaPlayer.isPlaying()) {
                    bt_pause.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bt_pause.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.button_pause:
                bt_pause.setVisibility(View.INVISIBLE);
                bt_play.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                break;
            case R.id.horizontal_lv:
                break;
        }
    }

    private void playButtonClick() {
        mediaPlayer.start();
        bt_play.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        bt_play.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        bt_play.setVisibility(View.VISIBLE);
        play(currentPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("lylog", " position = " + position);
    }
}
