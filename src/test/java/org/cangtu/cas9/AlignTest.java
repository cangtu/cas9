package org.cangtu.cas9;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CangTu on 16-10-26.
 * 测试Align类
 */
public class AlignTest {
    @Test
    public void testConvert() {
        List<Integer> mmPosList = new ArrayList<>(4);
        mmPosList.add(1); mmPosList.add(2); mmPosList.add(3); mmPosList.add(4);
        List<String> mmPosStringList = new ArrayList<>(4);
        mmPosStringList.add("1"); mmPosStringList.add("2"); mmPosStringList.add("3"); mmPosStringList.add("4");

        List<String> res = Align.convert(mmPosList);
        for (int i=0; i<mmPosList.size(); i++) {
            Assert.assertTrue(res.get(i).equals(mmPosStringList.get(i)));
        }
    }

    @Test
    public void testAlign0() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "AAAAAAAAAAAAAAAAAAAAAAA");
        BackgroundDatabase bd = new BackgroundDatabase("CCCCCCCCCCCCCCCCCCCCC", 1);
        Assert.assertEquals(-1d, align.align(cas9, bd), 0.0001);
    }

    @Test
    public void testAlign1() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "AAAAAAAAAAAAAAAAAAAAAAA");
        BackgroundDatabase bd = new BackgroundDatabase("AAAAAAAAAAAAAAAAAAAA", 1);
        Assert.assertEquals(100.0, align.align(cas9, bd), 0.00001);
    }

    @Test
    public void testAlign2() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "ATGTGGCTCAGGGTGGCTAAGGG");
        BackgroundDatabase bd = new BackgroundDatabase("ATGTGGCTCAGGGTGGATAA", 1);
        Assert.assertEquals(38.5, align.align(cas9, bd), 0.00001);
    }

    @Test
    public void testAlign3() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "ATGTGGCTCAGGGTGGCTAAGGG");
        BackgroundDatabase bd = new BackgroundDatabase("ATGTGGCACAGGGTGGGTAA", 1);
        Assert.assertEquals(3.1, align.align(cas9, bd), 0.00001);
    }

    @Test
    public void testAlign4() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "ATGTGGCTCAGGGTGGCTAAGGG");
        BackgroundDatabase bd = new BackgroundDatabase("AGGAGGCACAGGGTGGCTAA", 1);
        Assert.assertEquals(2.544, align.align(cas9, bd), 0.00001);
    }

    @Test
    public void testAlign5() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "ATGTGGCTCAGGGTGGCTAAGGG");
        BackgroundDatabase bd = new BackgroundDatabase("CGGGGGCCCAGGGTGGCTAA", 1);
        Assert.assertEquals(1.386, align.align(cas9, bd), 0.00001);
    }

    @Test
    public void testBatchAlign() {
        Align align = new Align();
        Cas9 cas9 = new Cas9("foo", "ATGTGGCTCAGGGTGGCTAAGGG");
        List<BackgroundDatabase> bdList = new ArrayList<>(6);
        BackgroundDatabase bd0 = new BackgroundDatabase("CCCCCCCCCCCCCCCCCCCC", 1);
        BackgroundDatabase bd1 = new BackgroundDatabase("ATGTGGCTCAGGGTGGCTAA", 2);
        BackgroundDatabase bd2 = new BackgroundDatabase("ATGTGGCTCAGGGTGGATAA", 1);
        BackgroundDatabase bd3 = new BackgroundDatabase("ATGTGGCACAGGGTGGGTAA", 3);
        BackgroundDatabase bd4 = new BackgroundDatabase("AGGAGGCACAGGGTGGCTAA", 1);
        BackgroundDatabase bd5 = new BackgroundDatabase("CGGGGGCCCAGGGTGGCTAA", 4);
        bdList.add(bd0);
        bdList.add(bd1);
        bdList.add(bd2);
        bdList.add(bd3);
        bdList.add(bd4);
        bdList.add(bd5);
        double alignRes = align.batchAlign(cas9, bdList);
        Assert.assertEquals(255.888, alignRes, 0.00001);
    }
}
