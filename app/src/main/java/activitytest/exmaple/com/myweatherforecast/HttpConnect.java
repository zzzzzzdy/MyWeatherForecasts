package activitytest.exmaple.com.myweatherforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class HttpConnect {
    //定义一个接口
    public interface MyInterface {


        //成功之后便开始解析得到的
        void success(String result);
    }



    private static MyInterface myInterface;
    public void setInterface(MyInterface myInterface){
        this.myInterface = myInterface;
    }
    private static volatile HttpConnect mHttpConnect;
    private HttpConnect(){}
    public static HttpConnect getInstance(){
        if(mHttpConnect == null){
            synchronized (OkHttp.class){
                mHttpConnect = new HttpConnect();
            }
        }
        return mHttpConnect;
    }
    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,5,1,TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));
    public void sendRequest(String path) throws IOException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                BufferedReader reader;
                InputStream inputStream;

                try {
                    URL url=new URL(path);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(7000);
                    connection.setReadTimeout(7000);
                    connection.setRequestProperty("Content-type", "application/json");
                    inputStream=connection.getInputStream();

                    reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    inputStream.close();
                    if (myInterface != null) {
                        myInterface.success(response.toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        };
        threadPoolExecutor.execute(runnable);
    }
}
