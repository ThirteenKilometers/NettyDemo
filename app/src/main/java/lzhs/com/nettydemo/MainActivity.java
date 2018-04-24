package lzhs.com.nettydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lzhs.com.nettydemo.beans.MessageEvent;
import lzhs.com.nettydemo.beans.accept_bean.AcceptLoginBean;
import lzhs.com.nettydemo.beans.accept_bean.AcceptUploadDeviceInfBean;
import lzhs.com.nettydemo.beans.send_bean.SendLoginBean;
import lzhs.com.nettydemo.beans.send_bean.SendUploadDeviceInfoBean;
import lzhs.com.nettydemo.beans.send_bean.base.BaseSendMsgBean;
import lzhs.com.nettydemo.beans.send_bean.base.BaseUserBean;
import lzhs.com.nettydemo.netty.client.Const;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";

    TextView mTextShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mTextShow = findViewById(R.id.mTextShow);
        findViewById(R.id.mBtnLogin).setOnClickListener(this);
        findViewById(R.id.mBtnUploadDeviceInfo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String data = "";
        switch (view.getId()) {
            case R.id.mBtnLogin:
                data = createLogingBean();
                break;
            case R.id.mBtnUploadDeviceInfo:
                data = createUploadDeviceInfoMessage();
                break;
        }
        sendMessage(data);
    }

    /**
     * 设备信息上传接口：method=”uploadDeviceInfo”
     */
    private String createUploadDeviceInfoMessage() {
        BaseSendMsgBean sendMsgBean = createDefaultSendBean();

        SendUploadDeviceInfoBean uploadDeviceInfoBean = new SendUploadDeviceInfoBean();
        uploadDeviceInfoBean.setNotification("值：REQUEST");
        uploadDeviceInfoBean.setDeviceCode("设备序列号");
        uploadDeviceInfoBean.setDeviceType("设备型号");
        uploadDeviceInfoBean.setDeviceModel("设备制作商");
        uploadDeviceInfoBean.setDeviceSystem("操作系统");
        uploadDeviceInfoBean.setDeviceSystemVersion("系统版本");
        uploadDeviceInfoBean.setMac("设备MAC地址");
        uploadDeviceInfoBean.setDeviceCode("设备序列号");
        uploadDeviceInfoBean.setImei("IMEI");
        uploadDeviceInfoBean.setEsn("ESN");
        uploadDeviceInfoBean.setCpuOccupy(1);
        uploadDeviceInfoBean.setRamOccupy(1);
        uploadDeviceInfoBean.setGpsState(false);
        uploadDeviceInfoBean.setBluetoothState(false);
        uploadDeviceInfoBean.setNetworkState(false);
        uploadDeviceInfoBean.setElectricity(false);
        uploadDeviceInfoBean.setSignalIntensity("信号强度");
        uploadDeviceInfoBean.setAccessInfo("接入点信息");
        uploadDeviceInfoBean.setSimInfo("SIM卡信息");
        uploadDeviceInfoBean.setPositionInfo("位置信息");
        uploadDeviceInfoBean.setStorageInfo("存储信息");
        uploadDeviceInfoBean.setAppInfo("应用安装信息");
        uploadDeviceInfoBean.setCertificateInfo("证书信息");
        uploadDeviceInfoBean.setConfigInfo("配置信息");


        sendMsgBean.setContent(uploadDeviceInfoBean);

        return JSON.toJSONString(sendMsgBean);

    }

    public void sendMessage(String data) {
        MessageEvent event = new MessageEvent<String>();
        event.setCode(Const.SEND_CODE);
        event.setMsg("正在向服务器发送登录消息");
        event.setData(data);
        EventBus.getDefault().post(event);

    }

    private String createLogingBean() {
        BaseSendMsgBean sendMsgBean = createDefaultSendBean();

        SendLoginBean loginBean = new SendLoginBean();
        loginBean.setDeviceCode("当前设备号");
        loginBean.setNotification("值：REQUEST");
        loginBean.setPassword("登录密码，传MD5加密后的值");
        loginBean.setUserCode("登录账号");

        sendMsgBean.setContent(loginBean);
        sendMsgBean.setMethod(Const.METHER_LOGIN);

        return JSON.toJSONString(sendMsgBean);
    }

    @NonNull
    private BaseSendMsgBean createDefaultSendBean() {
        BaseSendMsgBean sendMsgBean = new BaseSendMsgBean();
        sendMsgBean.setSender(createDefault());

        List<BaseUserBean> recipients = new ArrayList<>();
        recipients.add(createDefault());
        sendMsgBean.setRecipients(recipients);
        return sendMsgBean;
    }

    private BaseUserBean createDefault() {
        BaseUserBean userBean = new BaseUserBean();
        userBean.setClient("ANDROIDPHONE");
        userBean.setClientVersion("客户端版本，发件人必须传，收件人可以不传");
        userBean.setIct("通信类型SOCKET; WEBSOCKET，可以不传");
        userBean.setUserCode("用户账号， 发件人可以不传，收件人必须传");

        return userBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void acceptMsg(MessageEvent event) {
        switch (event.getCode()) {
            case Const.ACCEPT_CODE:
                setText((String) event.getData());
                break;
            case Const.METHER_LOGIN_CODE:  //登录操作
                AcceptLoginBean loginBean = JSON.parseObject((String) event.getData(), AcceptLoginBean.class);


                break;
            case Const.METHER_UPLOAD_DEVICE_INFO_CODE:  //设备信息上传接口

                AcceptUploadDeviceInfBean uploadDeviceInfBean=JSON.parseObject((String) event.getData(), AcceptUploadDeviceInfBean.class);

                break;
        }
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_LONG).show();
    }

//    private BaseMagBean getTextBean(String data) {
//        BaseMagBean bean = JSON.parseObject(data, BaseMagBean.class);
//        bean.setContent(JSON.parseObject(bean.getContent().toString(), ContentLoginBean.class));
//        return bean;
//    }

    public void setText(String text) {
        StringBuffer stringBuffer = new StringBuffer(mTextShow.getText());
        stringBuffer.append(text + "\n\r");
        mTextShow.setText(stringBuffer.toString());
    }


}
