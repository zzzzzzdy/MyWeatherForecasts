package activitytest.exmaple.com.myweatherforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class HttpConnect {




    private static volatile HttpConnect mHttpConnect;

    private HttpConnect() {
    }

    public static HttpConnect getInstance() {
        if (mHttpConnect == null) {
            synchronized (HttpConnect.class) {
                mHttpConnect = new HttpConnect();
            }
        }
        return mHttpConnect;
    }

    final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));

    public void sendRequestGet(String path) throws IOException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader;
                InputStream inputStream;
                StringBuilder response;
                String line;
                String mResult = null;

                    try {
                        URL url = new URL(path);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(7000);
                        connection.setReadTimeout(7000);
                        connection.setRequestProperty("Content-type", "application/json");
                        inputStream = connection.getInputStream();

                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        response = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        mResult = response.toString();
                        inputStream.close();
                        if (myInterface != null) {
                            myInterface.success(mResult);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(connection!=null)connection.disconnect();
                        if(mResult == null) mResult = "soory";
                    }

                }


        };
        threadPoolExecutor.execute(runnable);
    }
    public void sendRequestPost(String path, Map<String, String> form){
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                HttpURLConnection connection= null;
                PrintWriter pw = null ;
                BufferedReader reader = null ;
                StringBuilder out = new StringBuilder();
                StringBuilder sb = new StringBuilder();
                String line = null ;
                String mResponse = null;
                for (String key : form.keySet()) {
                    if(out.length()!=0){
                        out.append("&");
                    }
                    out.append(key).append("=").append(form.get(key));
                }
                try {
                    connection = (HttpURLConnection) new URL(path).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setReadTimeout(20000);
                    connection.setConnectTimeout(20000);
                    connection.setUseCaches(false);
                    connection.connect();
                    pw = new PrintWriter(connection.getOutputStream());
                    pw.print(out.toString());
                    pw.flush();
                    reader  = new BufferedReader( new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    while ((line = reader.readLine()) != null ) {
                        sb.append(line);
                    }
                    mResponse = sb.toString();
                    if (myInterface != null) {
                        myInterface.success(mResponse);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        if(pw != null){
                            pw.close();
                        }
                        if(reader != null){
                            reader.close();
                        }
                        if(connection != null){
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };threadPoolExecutor.execute(runnable);


    }

    //定义一个接口
    public interface MyInterface {


        //成功之后便开始解析得到的
        void success(String result);
    }


    private static MyInterface myInterface;

    public void setInterface(MyInterface myInterface) {
        this.myInterface = myInterface;
    }
}
