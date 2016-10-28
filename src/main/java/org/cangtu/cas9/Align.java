package org.cangtu.cas9;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CangTu on 16-10-25.
 * Align类专门用来比对Cas9和BackgroundDatabase
 */
public class Align {
    private Map<String, Double> map;
    public Align() {
        map = new Score().toMap();
    }

    public static List<String> convert(List<Integer> mmPosList) {
        List<String> mmPosStringList = new ArrayList<>(4);
        for (int mmPos : mmPosList) {
            mmPosStringList.add(String.valueOf(mmPos));
        }
        return mmPosStringList;
    }

    public double align(Cas9 cas9, BackgroundDatabase bd) {
        int mmCnt = 0;
        List<Integer> mmPosList = new ArrayList<>(4);
        for (int i=0; i<20; i++) {
            if (cas9.seq.charAt(i) != bd.s.charAt(i)) {
                mmCnt++;
                if (mmCnt > 4) {
                    return -1d;
                }
                mmPosList.add(i);
            }
        }
        if (mmCnt == 0) {
            return 100.0;
        } else {
            return map.get(String.join("_", convert(mmPosList)));
        }
    }

    public double batchAlign(Cas9 cas9, List<BackgroundDatabase> bdList) {
        double score = 0d;
        for (BackgroundDatabase bd : bdList) {
            double alignRes = align(cas9, bd);
            if (alignRes >= 0) {
                score += alignRes*bd.cnt;
            }
        }
        return score;
    }
}
