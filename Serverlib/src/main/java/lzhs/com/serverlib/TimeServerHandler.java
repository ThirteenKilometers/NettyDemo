package lzhs.com.serverlib;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
    /**
     * 覆盖channelActive 方法在channel被启用的时候触发（在建立连接的时候）
     * 覆盖了 channelActive() 事件处理方法。服务端监听到客户端活动
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {// (2)
        Channel incoming = ctx.channel();
        System.out.println("---------------handlerAdded");
        System.out.println("客户端" + incoming.remoteAddress() + " 加入\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        System.out.println("---------------handlerRemoved");
        System.out.println("客户端" + incoming.remoteAddress() + " 离开\n");
        // Broadcast a message to multiple Channels
    }

    /*当有数据时，自动调用，读取msg*/
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        final Channel channel = ctx.channel();
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("来自客户端" + channel.remoteAddress() + "消息: " + body);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    ByteBuf buf = Unpooled.copiedBuffer(createData());
                    channel.writeAndFlush(buf).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {

                            System.out.println("===============这个事华丽的分割线，你知道吗你？=======================");
                            if (future.isSuccess()) {
                                System.out.println("向客户端" + channel.remoteAddress() + "发送消息成功");
                            } else {
                                System.out.println("向客户端" + channel.remoteAddress() + "发送消息失败");
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 覆盖了 handlerAdded() 事件处理方法。
     * 每当从服务端收到新的客户端连接时
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }


    private byte[] createData() {
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

        String val = JSONObject.toJSONString(bean);
        return val.getBytes();
    }
}