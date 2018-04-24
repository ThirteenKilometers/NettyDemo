package lzhs.com.nettydemo.beans.send_bean;

/**
 * 设备信息上传接口：method=”uploadDeviceInfo”
 * 请求Entity 信息
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/25 00:47<br/>
 * 邮箱：1050629507@qq.com
 */
public class SendUploadDeviceInfoBean {
    /**
     * 值：REQUEST
     */
    String notification;
    /**
     * 设备型号
     */
    String  deviceType;
    /**
     * 设备制作商
     */
    String deviceModel;
    /**
     * 操作系统
     */
    String deviceSystem;
    /**
     * 系统版本
     */
    String deviceSystemVersion;
    /**
     * 设备MAC地址
     */
    String mac;
    /**
     * 设备序列号
     */
    String deviceCode;
    /**
     * IMEI
     */
    String imei;
    /**
     * ESN
     */
    String esn;
    /**
     * CPU占用1 表示100%   0 表示0%
     */
    double cpuOccupy;
    /**
     * 内存占用1 表示100%   0 表示0%
     */
    double ramOccupy;

    /**
     * GPS状态
     */
    boolean gpsState;

    /**
     * 蓝牙状态
     */
    boolean bluetoothState;

    /**
     * 网络状态
     */
    boolean networkState;

    /**
     * 电量状态1 表示100%0表示0%
     */
    boolean electricity;
    /**
     * 信号强度
     */
    String signalIntensity;

    /**
     * 接入点信息
     */
    String accessInfo;

    /**
     * SIM卡信息
     */
    String simInfo;

    /**
     * 位置信息
     */
    String positionInfo;
    /**
     * 存储信息
     */
    String storageInfo;
    /**
     * 应用安装信息
     */
    String appInfo;

    /**
     * 证书信息
     */
    String certificateInfo;

    /**
     * 配置信息
     */
    String configInfo;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }

    public String getDeviceSystemVersion() {
        return deviceSystemVersion;
    }

    public void setDeviceSystemVersion(String deviceSystemVersion) {
        this.deviceSystemVersion = deviceSystemVersion;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public double getCpuOccupy() {
        return cpuOccupy;
    }

    public void setCpuOccupy(double cpuOccupy) {
        this.cpuOccupy = cpuOccupy;
    }

    public double getRamOccupy() {
        return ramOccupy;
    }

    public void setRamOccupy(double ramOccupy) {
        this.ramOccupy = ramOccupy;
    }

    public boolean isGpsState() {
        return gpsState;
    }

    public void setGpsState(boolean gpsState) {
        this.gpsState = gpsState;
    }

    public boolean isBluetoothState() {
        return bluetoothState;
    }

    public void setBluetoothState(boolean bluetoothState) {
        this.bluetoothState = bluetoothState;
    }

    public boolean isNetworkState() {
        return networkState;
    }

    public void setNetworkState(boolean networkState) {
        this.networkState = networkState;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public String getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(String signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public String getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(String accessInfo) {
        this.accessInfo = accessInfo;
    }

    public String getSimInfo() {
        return simInfo;
    }

    public void setSimInfo(String simInfo) {
        this.simInfo = simInfo;
    }

    public String getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(String positionInfo) {
        this.positionInfo = positionInfo;
    }

    public String getStorageInfo() {
        return storageInfo;
    }

    public void setStorageInfo(String storageInfo) {
        this.storageInfo = storageInfo;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    public String getCertificateInfo() {
        return certificateInfo;
    }

    public void setCertificateInfo(String certificateInfo) {
        this.certificateInfo = certificateInfo;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }
}
