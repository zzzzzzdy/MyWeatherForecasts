package activitytest.exmaple.com.myweatherforecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editText = null;
    private Button button = null;
    private String ediStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.et);
        button = findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ediStr = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("city1",ediStr);
                startActivity(intent);
            }
        });
//        OkHttp okHttp = OkHttp.getInstance();
//        try {
//            okHttp.sendRequest("https://www.apiopen.top/weatherApi?city=重庆");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        okHttp.setInterface(new OkHttp.MyInterface() {
//            @Override
//            public void success(String result) {
//                Log.d("aaaaaaa",result);
//
//            }
//        });

    }
}
