package cn.com.zhihetech.util.hibernate.param.and;


import cn.com.zhihetech.util.hibernate.param.SimpleParam;

/**
 * 且小于等于参数类
 * Created by ShenYunjie on 2015/11/13.
 */
public class AndLessAndEqParam extends SimpleParam {

    /**
     * @param key   属性名称
     * @param value 属性值
     */
    public AndLessAndEqParam(String key, Object value) {
        super(key, value);
    }

    @Override
    public String getTargetHQL() {
        return LOGIC_AND_KEY + " " + this.key + " <= :" + getPlaceholderTitle();
    }
}
