package lzhs.com.nettydemo.netty.client;

import android.util.Log;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 13:05<br/>
 * 邮箱：1050629507@qq.com
 */
public class NettyClient {
    public static final String TAG = "NettyClient";
    private static NettyClient nettyClient = new NettyClient();

    private EventLoopGroup group;

    private NettyListener listener;

    private Channel channel;
    /**
     * 是否连接
     */
    private boolean isConnect = false;

    /**
     * 重连次数
     */
    private int reconnectNum = Integer.MAX_VALUE;
    /**
     * 重连时间设置
     */
    private long reconnectIntervalTime = 5000;

    public static NettyClient getInstance() {
        return nettyClient;
    }

    public synchronized NettyClient connect() {
        if (!isConnect) {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap().group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientInitializer(listener));

            try {
                bootstrap.connect(Const.HOST, Const.TCP_PORT).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            isConnect = true;
                            channel = channelFuture.channel();
                        } else {
                            isConnect = false;
                        }
                    }
                }).sync();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                listener.onServiceStatusConnectChanged(Const.STATUS_CONNECT_ERROR);
                reconnect();
            }
        }
        return this;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        group.shutdownGracefully();
    }

    public void reconnect() {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
            }
            Log.e(TAG, "重新连接");
            disconnect();
            connect();
        } else {
            disconnect();
        }
    }

    public boolean sendMsgToServer(byte[] data, ChannelFutureListener listener) {
        boolean flag = channel != null && isConnect;
        if (flag) {
            ByteBuf buf = Unpooled.copiedBuffer(data);
            channel.writeAndFlush(buf).addListener(listener);
        }
        return flag;
    }

    public void sendMsgToServer(final String sendMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (channel != null && channel.isOpen()) {
                        channel.writeAndFlush(sendMsg).sync();
                        listener.onServiceStatusConnectChanged(Const.SEND_SUCCESS_CODE);
                    } else {
                        throw new Exception("channel is null | closed");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "send failed " + e.getMessage());
                    listener.onServiceStatusConnectChanged(Const.SEND_FALIE_CODE);
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

    public void setReconnectIntervalTime(long reconnectIntervalTime) {
        this.reconnectIntervalTime = reconnectIntervalTime;
    }

    public boolean getConnectStatus() {
        return isConnect;
    }

    public void setConnectStatus(boolean status) {
        this.isConnect = status;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }

}
