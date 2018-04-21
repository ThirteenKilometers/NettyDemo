package lzhs.com.nettydemo.beans;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 13:33<br/>
 * 邮箱：1050629507@qq.com
 */
public class MessageEvent<T> {

    int code;
    String msg;
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
