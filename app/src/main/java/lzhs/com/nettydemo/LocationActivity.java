package lzhs.com.nettydemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import lzhs.com.library.wedgit.location.LocationService;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/5/9 10:15<br/>
 * 邮箱：1050629507@qq.com
 */
public class LocationActivity extends AppCompatActivity {
    TextView mTextShow;
    LocationService mLocationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mTextShow = findViewById(R.id.mTextShow);
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("lastLatitude: unknown\n")
                .append("lastLongitude: unknown\n")
                .append("latitude: unknown\n")
                .append("longitude: unknown\n")
                .append("getCountryName: unknown\n")
                .append("getLocality: unknown\n")
                .append("getStreet: unknown\n");
        mTextShow.setText(stringBuffer.toString());


        bindService(new Intent(this, LocationService.class), conn, Context.BIND_AUTO_CREATE);



    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationService = ((LocationService.LocationBinder) service).getService();
            mLocationService.setOnGetLocationListener(new LocationService.OnGetLocationListener() {
                @Override
                public void getLocation(final String lastLatitude, final String lastLongitude, final String latitude, final String longitude, final String country, final String locality, final String street) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StringBuffer stringBuffer = new StringBuffer("");
                            stringBuffer
                                    .append("lastLatitude: " + lastLatitude+"\n")
                                    .append("lastLongitude: " + lastLongitude+"\n")
                                    .append("latitude: " + latitude+"\n")
                                    .append("longitude: " + longitude+"\n")
                                    .append("getCountryName: " + country+"\n")
                                    .append("getLocality: " + locality+"\n")
                                    .append("getStreet: " + street);
                            mTextShow.setText(stringBuffer.toString());
                        }
                    });
                }
            });
        }
    };
}
