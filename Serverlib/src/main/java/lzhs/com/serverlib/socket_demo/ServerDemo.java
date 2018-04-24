package lzhs.com.serverlib.socket_demo;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lzhs.com.serverlib.BaseMagBean;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/23 09:13<br/>
 * 邮箱：1050629507@qq.com
 */
public class ServerDemo {
    public static final int PORT = 19999;//监听的端口号

    public static void main(String[] args) {
        System.out.println("服务器启动...\n");
        ServerDemo server = new ServerDemo();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                new HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // 读取客户端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据
                System.out.println("客户端发过来的内容:" + clientInputStr);

                // 向客户端回复信息
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.print("请输入:\t");
                // 发送键盘输入的一行
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(s);

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
    private String createData() {
        BaseMagBean bean = new BaseMagBean();

        bean.setMethod("login");
        bean.setMessage("欢欢，我爱你");
        bean.setSuccess(true);

        BaseMagBean.SenderBean mSender = new BaseMagBean.SenderBean();
        mSender.setClient("来自服务器消息");
        mSender.setUserCode("来自服务器消息");
        mSender.setIct("来自服务器消息SOCKET");

        bean.setSender(mSender);

        List<BaseMagBean.RecipientsBean> recipientsBeans = new ArrayList<>();
        BaseMagBean.RecipientsBean recipientsBean = new BaseMagBean.RecipientsBean();
        recipientsBean.setClient("来自服务器消息ANDROIDPHONE");
        recipientsBean.setIct("来自服务器消息SOCKET");
        recipientsBean.setUserCode("来自服务器消息testUserCode");
        recipientsBeans.add(recipientsBean);

        bean.setRecipients(recipientsBeans);

        BaseMagBean.ContentBean contentBean = new BaseMagBean.ContentBean();
        contentBean.setMessage("来自服务器消息 ： 欢欢我爱你");
        contentBean.setSuccess(false);
        contentBean.setNotification("RESPONES");
        bean.setContent(contentBean);

        return JSONObject.toJSONString(bean);
    }

}
