package org.cangtu.cas9;

import java.util.*;

import org.apache.commons.math3.util.CombinatoricsUtils;

/**
 * Created by CangTu on 16-10-24.
 * 计算比对得分的类
 */
public class Score {
    public double[] m;

    /**
     * 张峰实验室通过实验获得的每个位置错配的特异性，具体参考网页:
     * http://crispr.mit.edu/about
     */
    public Score() {
        m = new double[]{0d, 0d, 0.014d, 0d, 0d, 0.395d, 0.317d, 0d, 0.389d, 0.079d, 0.445d, 0.508d, 0.613d, 0.851d, 0.732d, 0.828d, 0.615d, 0.804d, 0.685d, 0.583d};
    }

    /**
     * 求一个列表的乘积的方法
     * @param lst: 列表
     * @return: 列表的乘积
     */
    public static double listProduct(List<Double> lst) {
        double product = 1;
        for (double e : lst) {
            product *= e;
        }
        return product;
    }

    /**
     * 保留double的三位有效数字
     * @param d: 双精度浮点型
     * @return: 保留三位有效数字的值
     */
    public static double newRound(double d) {
        return (double)Math.round(d * 1000)/1000;
    }

    /**
     * 公式第一部分的值
     * @param mmPosList: 错配位置的列表
     * @return: 第一部分的值
     */
    public double t1(int[] mmPosList) {
        List<Double> sp = new ArrayList<>();
        for (int i : mmPosList) {
            sp.add(1-m[i]);
        }
        return listProduct(sp);
    }

    /**
     * 公式第二部分的值
     * @param mmPosList: 错配的位置列表
     * @return: 第二部分的值
     */
    public double t2(int[] mmPosList) {
        int size = mmPosList.length;
        if (size == 1) {
            return 1d;
        } else {
            double mpd = 1.0 * (mmPosList[size-1] - mmPosList[0]) / (size - 1);
            return 1.0 / (((19.0 - mpd) / 19) * 4.0 + 1.0);
        }
    }

    /**
     * 公式第三部分的值
     * @param cnt: 错配碱基的个数
     * @return: 第三部分的值
     */
    public double t3(int cnt) {
        return 1.0 / Math.pow(cnt, 2);
    }

    public double get(int[] mmPosList) {
        int size = mmPosList.length;
        if (size == 0) {
            return 100.0;
        } else if (size == 1) {
            return newRound(100.0 * t1(mmPosList));
        } else {
            return newRound(100.0 * t1(mmPosList) * t2(mmPosList) * t3(size));
        }
    }

    public Map<String, Double> toMap() {
        Map<String, Double> map = new HashMap<>(6195);  // 总共有6195中组合
        int total = 20;
        for (int mmCnt=1; mmCnt<5; mmCnt++) {
            Iterator<int[]> mmPosListIterator = CombinatoricsUtils.combinationsIterator(total, mmCnt);

            int[] mmPosList;
            while(mmPosListIterator.hasNext()) {
                mmPosList = mmPosListIterator.next();
                List<String> mmPosStringList = new ArrayList<>();
                for (int mmPos : mmPosList) {
                    mmPosStringList.add(String.valueOf(mmPos));
                }
                map.put(String.join("_", mmPosStringList), get(mmPosList));
            }
        }
        return map;
    }
}
