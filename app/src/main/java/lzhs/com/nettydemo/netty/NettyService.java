package lzhs.com.nettydemo.netty;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lzhs.com.nettydemo.beans.MessageEvent;
import lzhs.com.nettydemo.beans.accept_bean.base.BaseAcceptMsgBean;
import lzhs.com.nettydemo.netty.client.Const;
import lzhs.com.nettydemo.netty.client.NettyClient;
import lzhs.com.nettydemo.netty.client.NettyListener;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 12:09<br/>
 * 邮箱：1050629507@qq.com
 */
public class NettyService extends Service implements NettyListener {

    public static final String TAG = "NettyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        NettyClient.getInstance().setListener(this);
        connect();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "bindService() executed");
        NettyClient.getInstance().setListener(this);
        connect();
        return null;
    }

    private void connect() {
        if (!NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器
                }
            }).start();
        }
    }

    /**
     * 接收服务器消息
     * @param byteBuf
     */
    @Override
    public void onMessageResponse(ByteBuf byteBuf) {
        try {
            MessageEvent msg = new MessageEvent();
            byte[] req = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(req);
            String body = new String(req, "UTF-8");
            BaseAcceptMsgBean acceptBean = JSON.parseObject(body, BaseAcceptMsgBean.class);
            if (acceptBean.isSuccess()) {
                msg.setMsg("来自服务器消息,本次请求成功");
                switch (acceptBean.getMethod()) {
                    case Const.METHER_LOGIN://登录接口：method=”login”
                        msg.setCode(Const.METHER_LOGIN_CODE);
                        break;
                    case Const.METHER_UPLOAD_DEVICE_INFO://设备信息上传接口：method=”uploadDeviceInfo”
                        msg.setCode(Const.METHER_UPLOAD_DEVICE_INFO_CODE);
                        break;
                }
                msg.setData(acceptBean.getContent());
            } else {
                msg.setMsg("来自服务器消息,服务器认为这个次请求失败，失败原因：" + acceptBean.getMessage());
                msg.setCode(Const.ACCEPT_CODE);
                msg.setData(body);
            }
            EventBus.getDefault().post(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送消息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sendMsgToService(MessageEvent event) {/* Do something */
        if (event.getCode() == Const.SEND_CODE)
            NettyClient.getInstance().sendMsgToServer(((String) event.getData()).getBytes()
                    , new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                onServiceStatusConnectChanged(Const.SEND_SUCCESS_CODE);
                            } else {
                                onServiceStatusConnectChanged(Const.SEND_FALIE_CODE);
                            }
                        }
                    });
    }

    /**
     * 连接状态监听
     *
     * @param statusCode
     */
    @Override
    public void onServiceStatusConnectChanged(int statusCode) {
        MessageEvent msg = new MessageEvent();
        switch (statusCode) {
            case Const.STATUS_CONNECT_SUCCESS:
                msg.setCode(Const.STATUS_CONNECT_SUCCESS);
                msg.setMsg("与服务器连接成功");
                break;
            case Const.STATUS_CONNECT_CLOSED:
                msg.setCode(Const.STATUS_CONNECT_CLOSED);
                msg.setMsg("与服务器连接关闭");
                break;
            case Const.STATUS_CONNECT_ERROR:
                msg.setCode(Const.STATUS_CONNECT_ERROR);
                msg.setMsg("与服务器连接异常");
                break;
            case Const.SEND_SUCCESS_CODE:
                msg.setCode(Const.SEND_SUCCESS_CODE);
                msg.setMsg("客户端向服务器发送消息成功");
                break;
            case Const.SEND_FALIE_CODE:
                msg.setCode(Const.STATUS_CONNECT_ERROR);
                msg.setMsg("客户端向服务器发送消息成功失败");
                break;
        }
        EventBus.getDefault().post(msg);

    }
}
