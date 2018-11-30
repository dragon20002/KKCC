package kr.ac.hansung.cse.stlc;

import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("경고")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm())
                .setCancelable(false)
                .create()
                .show();
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("알림")
                .setMessage(message)
                .setPositiveButton("네", (dialog, which) -> result.confirm())
                .setNegativeButton("아니오", (dialog, which) -> result.cancel())
                .setCancelable(false)
                .create()
                .show();

        return super.onJsConfirm(view, url, message, result);
    }
}
