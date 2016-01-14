package cn.com.zhihetech.online.util;

import cn.com.zhihetech.online.bean.SerializableAndCloneable;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 红包分配算法
 * Created by ShenYunjie on 2015/12/14.
 */
public class RedEnvelopUtils extends SerializableAndCloneable {
    public final static int YUAN_FEN_UNIT = 100;    //人名币元与分的转换率（1元=100分）
    private final static String FLOAT_FORMAT = "#.00"; //人名币元，精确到分

    /**
     * 根据红包租金额和红包个数随机分配每份红包金额
     *
     * @param totalMoney  红包总金额
     * @param totalPeople 红包个数
     * @return
     */
    public static float[] getItemAmouts(float totalMoney, int totalPeople) {
        Random random = new Random();
        int totalFen = (int) (formatNumber(totalMoney) * YUAN_FEN_UNIT);
        int[] resultFen = new int[totalPeople];
        float[] result = new float[totalPeople];
        for (int i = 0; i < totalPeople; i++) {
            resultFen[i] = random.nextInt(totalFen);
            result[i] = formatNumber(resultFen[i] / YUAN_FEN_UNIT);
            totalFen -= resultFen[i];
        }
        return result;
    }

    /**
     * 格式化数据，保留小数点后两位（3.1415926格式化为：3.14），多余部分直接舍去
     *
     * @param target
     * @return
     */
    private static float formatNumber(float target) {
        DecimalFormat format = new DecimalFormat(FLOAT_FORMAT);
        return Float.parseFloat(format.format(target));
    }
}
