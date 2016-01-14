package cn.com.zhihetech.online.commons;

import java.io.Serializable;

/**
 * 返回状态码
 * Created by ShenYunjie on 2015/11/18.
 */
public class ResponseStatusCode implements Serializable, Cloneable {
    /**
     * 正常返回
     */
    public final static int SUCCESS_CODE = 200;
    /**
     * 系统内部错误
     */
    public final static int SYSTEM_ERROR_CODE = 500;
    /**
     * 页面未找到
     */
    public final static int PAGE_NOT_FOUND_CODE = 404;

    /**
     * 未授权
     */
    public final static int UNAUTHORIZED = 401;


    /**
     * 商品已经下架
     *
     */
    public final static int NOT_ONSAL = 610;

    /**
     * 商品卖完了
     */
    public final static int NO_STOCK = 620;

    /**
     * 已经关注（收藏过）
     */
    public final static int ALREADY_FOCUS = 710;

    /**
     * 未关注（收藏过）
     */
    public final static int HAVE_NO_FOCUS = 715;

    /**
     *关注（收藏）为空
     */
    public final static int NOT_FOCUS = 720;


}
