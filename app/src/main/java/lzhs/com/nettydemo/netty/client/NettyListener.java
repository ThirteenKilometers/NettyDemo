package lzhs.com.nettydemo.netty.client;

import io.netty.buffer.ByteBuf;

public interface NettyListener {


    /**
     * 对消息的处理
     */
    void onMessageResponse(ByteBuf byteBuf);

    /**
     * 当服务状态发生变化时触发
     */
    void onServiceStatusConnectChanged(int statusCode);
}
