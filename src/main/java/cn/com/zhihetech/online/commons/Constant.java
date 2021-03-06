package cn.com.zhihetech.online.commons;

import java.io.Serializable;

/**
 * 系统常量
 * Created by ShenYunjie on 2015/11/17.
 */
public class Constant implements Serializable, Cloneable {

    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";    //默认日期格式
    public final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";    //默认日期时间格式

    /**
     * 商家自己推荐商品数量最大值
     */
    public final static int MERCHANT_RECOMMEND_MAX = 20;

    /**
     * 默认密码
     */
    public final static String DEFAULT_PASSWORD = "123456";

    /**
     * 数据分页相关，默认从第一页
     */
    public final static int DEFAULT_PAGE = 1;
    /**
     * 数据分页相关，默认每页数据显示条数（默认每页显示20条数据）
     */
    public final static int DEFAULT_ROWS = 20;

    /**
     * 商户、商品审核状态
     */
    public final static int EXAMINE_STATE_NOT_SUBMIT = 1;   //未提交审核
    public final static int EXAMINE_STATE_SUBMITED = 2;   //待审核
    public final static int EXAMINE_STATE_EXAMINED_OK = 3;   //已审核通过
    public final static int EXAMINE_STATE_EXAMINED1_NUOK = 4;   //已审核未通过

    /**
     * 活动审核状态
     */
    public final static int ACTIVITY_STATE_UNSBUMIT = 1; //未提交申请
    public final static int ACTIVITY_STATE_SBUMITED = 2; //已提交申请未审核
    public final static int ACTIVITY_STATE_EXAMINED_OK = 3; //已审核通过
    public final static int ACTIVITY_STATE_EXAMINED_NOT = 4; //已审核未通过
    public final static int ACTIVITY_STATE_STARTED = 5; //活动已开始
    public final static int ACTIVITY_STATE_FNISHED = 6; //活动已结束

    /**
     * 优惠券类型
     */
    public final static int COUPON_DISCOUNT_TYPE = 1;   //打折卷
    public final static int COUPON_VOUCHER_TYPE = 2;    //代金券

    /**
     * App端viewType相关
     */
    public final static int BANNER_VIEWTYPE_TARGET = 1; //跳转到viewTarget指定模块
    public final static int BANNER_VIEWTYPE_MERCHANT = 2;//跳转到商户页面
    public final static int BANNER_VIEWTYPE_GOODS = 3;//跳转到商品页面
    public final static int BANNER_VIEWTYPE_ACTIVITY = 4;//跳转到活动页面
    public final static int BANNER_VIEWTYPE_PAGE = 5;//跳转到指定页面
    /**
     * APP端viewTarget相关
     */
    public final static String BANNER_VIEWTARGET_DAILY_NEW = "1";//每日上新
    public final static String BANNER_VIEWTARGET_BIG_BRAND = "2";//大品牌
    public final static String BANNER_VIEWTARGET_BUG_KUMING = "3";//买昆明
    public final static String BANNER_VIEWTARGET_BUG_PREFECTURES = "4";//购地州
    public final static String BANNER_VIEWTARGET_TYPE_CATEGOR = "5";//类别
    public final static String BANNER_VIEWTARGET_TYPE_ACTIVITY_ZONE = "6";//活动专区
    /**
     * 验证码类型
     */
    public final static int SECURITY_REGISTER = 1;   //注册验证码
    public final static int SECURITY_ALTERPWD = 2; //修改密码验证码

    /**
     * 验证码有效期
     */
    public final static int VALIDITY_ONE_MINUTE = 60 * 1000;
    /**
     * 搜索类型相关
     */
    public final static int SEARCH_GOODS = 1;//商品
    public final static int SEARCH_MERCHANT = 2;//商家
    public final static String ENCODING = "UTF-8";

    /**
     * 用户类型，分为普通用户与买手用户
     */
    public final static int COMMON_USER = 1;    //普通用户
    public final static int BUYER_USER = 2; //买手用户

    /**
     * 轮播图所处的位置
     */
    public final static int BANNER_MAIN = 1;  //主页面上的轮播图
    public final static int BANNER_DAILY_NEW = 2;  //每日上新上的轮播图
    public final static int BANNER_BIG_BRAND = 3;  //大品牌上的轮播图
    public final static int BANNER_BUY_KUNMING = 4; //买昆明上的轮播图
    public final static int BANNER_BUY_PREFECTURES = 5; //购地州上的轮播图
    public final static int BANNER_TYPE_CATEGOR = 6;   //分类上的轮播图
    public final static int BANNER_TYPE_ACTIVITY_ZONE = 7; //活动专区上的轮播图

    public static final int ORDER_STATE_NO_SUBMIT = 1;  //订单未提交
    public static final int ORDER_STATE_NO_PAYMENT = 2;   //订单已提交，待支付
    public static final int ORDER_STATE_NO_DISPATCHER = 3;  //订单已经支付，等待发货
    public static final int ORDER_STATE_ALREADY_DISPATCHER = 4;  //订单已发货，等待确认收货
    public static final int ORDER_STATE_ALREADY_DELIVER = 5;   //订单已经确认收货
    public static final int ORDER_STATE_ALREADY_CANCEL = 6;  //订单已取消
}
