package activitytest.exmaple.com.myweatherforecast;

import java.io.IOException;

public abstract class HttpC {
    public interface MyInterface{}
    abstract public void sendRequest(String path) throws IOException;
    public void setInterface(MyInterface myInterface){}
}
