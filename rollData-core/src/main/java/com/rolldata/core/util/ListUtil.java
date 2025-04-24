package com.rolldata.core.util;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: ListUtil
 * @Description: List集合工具类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2023-08-08
 * @version: V1.0
 */
public class ListUtil {

    /**
     * 集合分页获取总页数
     * @param list
     * @param pageSize
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> int pageSum(List<T> list, int pageSize)throws Exception{
        int total = list.size();
        int pageSum = (total -1) / pageSize +1;
        return pageSum;
    }

    /**
     * 集合分页，根据每页条数和页数，返回结果
     * @param list
     * @param pageNo
     * @param pageSize
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> subList(List<T> list, int pageNo, int pageSize) throws Exception{
        List<T> subList = list.stream().skip((pageNo-1)*pageSize).limit(pageSize).
                collect(Collectors.toList());
//        List<T> listSort  = new ArrayList<>();
//        int size=list.size();
//        int pageStart=page==1?0:(page-1)*rows;//截取的开始位置
//        int pageEnd=size<page*rows?size:page*rows;//截取的结束位置
//        if(size>pageStart){
//            listSort =list.subList(pageStart, pageEnd);
//        }
//        //总页数
//        int totalPage=list.size()/rows;
//        return listSort;
        return subList;
    }
}
