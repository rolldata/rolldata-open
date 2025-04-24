package com.rolldata.web.system.enums;

/**
 * @Title:DataBaseSortEnum
 * @Description:数据库排序枚举
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-12-03
 * @version:V1.0
 */
public enum DataBaseSortEnum {

    ASC, DESC;

    public static String getSort(String sort) {

        String defaul = DataBaseSortEnum.ASC.name();
        DataBaseSortEnum[] values = values();
        for (int i = 0; i < values.length; i++) {
            String name = values[i].name();
            if (name.equalsIgnoreCase(sort)) {
                return name;
            }
        }
        return defaul;
    }
}
