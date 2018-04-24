package lzhs.com.nettydemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import lzhs.com.nettydemo.beans.BaseMagBean;
import lzhs.com.nettydemo.beans.ContentLoginBean;
import lzhs.com.nettydemo.beans.MessageEvent;
import lzhs.com.nettydemo.netty.client.Const;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MainActivity";

    TextView mTextShow;
   Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mTextShow = findViewById(R.id.mTextShow);
        mBtnLogin=findViewById(R.id.mBtnLogin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtnLogin:
                sendLoginMessage();
                break;
        }
    }
    public void sendLoginMessage() {
        MessageEvent event = new MessageEvent<String>();
        event.setCode(Const.SEND_CODE);
        event.setMsg("正在向服务器发送登录消息");
        event.setData(JSONObject.toJSONString(createBean()));
        EventBus.getDefault().post(event);

    }

    @NonNull
    private BaseMagBean createBean() {
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

        ContentLoginBean contentBean = new ContentLoginBean();
        contentBean.setMessage("密码错误");
        contentBean.setSuccess(false);
        contentBean.setNotification("RESPONES");
        bean.setContent(JSONObject.toJSONString(contentBean));
        return bean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getCode()) {
            case Const.ACCEPT_CODE:
                setText((String) event.getData());
                break;
            case Const.METHER_LOGIN_CODE:
                //登录操作
                ContentLoginBean bean = getTextBean((String) event.getData());
                setText(bean.getMessage());
                break;
        }
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_LONG).show();
    }

    private ContentLoginBean getTextBean(String data) {
        BaseMagBean bean = JSON.parseObject(data, BaseMagBean.class);
        return JSON.parseObject(bean.getContent(), ContentLoginBean.class);


    }

    public void setText(String text) {
        StringBuffer stringBuffer = new StringBuffer(mTextShow.getText());
        stringBuffer.append(text + "\n\r");
        mTextShow.setText(stringBuffer.toString());
    }


}
