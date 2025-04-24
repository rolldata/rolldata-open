package com.rolldata.web.system.pojo;

/**
 * @Title: DevMonitorServerResult
 * @Description: 服务器监控结果
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-02-06
 * @version: V1.0
 */
public class DevMonitorServerResult {
    /* ==============概览数据============ */
    /** CPU信息 */
    private DevMonitorCpuInfo devMonitorCpuInfo;

    /** 内存信息 */
    private DevMonitorMemoryInfo devMonitorMemoryInfo;

    /** 存储信息 */
    private DevMonitorStorageInfo devMonitorStorageInfo;

    /**网络信息*/
    private DevMonitorNetworkInfo devMonitorNetworkInfo;

    /* ==============服务器数据============ */
    /** 服务器信息 */
    private DevMonitorServerInfo devMonitorServerInfo;

    /* ==============JVM数据============ */
    /** JVM信息 */
    private DevMonitorJvmInfo devMonitorJvmInfo;

    /**
     * 获取 CPU信息
     *
     * @return devMonitorCpuInfo CPU信息
     */
    public DevMonitorCpuInfo getDevMonitorCpuInfo() {
        return this.devMonitorCpuInfo;
    }

    /**
     * 设置 CPU信息
     *
     * @param devMonitorCpuInfo CPU信息
     */
    public void setDevMonitorCpuInfo(DevMonitorCpuInfo devMonitorCpuInfo) {
        this.devMonitorCpuInfo = devMonitorCpuInfo;
    }

    /**
     * 获取 内存信息
     *
     * @return devMonitorMemoryInfo 内存信息
     */
    public DevMonitorMemoryInfo getDevMonitorMemoryInfo() {
        return this.devMonitorMemoryInfo;
    }

    /**
     * 设置 内存信息
     *
     * @param devMonitorMemoryInfo 内存信息
     */
    public void setDevMonitorMemoryInfo(DevMonitorMemoryInfo devMonitorMemoryInfo) {
        this.devMonitorMemoryInfo = devMonitorMemoryInfo;
    }

    /**
     * 获取 存储信息
     *
     * @return devMonitorStorageInfo 存储信息
     */
    public DevMonitorStorageInfo getDevMonitorStorageInfo() {
        return this.devMonitorStorageInfo;
    }

    /**
     * 设置 存储信息
     *
     * @param devMonitorStorageInfo 存储信息
     */
    public void setDevMonitorStorageInfo(DevMonitorStorageInfo devMonitorStorageInfo) {
        this.devMonitorStorageInfo = devMonitorStorageInfo;
    }

    /**
     * 获取 网络信息
     *
     * @return devMonitorNetworkInfo 网络信息
     */
    public DevMonitorNetworkInfo getDevMonitorNetworkInfo() {
        return this.devMonitorNetworkInfo;
    }

    /**
     * 设置 网络信息
     *
     * @param devMonitorNetworkInfo 网络信息
     */
    public void setDevMonitorNetworkInfo(DevMonitorNetworkInfo devMonitorNetworkInfo) {
        this.devMonitorNetworkInfo = devMonitorNetworkInfo;
    }

    /**
     * 获取 服务器信息
     *
     * @return devMonitorServerInfo 服务器信息
     */
    public DevMonitorServerInfo getDevMonitorServerInfo() {
        return this.devMonitorServerInfo;
    }

    /**
     * 设置 服务器信息
     *
     * @param devMonitorServerInfo 服务器信息
     */
    public void setDevMonitorServerInfo(DevMonitorServerInfo devMonitorServerInfo) {
        this.devMonitorServerInfo = devMonitorServerInfo;
    }

    /**
     * 获取 JVM信息
     *
     * @return devMonitorJvmInfo JVM信息
     */
    public DevMonitorJvmInfo getDevMonitorJvmInfo() {
        return this.devMonitorJvmInfo;
    }

    /**
     * 设置 JVM信息
     *
     * @param devMonitorJvmInfo JVM信息
     */
    public void setDevMonitorJvmInfo(DevMonitorJvmInfo devMonitorJvmInfo) {
        this.devMonitorJvmInfo = devMonitorJvmInfo;
    }

    /**
     * CPU信息类
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorCpuInfo {

        /** CPU名称 */
        private String cpuName;

        /** CPU数量 */
        private String cpuNum;

        /** CPU物理核心数 */
        private String cpuPhysicalCoreNum;

        /** CPU逻辑核心数 */
        private String cpuLogicalCoreNum;

        /** CPU系统使用率 */
        private String cpuSysUseRate;

        /** CPU用户使用率 */
        private String cpuUserUseRate;

        /** CPU当前总使用率 */
        private Double cpuTotalUseRate;

        /** CPU当前等待率 */
        private String cpuWaitRate;

        /** CPU当前空闲率 */
        private String cpuFreeRate;

        /**
         * 获取 CPU名称
         *
         * @return cpuName CPU名称
         */
        public String getCpuName() {
            return this.cpuName;
        }

        /**
         * 设置 CPU名称
         *
         * @param cpuName CPU名称
         */
        public void setCpuName(String cpuName) {
            this.cpuName = cpuName;
        }

        /**
         * 获取 CPU数量
         *
         * @return cpuNum CPU数量
         */
        public String getCpuNum() {
            return this.cpuNum;
        }

        /**
         * 设置 CPU数量
         *
         * @param cpuNum CPU数量
         */
        public void setCpuNum(String cpuNum) {
            this.cpuNum = cpuNum;
        }

        /**
         * 获取 CPU物理核心数
         *
         * @return cpuPhysicalCoreNum CPU物理核心数
         */
        public String getCpuPhysicalCoreNum() {
            return this.cpuPhysicalCoreNum;
        }

        /**
         * 设置 CPU物理核心数
         *
         * @param cpuPhysicalCoreNum CPU物理核心数
         */
        public void setCpuPhysicalCoreNum(String cpuPhysicalCoreNum) {
            this.cpuPhysicalCoreNum = cpuPhysicalCoreNum;
        }

        /**
         * 获取 CPU逻辑核心数
         *
         * @return cpuLogicalCoreNum CPU逻辑核心数
         */
        public String getCpuLogicalCoreNum() {
            return this.cpuLogicalCoreNum;
        }

        /**
         * 设置 CPU逻辑核心数
         *
         * @param cpuLogicalCoreNum CPU逻辑核心数
         */
        public void setCpuLogicalCoreNum(String cpuLogicalCoreNum) {
            this.cpuLogicalCoreNum = cpuLogicalCoreNum;
        }

        /**
         * 获取 CPU系统使用率
         *
         * @return cpuSysUseRate CPU系统使用率
         */
        public String getCpuSysUseRate() {
            return this.cpuSysUseRate;
        }

        /**
         * 设置 CPU系统使用率
         *
         * @param cpuSysUseRate CPU系统使用率
         */
        public void setCpuSysUseRate(String cpuSysUseRate) {
            this.cpuSysUseRate = cpuSysUseRate;
        }

        /**
         * 获取 CPU用户使用率
         *
         * @return cpuUserUseRate CPU用户使用率
         */
        public String getCpuUserUseRate() {
            return this.cpuUserUseRate;
        }

        /**
         * 设置 CPU用户使用率
         *
         * @param cpuUserUseRate CPU用户使用率
         */
        public void setCpuUserUseRate(String cpuUserUseRate) {
            this.cpuUserUseRate = cpuUserUseRate;
        }

        /**
         * 获取 CPU当前总使用率
         *
         * @return cpuTotalUseRate CPU当前总使用率
         */
        public Double getCpuTotalUseRate() {
            return this.cpuTotalUseRate;
        }

        /**
         * 设置 CPU当前总使用率
         *
         * @param cpuTotalUseRate CPU当前总使用率
         */
        public void setCpuTotalUseRate(Double cpuTotalUseRate) {
            this.cpuTotalUseRate = cpuTotalUseRate;
        }

        /**
         * 获取 CPU当前等待率
         *
         * @return cpuWaitRate CPU当前等待率
         */
        public String getCpuWaitRate() {
            return this.cpuWaitRate;
        }

        /**
         * 设置 CPU当前等待率
         *
         * @param cpuWaitRate CPU当前等待率
         */
        public void setCpuWaitRate(String cpuWaitRate) {
            this.cpuWaitRate = cpuWaitRate;
        }

        /**
         * 获取 CPU当前空闲率
         *
         * @return cpuFreeRate CPU当前空闲率
         */
        public String getCpuFreeRate() {
            return this.cpuFreeRate;
        }

        /**
         * 设置 CPU当前空闲率
         *
         * @param cpuFreeRate CPU当前空闲率
         */
        public void setCpuFreeRate(String cpuFreeRate) {
            this.cpuFreeRate = cpuFreeRate;
        }
    }

    /**
     * 内存信息类
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorMemoryInfo {

        /** 内存总量 */
        private String memoryTotal;

        /** 内存已用 */
        private String memoryUsed;

        /** 内存剩余 */
        private String memoryFree;

        /** 内存使用率 */
        private Double memoryUseRate;

        /**
         * 获取 内存总量
         *
         * @return memoryTotal 内存总量
         */
        public String getMemoryTotal() {
            return this.memoryTotal;
        }

        /**
         * 设置 内存总量
         *
         * @param memoryTotal 内存总量
         */
        public void setMemoryTotal(String memoryTotal) {
            this.memoryTotal = memoryTotal;
        }

        /**
         * 获取 内存已用
         *
         * @return memoryUsed 内存已用
         */
        public String getMemoryUsed() {
            return this.memoryUsed;
        }

        /**
         * 设置 内存已用
         *
         * @param memoryUsed 内存已用
         */
        public void setMemoryUsed(String memoryUsed) {
            this.memoryUsed = memoryUsed;
        }

        /**
         * 获取 内存剩余
         *
         * @return memoryFree 内存剩余
         */
        public String getMemoryFree() {
            return this.memoryFree;
        }

        /**
         * 设置 内存剩余
         *
         * @param memoryFree 内存剩余
         */
        public void setMemoryFree(String memoryFree) {
            this.memoryFree = memoryFree;
        }

        /**
         * 获取 内存使用率
         *
         * @return memoryUseRate 内存使用率
         */
        public Double getMemoryUseRate() {
            return this.memoryUseRate;
        }

        /**
         * 设置 内存使用率
         *
         * @param memoryUseRate 内存使用率
         */
        public void setMemoryUseRate(Double memoryUseRate) {
            this.memoryUseRate = memoryUseRate;
        }
    }

    /**
     * 存储信息
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorStorageInfo {

        /** 存储总量 */
        private String storageTotal;

        /** 存储已用 */
        private String storageUsed;

        /** 存储剩余 */
        private String storageFree;

        /** 存储使用率 */
        private Double storageUseRate;

        /**
         * 获取 存储总量
         *
         * @return storageTotal 存储总量
         */
        public String getStorageTotal() {
            return this.storageTotal;
        }

        /**
         * 设置 存储总量
         *
         * @param storageTotal 存储总量
         */
        public void setStorageTotal(String storageTotal) {
            this.storageTotal = storageTotal;
        }

        /**
         * 获取 存储已用
         *
         * @return storageUsed 存储已用
         */
        public String getStorageUsed() {
            return this.storageUsed;
        }

        /**
         * 设置 存储已用
         *
         * @param storageUsed 存储已用
         */
        public void setStorageUsed(String storageUsed) {
            this.storageUsed = storageUsed;
        }

        /**
         * 获取 存储剩余
         *
         * @return storageFree 存储剩余
         */
        public String getStorageFree() {
            return this.storageFree;
        }

        /**
         * 设置 存储剩余
         *
         * @param storageFree 存储剩余
         */
        public void setStorageFree(String storageFree) {
            this.storageFree = storageFree;
        }

        /**
         * 获取 存储使用率
         *
         * @return storageUseRate 存储使用率
         */
        public Double getStorageUseRate() {
            return this.storageUseRate;
        }

        /**
         * 设置 存储使用率
         *
         * @param storageUseRate 存储使用率
         */
        public void setStorageUseRate(Double storageUseRate) {
            this.storageUseRate = storageUseRate;
        }
    }

    /**
     * 网络信息类
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorNetworkInfo {

        /** 上行速率 */
        private String upLinkRate;

        /** 下行速率 */
        private String downLinkRate;

        /**
         * 获取 上行速率
         *
         * @return upLinkRate 上行速率
         */
        public String getUpLinkRate() {
            return this.upLinkRate;
        }

        /**
         * 设置 上行速率
         *
         * @param upLinkRate 上行速率
         */
        public void setUpLinkRate(String upLinkRate) {
            this.upLinkRate = upLinkRate;
        }

        /**
         * 获取 下行速率
         *
         * @return downLinkRate 下行速率
         */
        public String getDownLinkRate() {
            return this.downLinkRate;
        }

        /**
         * 设置 下行速率
         *
         * @param downLinkRate 下行速率
         */
        public void setDownLinkRate(String downLinkRate) {
            this.downLinkRate = downLinkRate;
        }
    }

    /**
     * 服务器信息类
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorServerInfo {

        /** 服务器名称 */
        private String serverName;

        /** 服务器操作系统 */
        private String serverOs;

        /** 服务器IP */
        private String serverIp;

        /** 服务器架构 */
        private String serverArchitecture;

        /**
         * 获取 服务器名称
         *
         * @return serverName 服务器名称
         */
        public String getServerName() {
            return this.serverName;
        }

        /**
         * 设置 服务器名称
         *
         * @param serverName 服务器名称
         */
        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        /**
         * 获取 服务器操作系统
         *
         * @return serverOs 服务器操作系统
         */
        public String getServerOs() {
            return this.serverOs;
        }

        /**
         * 设置 服务器操作系统
         *
         * @param serverOs 服务器操作系统
         */
        public void setServerOs(String serverOs) {
            this.serverOs = serverOs;
        }

        /**
         * 获取 服务器IP
         *
         * @return serverIp 服务器IP
         */
        public String getServerIp() {
            return this.serverIp;
        }

        /**
         * 设置 服务器IP
         *
         * @param serverIp 服务器IP
         */
        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }

        /**
         * 获取 服务器架构
         *
         * @return serverArchitecture 服务器架构
         */
        public String getServerArchitecture() {
            return this.serverArchitecture;
        }

        /**
         * 设置 服务器架构
         *
         * @param serverArchitecture 服务器架构
         */
        public void setServerArchitecture(String serverArchitecture) {
            this.serverArchitecture = serverArchitecture;
        }
    }

    /**
     * JVM信息类
     *
     * @author xuyuxiang
     * @date 2022/7/31 16:42
     */
    public static class DevMonitorJvmInfo {

        /** JVM名称 */
        private String jvmName;

        /** JVM版本 */
        private String jvmVersion;

        /** JVM总分配内存 */
        private String jvmMemoryTotal;

        /** JVM已用内存 */
        private String jvmMemoryUsed;

        /** JVM剩余内存 */
        private String jvmMemoryFree;

        /** JVM内存使用率 */
        private Double jvmUseRate;

        /** JVM启动时间 */
        private String jvmStartTime;

        /** JVM运行时长 */
        private String jvmRunTime;

        /** Java版本 */
        private String javaVersion;

        /** Java安装路径 */
        private String javaPath;

        /**
         * 获取 JVM名称
         *
         * @return jvmName JVM名称
         */
        public String getJvmName() {
            return this.jvmName;
        }

        /**
         * 设置 JVM名称
         *
         * @param jvmName JVM名称
         */
        public void setJvmName(String jvmName) {
            this.jvmName = jvmName;
        }

        /**
         * 获取 JVM版本
         *
         * @return jvmVersion JVM版本
         */
        public String getJvmVersion() {
            return this.jvmVersion;
        }

        /**
         * 设置 JVM版本
         *
         * @param jvmVersion JVM版本
         */
        public void setJvmVersion(String jvmVersion) {
            this.jvmVersion = jvmVersion;
        }

        /**
         * 获取 JVM总分配内存
         *
         * @return jvmMemoryTotal JVM总分配内存
         */
        public String getJvmMemoryTotal() {
            return this.jvmMemoryTotal;
        }

        /**
         * 设置 JVM总分配内存
         *
         * @param jvmMemoryTotal JVM总分配内存
         */
        public void setJvmMemoryTotal(String jvmMemoryTotal) {
            this.jvmMemoryTotal = jvmMemoryTotal;
        }

        /**
         * 获取 JVM已用内存
         *
         * @return jvmMemoryUsed JVM已用内存
         */
        public String getJvmMemoryUsed() {
            return this.jvmMemoryUsed;
        }

        /**
         * 设置 JVM已用内存
         *
         * @param jvmMemoryUsed JVM已用内存
         */
        public void setJvmMemoryUsed(String jvmMemoryUsed) {
            this.jvmMemoryUsed = jvmMemoryUsed;
        }

        /**
         * 获取 JVM剩余内存
         *
         * @return jvmMemoryFree JVM剩余内存
         */
        public String getJvmMemoryFree() {
            return this.jvmMemoryFree;
        }

        /**
         * 设置 JVM剩余内存
         *
         * @param jvmMemoryFree JVM剩余内存
         */
        public void setJvmMemoryFree(String jvmMemoryFree) {
            this.jvmMemoryFree = jvmMemoryFree;
        }

        /**
         * 获取 JVM内存使用率
         *
         * @return jvmUseRate JVM内存使用率
         */
        public Double getJvmUseRate() {
            return this.jvmUseRate;
        }

        /**
         * 设置 JVM内存使用率
         *
         * @param jvmUseRate JVM内存使用率
         */
        public void setJvmUseRate(Double jvmUseRate) {
            this.jvmUseRate = jvmUseRate;
        }

        /**
         * 获取 JVM启动时间
         *
         * @return jvmStartTime JVM启动时间
         */
        public String getJvmStartTime() {
            return this.jvmStartTime;
        }

        /**
         * 设置 JVM启动时间
         *
         * @param jvmStartTime JVM启动时间
         */
        public void setJvmStartTime(String jvmStartTime) {
            this.jvmStartTime = jvmStartTime;
        }

        /**
         * 获取 JVM运行时长
         *
         * @return jvmRunTime JVM运行时长
         */
        public String getJvmRunTime() {
            return this.jvmRunTime;
        }

        /**
         * 设置 JVM运行时长
         *
         * @param jvmRunTime JVM运行时长
         */
        public void setJvmRunTime(String jvmRunTime) {
            this.jvmRunTime = jvmRunTime;
        }

        /**
         * 获取 Java版本
         *
         * @return javaVersion Java版本
         */
        public String getJavaVersion() {
            return this.javaVersion;
        }

        /**
         * 设置 Java版本
         *
         * @param javaVersion Java版本
         */
        public void setJavaVersion(String javaVersion) {
            this.javaVersion = javaVersion;
        }

        /**
         * 获取 Java安装路径
         *
         * @return javaPath Java安装路径
         */
        public String getJavaPath() {
            return this.javaPath;
        }

        /**
         * 设置 Java安装路径
         *
         * @param javaPath Java安装路径
         */
        public void setJavaPath(String javaPath) {
            this.javaPath = javaPath;
        }
    }
}
