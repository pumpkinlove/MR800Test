package com.miaxis.mr800test.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by xu.nan on 2016/9/7.
 */
@Table(name = "versions")
public class Versions implements Serializable {

    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "systemVersion")
    private String systemVersion;           //操作系统版本
    @Column(name = "tICVersion")
    private String tICVersion;              //接触式IC卡
    @Column(name = "utICVersion")
    private String utICVersion;             //非接触式IC卡
    @Column(name = "fingerVersion")
    private String fingerVersion;           //指纹仪
    @Column(name = "idCardVersion")
    private String idCardVersion;           //二代证
    @Column(name = "aVersion")
    private String aVersion;                //测试程序版本

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String gettICVersion() {
        return tICVersion;
    }

    public void settICVersion(String tICVersion) {
        this.tICVersion = tICVersion;
    }

    public String getUtICVersion() {
        return utICVersion;
    }

    public void setUtICVersion(String utICVersion) {
        this.utICVersion = utICVersion;
    }

    public String getFingerVersion() {
        return fingerVersion;
    }

    public void setFingerVersion(String fingerVersion) {
        this.fingerVersion = fingerVersion;
    }

    public String getIdCardVersion() {
        return idCardVersion;
    }

    public void setIdCardVersion(String idCardVersion) {
        this.idCardVersion = idCardVersion;
    }

    public String getaVersion() {
        return aVersion;
    }

    public void setaVersion(String aVersion) {
        this.aVersion = aVersion;
    }
}
