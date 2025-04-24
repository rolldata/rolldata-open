package com.rolldata.core.interceptors;

import org.springframework.web.servlet.view.JstlView;

import java.io.File;
import java.util.Locale;

/**
 * @Title: HtmlResourceView
 * @Description: HtmlResourceView
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2025-02-08
 * @version: V1.0
 */
public class HtmlResourceView extends JstlView {
    @Override
    public boolean checkResource(Locale locale){
        File file=new File(this.getServletContext().getRealPath("/")+getUrl());
        return file.exists(); //判断页面是否存在
    }

}
