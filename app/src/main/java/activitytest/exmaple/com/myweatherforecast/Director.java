package activitytest.exmaple.com.myweatherforecast;

import android.util.Log;

public class Director {
    private HttpC httpC;

    public Director(HttpC httpC) {
        this.httpC = httpC;
    }
    public void construct(){
        Log.d("hello","e...");

    }
}
