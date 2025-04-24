package com.rolldata.core.task.pojo;

import com.rolldata.core.util.StringUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

public class PoolResourcePojo implements Serializable {

    private static final long serialVersionUID = -3037361902601730905L;

    private final String machineCode;
    private final String userCode;
    private final String resourceName;
    private final String accessTime;

    public PoolResourcePojo(String machineCode, String userCode, String resourceName, String accessTime) {

        this.machineCode = machineCode;
        try {
            if (StringUtil.isEmpty(userCode)) {
                userCode = "未登录的用户";
            }
            this.userCode = URLEncoder.encode(userCode, "UTF-8");
            this.resourceName = URLEncoder.encode(resourceName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        this.accessTime = accessTime;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getAccessTime() {
        return accessTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PoolResourcePojo entity = (PoolResourcePojo) o;
        return Objects.equals(this.machineCode, entity.machineCode) &&
                Objects.equals(this.userCode, entity.userCode) &&
                Objects.equals(this.resourceName, entity.resourceName) &&
                Objects.equals(this.accessTime, entity.accessTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineCode, userCode, resourceName, accessTime);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "machineCode = " + machineCode + ", " +
                "userCode = " + userCode + ", " +
                "resourceName = " + resourceName + ", " +
                "accessTime = " + accessTime + ")";
    }
}