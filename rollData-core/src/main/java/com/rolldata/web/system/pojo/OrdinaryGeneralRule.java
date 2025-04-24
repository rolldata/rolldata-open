package com.rolldata.web.system.pojo;

import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import com.rolldata.web.system.util.PinyinUtil;

import java.util.List;
import java.util.Random;

/**
 * 通用生成规则(通过生成职务编码演变而来)
 *
 * @Title: OrdinaryGeneralRule
 * @Description: OrdinaryGeneralRule
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-03-12
 * @version: V1.0
 */
public class OrdinaryGeneralRule extends GenerateCodeRule {


    /**
     * 默认最大
     */
    private int defalutMax = 99_999;

    /**
     * 默认编号开始字母的长度,不够则随机补齐字母
     */
    private int defaultPrefixLength = 10;

    private final Random random = new Random();

    @Override
    public String generateCode(String name, DataCallback callback) {

        // 编号前缀
        StringBuilder codePrefix = new StringBuilder();

        // 编号后缀
        String codeSuffix = "";

        // 控制编号最长阈值,如果到达此长度编码还重复时,则UUID生成
        int maxLength = 10;

        if (StringUtil.isEmpty(name)
            || this.defalutMax == 0) {

            // 名称为空,或者后缀编码长度等于0时,直接UUID生成,从12开始截取20个
            return UUIDGenerator.generate().substring(12);
        }
        int nameLength = name.length();
        if (this.defaultPrefixLength > nameLength) {

            // 随机补齐字母,整体长度到5位
            StringBuilder nameBuilder = new StringBuilder(name);
            for (int i = 0; i < (this.defaultPrefixLength - nameLength); i++) {
                nameBuilder.append(chars[random.nextInt(chars.length)]);
            }
            name = nameBuilder.toString();
        }
        if (!this.isProceedReplace) {
            String[] names = PinyinUtil.stringToPinyin(name, "");
            for (int i = 0; i < this.defaultPrefixLength; i++) {
                codePrefix.append(names[i].charAt(0));
            }
        } else {
            codePrefix = new StringBuilder(name);
        }
        List<String> repeatCodes = null;
        if (null != callback) {

            // 一般是去库里模糊匹配("codePrefix%")查询
            repeatCodes = callback.queryFuzzyCode(codePrefix.toString());
        }
        int oldMax = 1;
        if (null != repeatCodes &&
            !repeatCodes.isEmpty()) {
            for (String repeatCode : repeatCodes) {

                // 转换成数字
                try {
                    if (repeatCode.length() != this.defaultPrefixLength) {
                        int tNum = Integer.parseInt(repeatCode.substring(this.defaultPrefixLength));
                        if (tNum > oldMax) {
                            oldMax = tNum;
                        }
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        // 超过[编号后缀]的最大值阈值
        if (oldMax >= this.defalutMax) {
            this.isProceedReplace = Boolean.TRUE;

            // 最大阈值随之更改
            this.defalutMax = this.defalutMax - ((this.defalutMax + 1) / 10) * 9;
            codePrefix.append(chars[random.nextInt(chars.length)]);
            maxLength--;
            this.defaultPrefixLength++;
            return generateCode(codePrefix.toString(), callback);
        }

        // 编码加一操作
        oldMax++;
        codeSuffix = StringUtil.frontCompWithZore(oldMax, String.valueOf(maxLength));
        return codePrefix.append(codeSuffix).toString();
    }
}
