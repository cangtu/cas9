package org.cangtu.cas9;

import static org.junit.Assert.assertEquals;
import org.junit.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by CangTu on 16-10-24.
 * Score类的测试
 */
public class ScoreTest {
    @Test
    public void testListProduct() {
        List<Double> lst = new ArrayList<>();
        lst.add(1.1d);
        lst.add(2.1d);
        lst.add(3.1d);
        assertEquals(7.161, Score.listProduct(lst), 0.0001);
    }

    @Test
    public void testNewRound() {
        assertEquals(98.123, Score.newRound(98.1234), 0.0001);
        assertEquals(98.124, Score.newRound(98.1235), 0.0001);
    }

    @Test
    public void testT1Part1() {
        Score score = new Score();
        int[] mmPosList = {0, 2, 5};
        assertEquals(0.59653, score.t1(mmPosList), 0.0001);
    }

    @Test
    public void testT1Part2() {
        Score score = new Score();
        int[] mmPosList = {2, 5};
        assertEquals(0.59653, score.t1(mmPosList), 0.0001);
    }

    @Test
    public void testT2Part1() {
        Score score = new Score();
        int[] mmPosList = {10};
        assertEquals(1.0, score.t2(mmPosList), 0.0001);
    }

    @Test
    public void testT2Part2() {
        Score score = new Score();
        int[] mmPosList = {11, 13, 15};
        assertEquals(0.21839080459770116, score.t2(mmPosList), 0.0001);
    }

    @Test
    public void testT2Part3() {
        Score score = new Score();
        int[] mmPosList = {0, 19};
        assertEquals(1d, score.t2(mmPosList), 0.0001);
    }

    @Test
    public void testT3() {
        Score score = new Score();
        assertEquals(0.25d, score.t3(2), 0.0001);
    }

    @Test
    public void testGetPart1() {
        Score score = new Score();
        int[] mmPosList = {};
        assertEquals(100d, score.get(mmPosList), 0.0001);
        int[] newMmPosList = {0};
        assertEquals(100d, score.get(newMmPosList), 0.0001);
    }

    @Test
    public void testGetPart2() {
        Score score = new Score();
        int[] mmPosList = {4, 6, 7, 11};
        assertEquals(0.466, score.get(mmPosList), 0.0001);
    }

    @Test
    public void testToMap() {
        Score score = new Score();
        Map<String, Double> map = score.toMap();
        assertEquals(6195, map.size());
        int[] mmPosList = {4, 6, 7, 11};
        assertEquals(score.get(mmPosList), map.get("4_6_7_11"), 0.0001);
    }
}
