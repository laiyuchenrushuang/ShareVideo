package videotool.hc.com.videotool;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ly on 2019/5/27.
 */

public class ToolActivity extends AppCompatActivity  {
 @BindView(R.id.webview)
    WebView webView;
    Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_show);
        ButterKnife.bind(this);
        initwebview();
    }

    private void initwebview() {
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        finish();
    }
}
