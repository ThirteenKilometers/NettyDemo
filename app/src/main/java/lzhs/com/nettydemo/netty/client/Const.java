package lzhs.com.nettydemo.netty.client;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 13:09<br/>
 * 邮箱：1050629507@qq.com
 */
public final class Const {
    /**
     * 服务器连接地址
     */
    public static final String HOST = "192.168.1.112";
    /**
     * 服务器连接端口
     */
    public static final int TCP_PORT = 19999;
    /**
     * 服务器连接成功状态码
     */
    public final static int STATUS_CONNECT_SUCCESS = 0x10a;
    /**
     * 服务器连接关闭状态吗
     */
    public final static int STATUS_CONNECT_CLOSED = 0x10b;
    /**
     * 服务器连接异常状态码
     */
    public final static int STATUS_CONNECT_ERROR = 0x10c;
    /**
     * 服务器向客户端发送消息状态码
     */
    public final static int ACCEPT_CODE = 0x10e;
    /**
     * 客户端向服务器发送消息状态码
     */
    public final static int SEND_CODE = 0x10f;
    /**
     * 客户端向服务器发送消息成功状态码
     */
    public final static int SEND_SUCCESS_CODE = 0x0;
    /**
     * 客户端向服务器发送消息失败状态码
     */
    public final static int SEND_FALIE_CODE = 0x1;
    /**
     * 登录接口
     */
    public final static String METHER_LOGIN = "login";
    /**
     * 登录接口 code
     */
    public final static int METHER_LOGIN_CODE = 0xa001;
    /**
     * 设备信息上传接口
     */
    public final static String METHER_UPLOAD_DEVICE_INFO = "uploadDeviceInfo";
    /**
     * 设备信息上传接口 code
     */
    public final static int METHER_UPLOAD_DEVICE_INFO_CODE = 0xa002;


}
