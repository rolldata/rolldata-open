package com.rolldata.web.system.pojo;

import java.util.List;

/**
 * 生成编码规则,一般用于系统根据名称生成编码</br>
 * 继承后覆盖generateCode来扩展
 *
 * @Title:GenerateCodeRule
 * @Description:GenerateCodeRule
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2021-3-12
 * @version:V1.0
 */
public abstract class GenerateCodeRule {

    /**
     * 依据名称
     */
    private String name;

    /**
     * 当新生成的编号还是重复时,是否继续递归获取编码(内部使用)
     */
    protected Boolean isProceedReplace = Boolean.FALSE;

    /**
     * 英文字母数组
     */
    public static char[] chars = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'A', 'B','C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    // protected abstract String generateCode(String name, int codeLength);

    public abstract String generateCode(String name, DataCallback callback);

    public GenerateCodeRule() {}

    public GenerateCodeRule(String name) {
        this.name = name;
    }

    /**
     * 以后传参可加构造
     *
     * @param name
     * @param codeLength
     */
    public GenerateCodeRule(String name, int codeLength) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public GenerateCodeRule setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 回调
     */
    public static interface DataCallback {

        /**
         * 根据code,返回以code开头的所有字符串集合
         *
         * @param code
         * @return
         */
        List<String> queryFuzzyCode(String code);
    }
}
