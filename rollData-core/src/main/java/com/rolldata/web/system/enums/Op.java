/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.rolldata.web.system.enums;

import com.rolldata.core.util.StringUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jacky.gao
 * @since 2016年11月22日
 */
public enum Op {
    GreatThen, EqualsGreatThen, LessThen, EqualsLessThen, Equals, NotEquals, In, NotIn, Like, NotLike, SQLEqual,
    SQLNotEquals, And, IsNull, IsNotNull, IsNullStr, IsNotNullStr, StartWith, EndWith;

    public static Op parse(String op) {
        op = op.trim();
        if (">".equals(op)) {
            return GreatThen;
        } else if (">=".equals(op)) {
            return EqualsGreatThen;
        } else if ("==".equals(op)) {
            return Equals;
        } else if ("<".equals(op)) {
            return LessThen;
        } else if ("<=".equals(op)) {
            return EqualsLessThen;
        } else if ("!=".equals(op)) {
            return NotEquals;
        } else if ("<>".equals(op)) {
            return SQLNotEquals;
        } else if ("in".equals(op)) {
            return In;
        } else if ("not in".equals(op) || "not  in".equals(op)) {
            return NotIn;
        } else if ("like".equals(op)) {
            return Like;
        } else if ("not like".equals(op)) {
            return NotLike;
        } else if ("=".equals(op)) {
            return SQLEqual;
        } else if ("is null".equals(op)) {
            return IsNull;
        } else if ("is not null".equals(op)) {
            return IsNotNull;
        } else if ("And".equals(op)) {
            return And;
        }else if ("=''".equals(op)) {
            return IsNullStr;
        }else if ("<>''".equals(op)) {
            return IsNotNullStr;
        }else if (
            "start with".equals(op)
            || "开始以".equals(op)) {
            return StartWith;
        }else if (
            "end with".equals(op)
            || "结束以".equals(op)) {
            return EndWith;
        }
        throw new RuntimeException("Unknow op :" + op);
    }

    /**
     * 判断SQL连接符有没有in
     */
    private static final Set<String> ins = new HashSet<>();
    static {
        ins.add("in");
        ins.add("not in");
        ins.add("not  in");
    }

    /**
     * 是不是：集合的关键字
     *
     * @param op 前台配置的条件符
     * @return
     */
    public static boolean isList(String op) {

        if (StringUtil.isEmpty(op)) {
            return false;
        }
        return ins.contains(op.trim());
    }

    /**
     * 获取符号对应的中文
     *
     * @param op 前台配置的条件符
     * @return 显示的中文
     */
    public String symbolConversion(String op) {

        if (">".equals(op)) {
            return "大于";
        } else if (">=".equals(op)) {
            return "大于或等于";
        } else if ("=".equals(op) || "==".equals(op)) {
            return "等于";
        } else if ("<".equals(op)) {
            return "小于";
        } else if ("<=".equals(op)) {
            return "小于或等于";
        } else if ("!=".equals(op) || "<>".equals(op)) {
            return "不等于";
        } else if ("in".equals(op)) {
            return "包含";
        } else if ("not in".equals(op) || "not  in".equals(op)) {
            return "不包含";
        } else if ("开始以".equals(op)) {
            return "开始以";
        } else if ("结束以".equals(op)) {
            return "结束以";
        } else if ("is null".equals(op)) {
            return "是空值(null)";
        } else if ("is not null".equals(op)) {
            return "不是空值(null)";
        } else if ("=''".equals(op)) {
            return "是空的('')";
        } else if ("<>''".equals(op)) {
            return "不是空的('')";
        }
        throw new RuntimeException("Unknow op :" + op);
    }

    @Override
    public String toString() {
        switch (this) {
            case GreatThen:
                return ">";
            case EqualsGreatThen:
                return ">=";
            case LessThen:
                return "<";
            case EqualsLessThen:
                return "<=";
            case Equals:
                return "==";
            case NotEquals:
                return "!=";
            case In:
                return " in ";
            case NotIn:
                return " not in ";
            case Like:
            case StartWith:
            case EndWith:
                return " like ";
            case NotLike:
                return " not like ";
            case SQLEqual:
                return " = ";
            case SQLNotEquals:
                return " <> ";
            case IsNull:
                return "is null";
            case IsNotNull:
                return "is not null";
            case And:
                return " and ";
            case IsNullStr:
                return " ='' ";
            case IsNotNullStr:
                return " <>'' ";
        }
        return super.toString();
    }
}
