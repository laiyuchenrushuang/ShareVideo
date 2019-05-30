package videotool.hc.com.videotool;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ly on 2019/5/30.
 */

public class HttpService {
public void getData(){
    String url = "http://wwww.baidu.com";
    OkHttpClient okHttpClient = new OkHttpClient();
    final Request request = new Request.Builder()
            .url(url)
            .get()//默认就是GET请求，可以不写
            .build();


    okHttpClient.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    });//得到Response 对象

}

}
