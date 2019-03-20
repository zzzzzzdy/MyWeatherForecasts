package activitytest.exmaple.com.myweatherforecast;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    List<Data> datas=new ArrayList<Data>();
    ReAdapter recyclerAdapter;
    RecyclerView recyclerView;
    TextView textView;

    String city;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        String getCity = intent.getStringExtra("city1");
        city = getCity;
        initData();
        textView = findViewById(R.id.ttv);
        textView.setText(city);
        recyclerView=findViewById(R.id.rev);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerAdapter=new ReAdapter(datas,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(manager);
    }

    private void initData() {
                HttpConnect httpConnect = HttpConnect.getInstance();
        try {
            httpConnect.sendRequestGet("https://www.apiopen.top/weatherApi?city=" + city, new HttpConnect.MyInterface() {
                @Override
                public void success(String result) {
                    parseJSON(result);

                }

                @Override
                public void onError(Exception e) {
                    parseJSON(e.toString());

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
//        httpConnect.setInterface(new HttpConnect.MyInterface() {
//            @Override
//            public void success(String result) {
//                parseJSON(result);
//
//            }
//        });


    }
    private void parseJSON( String response){
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("data"));
            JSONArray jsonArray=new JSONArray(jsonObjectOne.getString("forecast"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                Data data=new Data(jsonObject2.getString("date"),jsonObject2.getString("high"),
                        jsonObject2.getString("fengli"),jsonObject2.getString("low"),
                        jsonObject2.getString("fengxiang"),jsonObject2.getString("type"));


                datas.add(data);
            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    recyclerAdapter.notifyDataSetChanged();
//                }
//            });
            Runnable mRunnable = new Runnable() {

                @Override
                public void run() {
                    recyclerAdapter.notifyDataSetChanged();

                }
            };
            handler.post(mRunnable);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
