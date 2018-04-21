package lzhs.com.nettydemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lzhs.com.nettydemo.beans.BaseMagBean;
import lzhs.com.nettydemo.beans.MessageEvent;
import lzhs.com.nettydemo.netty.NettyService;
import lzhs.com.nettydemo.netty.client.Const;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ServiceConnection connection = new ServiceConnection() {
        /**
         * 与服务器端交互的接口方法 绑定服务的时候被回调，在这个方法获取绑定Service传递过来的IBinder对象，
         * 通过这个IBinder对象，实现宿主和Service的交互。
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "绑定成功调用：onServiceConnected");
        }

        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        Intent mIntent = new Intent(this, NettyService.class);
        bindService(mIntent, connection, Service.BIND_AUTO_CREATE);
    }

    public void sendMessage(View v) {
        BaseMagBean bean = new BaseMagBean();

        bean.setMethod("login");
        bean.setMessage("sucess");
        bean.setSuccess(true);

        BaseMagBean.SenderBean mSender = new BaseMagBean.SenderBean();
        mSender.setClient("INTERFACE");
        mSender.setUserCode("INTERFACE");
        mSender.setIct("SOCKET");

        bean.setSender(mSender);

        List<BaseMagBean.RecipientsBean> recipientsBeans = new ArrayList<>();
        BaseMagBean.RecipientsBean recipientsBean = new BaseMagBean.RecipientsBean();
        recipientsBean.setClient("ANDROIDPHONE");
        recipientsBean.setIct("SOCKET");
        recipientsBean.setUserCode("testUserCode");
        recipientsBeans.add(recipientsBean);

        bean.setRecipients(recipientsBeans);

        BaseMagBean.ContentBean contentBean = new BaseMagBean.ContentBean();
        contentBean.setMessage("密码错误");
        contentBean.setSuccess(false);
        contentBean.setNotification("RESPONES");
        bean.setContent(contentBean);


        MessageEvent event = new MessageEvent<String>();
        event.setCode(Const.SEND_CODE);
        event.setMsg("正在向服务器发送消息");
        event.setData(JSONObject.toJSONString(bean));

        EventBus.getDefault().post(event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_LONG).show();
    }
}
