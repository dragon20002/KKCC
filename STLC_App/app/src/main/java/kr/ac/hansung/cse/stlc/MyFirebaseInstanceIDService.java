package kr.ac.hansung.cse.stlc;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendRegistrationToServer(String token) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        Request request = new Request.Builder()
                .url(MainActivity.HOME_URL + "/RegisterKey")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.i("REQUEST_INFO", "url: " + request.url() +
                    "\nmethod: " + request.method() +
                    "\nbody: " + request.body().contentType() +
                    "\ntoken: " + token);
            Log.i("RESPONSE_INFO", "code: " + response.code() +
                    "\nmessage: " + response.message() +
                    "\n: body: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
