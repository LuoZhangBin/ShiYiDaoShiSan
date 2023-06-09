package com.example.shiyidaoshisan;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shiyidaoshisan.DB.DB;
import com.example.shiyidaoshisan.fanfa.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SHZS17 extends AppCompatActivity implements View.OnClickListener {

    private TextView mSHZS17Wz1;
    private TextView mSHZS17Tishi1;
    private TextView mSHZS17Wz2;
    private TextView mSHZS17Tishi2;
    private TextView mSHZS17Wz3;
    private TextView mSHZS17Tishi3;
    private TextView mSHZS17Wz4;
    private TextView mSHZS17Tishi4;
    private TextView mSHZS17Wz5;
    private TextView mSHZS17Tishi5;
    private int temperaturey;
    private int humidityy;
    private int illuminationy;
    private int co2y;
    private int pm25y;
    private LinearLayout mXianshikuan;
    private LinearLayout mXianshikuan2;
    private LinearLayout mXianshikuan3;
    private LinearLayout mXianshikuan4;
    private LinearLayout mXianshikuan5;
    private int temperature;
    private int illumination;
    private int co2;
    private int pm25;
    private int sss=0;
    private int aaa=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shzs17);
        DB mas=new DB(this);
        boolean s=mas.isExist("huanjing");
        if (s==false){
            String sq1="create table huanjing (\n" +
                    "id integer primary key autoincrement,\n" +
                    "                   jingbao varchar,\n" +
                    "                   yvzhi varchar,\n" +
                    "                   dangqianzhi varchar)";
            mas.createTable(sq1);
        }
        mas.deleteDB("huanjing",null,null);
        initView();
        yvzhi();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tianqishuzhi();
            }
        }, 0, 3000);

    }

    private void yvzhi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_threshold", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    JSONArray jsonArray=jsonObject1.optJSONArray("ROWS_DETAIL");
                    JSONObject jsonObject2=new JSONObject(jsonArray.get(0).toString());
                    temperaturey = jsonObject2.getInt("temperature");
                    Log.v("7777777",temperaturey+"");
                    illuminationy = jsonObject2.getInt("illumination");
                    co2y = jsonObject2.getInt("co2");
                    pm25y = jsonObject2.getInt("pm25");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void tianqishuzhi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserName", "user1");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        new Http().sendResUtil("get_all_sense", jsonObject.toString(), "POST", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject1 = new JSONObject(s);
                    Log.v("55555555", String.valueOf(jsonObject1.getInt("illumination")));
                    temperature = jsonObject1.getInt("temperature");
                    int humidity = jsonObject1.getInt("humidity");
                    illumination = jsonObject1.getInt("illumination");
                    co2 = jsonObject1.getInt("co2");
                    pm25 = jsonObject1.getInt("pm25");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DB mas=new DB(SHZS17.this);
                            ContentValues cv=new ContentValues();
                            if (illumination <= 1000 && illumination > 0) {
                                mSHZS17Wz1.setText("弱(" + illumination + ")");
                                mSHZS17Tishi1.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
                            } else if (illumination > 1000 && illumination <= 3000) {
                                mSHZS17Wz1.setText("中等(" + illumination + ")");
                                mSHZS17Tishi1.setText("涂擦SPF大于15、PA+防晒护肤品");
                            } else {
                                mSHZS17Wz1.setText("强(" + illumination + ")");
                                mSHZS17Tishi1.setText("尽量减少外出，需要涂抹高倍数防晒霜");
                            }

                            if (temperature <= 8) {
                                mSHZS17Wz2.setText("较易发(" + temperature + ")");
                                mSHZS17Tishi2.setText("温度低，风较大，较易发生感冒，注意防护");
                            } else {
                                mSHZS17Wz2.setText("少发(" + temperature + ")");
                                mSHZS17Tishi2.setText("无明显降温，感冒机率较低");
                            }

                            if (temperature <= 12) {
                                mSHZS17Wz3.setText("冷(" + temperature + ")");
                                mSHZS17Tishi3.setText("建议穿长袖衬衫、单裤等服装");
                            } else if (temperature > 12 && temperature <= 21) {
                                mSHZS17Wz3.setText("舒适(" + temperature + ")");
                                mSHZS17Tishi3.setText("建议穿短袖衬衫、单裤等服装");
                            } else {
                                mSHZS17Wz3.setText("热(" + temperature + ")");
                                mSHZS17Tishi3.setText("适合穿T恤、短薄外套等夏季服装");
                            }

                            if (co2 <= 3000) {
                                mSHZS17Wz4.setText("适宜(" + co2 + ")");
                                mSHZS17Tishi4.setText("气候适宜，推荐您进行户外运动");
                            } else if (co2 > 3000 && co2 <= 6000) {
                                mSHZS17Wz4.setText("中(" + co2 + ")");
                                mSHZS17Tishi4.setText("易感人群应适当减少室外活动");
                            } else {
                                mSHZS17Wz4.setText("较不宜(" + co2 + ")");
                                mSHZS17Tishi4.setText("空气氧气含量低，请在室内进行休闲运动");
                            }

                            if (pm25 <= 30) {
                                mSHZS17Wz5.setText("优(" + pm25 + ")");
                                mSHZS17Tishi5.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
                            } else if (pm25 > 30 && pm25 <= 100) {
                                mSHZS17Wz5.setText("良(" + pm25 + ")");
                                mSHZS17Tishi5.setText("易感人群应适当减少室外活动");
                            } else {
                                mSHZS17Wz5.setText("污染(" + pm25 + ")");
                                mSHZS17Tishi5.setText("空气质量差，不适合户外活动");
                            }
                            if (illumination > illuminationy) {
                                mXianshikuan.setBackgroundResource(R.drawable.beij7);
                                sss++;
                                if (sss>=20){
                                    aaa++;
                                    sss=1;
                                }
                                if (aaa>0){
                                    mas.updateDB("huanjing",cv,"id=?", String.valueOf(sss));
                                    Log.v("88888888",sss+"");
                                }else {
                                    cv.put("jingbao","[光照]报警");
                                    cv.put("yvzhi",illuminationy+"");
                                    cv.put("dangqianzhi",illumination+"");
                                    mas.insertDB("huanjing",cv);
                                }



                            }else {
                                mXianshikuan.setBackgroundResource(R.drawable.beij8);
                            }
                            if (temperature > temperaturey) {
                                mXianshikuan2.setBackgroundResource(R.drawable.beij7);
                                sss++;
                                if (sss>21){
                                    aaa++;
                                    sss=1;
                                }
                                if (aaa>0){
                                    mas.updateDB("huanjing",cv,"id=?", String.valueOf(sss));
                                    Log.v("88888888",sss+"");
                                }else {

                                    cv.put("jingbao","[温度]报警");
                                    cv.put("yvzhi",temperaturey+"");
                                    cv.put("dangqianzhi",temperature+"");
                                    mas.insertDB("huanjing",cv);
                                }

                            }else {
                                mXianshikuan2.setBackgroundResource(R.drawable.beij8);
                            }
                            if (temperature > temperaturey) {
                                mXianshikuan3.setBackgroundResource(R.drawable.beij7);
                                sss++;
                                if (sss>21){
                                    aaa++;
                                    sss=1;
                                }
                                if (aaa>0){
                                    mas.updateDB("huanjing",cv,"id=?", String.valueOf(sss));
                                    Log.v("88888888",sss+"");
                                }else {
                                    cv.put("jingbao","[温度]报警");
                                    cv.put("yvzhi",temperaturey+"");
                                    cv.put("dangqianzhi",temperature+"");
                                    mas.insertDB("huanjing",cv);
                                }

                            }else {
                                mXianshikuan3.setBackgroundResource(R.drawable.beij8);
                            }
                            if (co2 > co2y) {
                                mXianshikuan4.setBackgroundResource(R.drawable.beij7);
                                sss++;
                                if (sss>21){
                                    aaa++;
                                    sss=1;
                                }
                                if (aaa>0){
                                    mas.updateDB("huanjing",cv,"id=?", String.valueOf(sss));
                                    Log.v("88888888",sss+"");
                                }else {
                                    cv.put("jingbao","[CO.2]报警");
                                    cv.put("yvzhi",co2y+"");
                                    cv.put("dangqianzhi",co2+"");
                                    mas.insertDB("huanjing",cv);
                                }

                            }else {
                                mXianshikuan4.setBackgroundResource(R.drawable.beij8);
                            }
                            if (pm25 > pm25y) {
                                mXianshikuan5.setBackgroundResource(R.drawable.beij7);
                                sss++;
                                if (sss>21){
                                    aaa++;
                                    sss=1;
                                }
                                if (aaa>0){
                                    mas.updateDB("huanjing",cv,"id=?", String.valueOf(sss));
                                    Log.v("88888888",sss+"");
                                }else {
                                    cv.put("jingbao","[PM2.5]报警");
                                    cv.put("yvzhi",pm25y+"");
                                    cv.put("dangqianzhi",pm25+"");
                                    mas.insertDB("huanjing",cv);
                                }

                            }else {
                                mXianshikuan5.setBackgroundResource(R.drawable.beij8);
                            }
                        }
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void initView() {
        mSHZS17Wz1 = (TextView) findViewById(R.id.SHZS17_wz1);
        mSHZS17Tishi1 = (TextView) findViewById(R.id.SHZS17_tishi1);
        mSHZS17Wz2 = (TextView) findViewById(R.id.SHZS17_wz2);
        mSHZS17Tishi2 = (TextView) findViewById(R.id.SHZS17_tishi2);
        mSHZS17Wz3 = (TextView) findViewById(R.id.SHZS17_wz3);
        mSHZS17Tishi3 = (TextView) findViewById(R.id.SHZS17_tishi3);
        mSHZS17Wz4 = (TextView) findViewById(R.id.SHZS17_wz4);
        mSHZS17Tishi4 = (TextView) findViewById(R.id.SHZS17_tishi4);
        mSHZS17Wz5 = (TextView) findViewById(R.id.SHZS17_wz5);
        mSHZS17Tishi5 = (TextView) findViewById(R.id.SHZS17_tishi5);
        mXianshikuan = (LinearLayout) findViewById(R.id.xianshikuan);
        mXianshikuan2 = (LinearLayout) findViewById(R.id.xianshikuan2);
        mXianshikuan3 = (LinearLayout) findViewById(R.id.xianshikuan3);
        mXianshikuan4 = (LinearLayout) findViewById(R.id.xianshikuan4);
        mXianshikuan5 = (LinearLayout) findViewById(R.id.xianshikuan5);
        mXianshikuan.setOnClickListener(this);
        mXianshikuan2.setOnClickListener(this);
        mXianshikuan3.setOnClickListener(this);
        mXianshikuan4.setOnClickListener(this);
        mXianshikuan5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.xianshikuan:
                intent.putExtra("sss",0);
                intent.setClass(this,SSXS.class);
                startActivity(intent);
                break;
            case R.id.xianshikuan2:
                intent.putExtra("sss",1);
                intent.setClass(this,SSXS.class);
                startActivity(intent);
                break;
            case R.id.xianshikuan3:
                intent.putExtra("sss",1);
                intent.setClass(this,SSXS.class);
                startActivity(intent);
                break;
            case R.id.xianshikuan4:
                intent.putExtra("sss",3);
                intent.setClass(this,SSXS.class);
                startActivity(intent);
                break;
            case R.id.xianshikuan5:
                intent.putExtra("sss",4);
                intent.setClass(this,SSXS.class);
                startActivity(intent);
                break;

        }
    }
}