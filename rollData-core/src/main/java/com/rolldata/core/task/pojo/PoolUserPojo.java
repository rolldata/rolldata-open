package com.rolldata.core.task.pojo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

public class PoolUserPojo implements Serializable {

    private static final long serialVersionUID = -4313067200079029818L;

    private final String machineCode;
    private final String userCode;
    private final String userName;
    private final String loginTime;

    public PoolUserPojo(String machineCode, String userCode, String userName, String loginTime) {


        this.machineCode = machineCode;
        try {
            this.userCode = URLEncoder.encode(userCode, "UTF-8");
            this.userName = URLEncoder.encode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.loginTime = loginTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getLoginTime() {
        return loginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoolUserPojo entity = (PoolUserPojo) o;
        return Objects.equals(this.machineCode, entity.machineCode) &&
                Objects.equals(this.userCode, entity.userCode) &&
                Objects.equals(this.userName, entity.userName) &&
                Objects.equals(this.loginTime, entity.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineCode, userCode, userName, loginTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "machineCode = " + machineCode + ", " +
                "userCode = " + userCode + ", " +
                "userName = " + userName + ", " +
                "loginTime = " + loginTime + ")";
    }
}