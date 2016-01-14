package cn.com.zhihetech.online.commons;

import java.io.Serializable;

/**
 * 七牛云存储相关配置
 * Created by ShenYunjie on 2015/11/19.
 */
public class QiniuConfig implements Serializable, Cloneable {
    public final static String AK = "qLRR5Sa-CIsik-6sm_WRX5R_HjuP9xBwhjnjRGmi";
    public final static String SK = "Tubr6q--abzz8fC-3GDv0eSngylt4lAwyybX6LR2";
    /**
     * 存储空间
     */
    public final static String BUCKET = "zhihetech";
    /**
     * 下载域名
     */
    public final static String DOMIN = "http://7xofn0.com1.z0.glb.clouddn.com/";
}
