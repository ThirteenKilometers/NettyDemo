package lzhs.com.nettydemo.beans;

import java.util.List;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/21 12:51<br/>
 * 邮箱：1050629507@qq.com
 */
public class BaseMagBean {

    /**
     * sender : {"userCode":"INTERFACE","client":"INTERFACE","ict":"SOCKET"}
     * recipients : [{"userCode":"testUserCode","client":"ANDROIDPHONE","ict":"SOCKET"}]
     * method : login
     * content : {"success":false,"message":"密码错误","notification":"RESPONES"}
     * success : true
     * message : sucess
     */

    private SenderBean sender;
    private String method;
    private ContentBean content;
    private boolean success;
    private String message;
    private List<RecipientsBean> recipients;

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecipientsBean> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientsBean> recipients) {
        this.recipients = recipients;
    }

    public static class SenderBean {
        /**
         * userCode : INTERFACE
         * client : INTERFACE
         * ict : SOCKET
         */

        private String userCode;
        private String client;
        private String ict;

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getIct() {
            return ict;
        }

        public void setIct(String ict) {
            this.ict = ict;
        }
    }

    public static class ContentBean {
        /**
         * success : false
         * message : 密码错误
         * notification : RESPONES
         */

        private boolean success;
        private String message;
        private String notification;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNotification() {
            return notification;
        }

        public void setNotification(String notification) {
            this.notification = notification;
        }
    }

    public static class RecipientsBean {
        /**
         * userCode : testUserCode
         * client : ANDROIDPHONE
         * ict : SOCKET
         */

        private String userCode;
        private String client;
        private String ict;

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getIct() {
            return ict;
        }

        public void setIct(String ict) {
            this.ict = ict;
        }
    }
}
