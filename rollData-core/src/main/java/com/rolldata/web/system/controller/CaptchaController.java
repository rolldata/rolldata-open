package com.rolldata.web.system.controller;

import com.google.code.kaptcha.Producer;
import com.rolldata.core.util.CacheUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = "/static/images")
public class CaptchaController {

    private final Logger log = LogManager.getLogger(CaptchaController.class);

    private Producer captchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    /**
     * 获取验证码图片
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/kaptcha.jpg")
    public ModelAndView getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        this.log.info("获取验证码图片");
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 判断是否新打开的浏览用户
        String uuid = (String) session.getAttribute("ROLLDATA_UUID");
        if (!StringUtil.isNotEmpty(uuid)) {
            uuid = UUIDGenerator.generate();
        }
        session.setAttribute("ROLLDATA_UUID", uuid);

        // 生成验证码文本
        List<String> capText = createText();
        CacheUtils.put("ROLLDATA_PICCODE" + uuid, capText.get(1));

        // 利用生成的字符串构建图片
        BufferedImage bi = this.captchaProducer.createImage(capText.get(0));
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    private List<String> createText() {
        int intFirst, intSec, intTemp, validCodeResult;
        String validCode = null;
        Random rand = new Random();
        intFirst = (int) (Math.random() * 10);
        intSec = (int) (Math.random() * 10);
        switch (rand.nextInt(3)) {
            case 0:
                if (intFirst < intSec) {
                    intTemp = intFirst;
                    intFirst = intSec;
                    intSec = intTemp;
                }
                validCode = intFirst + "-" + intSec + "=?";
                validCodeResult = intFirst - intSec;
                break;
            case 1:
                validCode = intFirst + "+" + intSec + "=?";
                validCodeResult = intFirst + intSec;
                break;
            default:
                validCode = intFirst + "*" + intSec + "=?";
                validCodeResult = intFirst * intSec;
                break;
        }
        List<String> list = new ArrayList<String>();
        list.add(validCode);
        list.add(String.valueOf(validCodeResult));
        return list;
    }
}
