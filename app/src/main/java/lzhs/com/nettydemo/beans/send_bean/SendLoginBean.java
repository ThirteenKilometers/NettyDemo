package lzhs.com.nettydemo.beans.send_bean;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/25 00:47<br/>
 * 邮箱：1050629507@qq.com
 */
public class SendLoginBean {
    String notification = "";// 值：REQUEST
    String userCode = "";//录账号
    String password = "";// 登录密码，传MD5加密后的值
    String deviceCode = "";//前设备号

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
