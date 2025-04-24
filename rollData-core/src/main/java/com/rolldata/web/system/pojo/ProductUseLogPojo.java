package com.rolldata.web.system.pojo;

/**
 * @Title: ProductUseLogPojo
 * @Description: ProductUseLogPojo
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2021-10-29
 * @version: V1.0
 */
public class ProductUseLogPojo implements java.io.Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1515213441147436039L;

    /**机器码*/
    private String machineCode;

    /**类型，1初始化安装，2启动服务，3正常使用*/
    private String type;

    /**
     * 获取机器码
     * @return machineCode 机器码
     */
    public String getMachineCode() {
        return machineCode;
    }

    /**
     * 设置机器码
     * @param machineCode 机器码
     */
    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    /**
     * 获取类型，1初始化安装，2启动服务，3正常使用
     * @return type 类型，1初始化安装，2启动服务，3正常使用
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型，1初始化安装，2启动服务，3正常使用
     * @param type 类型，1初始化安装，2启动服务，3正常使用
     */
    public void setType(String type) {
        this.type = type;
    }
}
