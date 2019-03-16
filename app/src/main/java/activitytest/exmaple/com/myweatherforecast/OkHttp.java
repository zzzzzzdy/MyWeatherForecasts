package activitytest.exmaple.com.myweatherforecast;



import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp {
//定义一个接口
    public interface MyInterface {


        //成功之后便开始解析得到的
        void success(String result);
    }



    private static MyInterface myInterface;
    public void setInterface(MyInterface myInterface){
        this.myInterface = myInterface;
    }
    private static volatile OkHttp mOkHttp;
    private OkHttp(){}
    public static OkHttp getInstance(){
        if(mOkHttp == null){
            synchronized (OkHttp.class){
                mOkHttp = new OkHttp();
            }
        }
        return mOkHttp;
    }
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,5,1,TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));
    public void sendRequest(String path) throws IOException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(path).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String rData = null;
                try {
                    rData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(myInterface!=null)
                {
                    myInterface.success(rData);
                }

            }
        };
        threadPoolExecutor.execute(runnable);



            }





}
