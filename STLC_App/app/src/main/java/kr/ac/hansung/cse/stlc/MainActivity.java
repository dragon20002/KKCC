package kr.ac.hansung.cse.stlc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static String HOME_URL = "http://13.125.115.128:8080/STLC"; //"http://192.168.0.200:8080/STLC";

    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 바인딩
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        webView = findViewById(R.id.webView);

        // 웹뷰 설정
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 웹뷰 클라이언트 동작 설정
        webView.setWebViewClient(new MyWebViewClient(progressBar));
        webView.setWebChromeClient(new MyWebChromeClient());

        // URL 로드
        webView.loadUrl(HOME_URL);
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("종료")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("네",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.super.onBackPressed();
                            }
                        })
                .setNegativeButton("아니오",
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .create().show();
    }
}
