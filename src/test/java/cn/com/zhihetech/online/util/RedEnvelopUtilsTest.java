package cn.com.zhihetech.online.util;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by ShenYunjie on 2015/12/14.
 */
public class RedEnvelopUtilsTest extends TestCase {

    @Test
    public void testGetItemAmouts() throws Exception {
        float[] result = RedEnvelopUtils.getItemAmouts(100, 100);
        for(float f : result){
            System.out.println(f);
        }
    }
}