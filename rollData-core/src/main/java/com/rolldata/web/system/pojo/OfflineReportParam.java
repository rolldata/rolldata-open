package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: OfflineReportParam
 * @Description: OfflineReportParam
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2023-11-01
 * @version: V1.0
 */
public class OfflineReportParam implements Serializable {

    private static final long serialVersionUID = 2874167027668538853L;

    private String dataReportId;

    private List<ParameDefinition> parames;

    public String getDataReportId() {
        return dataReportId;
    }

    public OfflineReportParam setDataReportId(String dataReportId) {
        this.dataReportId = dataReportId;
        return this;
    }

    public List<ParameDefinition> getParames() {
        return parames;
    }

    public OfflineReportParam setParames(List<ParameDefinition> parames) {
        this.parames = parames;
        return this;
    }

    @Override
    public String toString() {
        return "OfflineReportParam{" +
                "dataReportId='" + dataReportId + '\'' +
                ", parames=" + parames +
                '}';
    }
}
