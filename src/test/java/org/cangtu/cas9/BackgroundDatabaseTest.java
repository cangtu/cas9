package org.cangtu.cas9;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by CangTu on 16-10-26.
 * 测试BackgroundDatabase类
 */
public class BackgroundDatabaseTest {
    @Test
    public void testCount() {
        String str1 = "NC_000006.12__143194256__-1__AAG;NC_000001.11__217642717__1__AAG";
        String str2 = "NC_000006.12__124529630__1__GAG";
        Assert.assertEquals(1, BackgroundDatabase.count(str1, ";"));
        Assert.assertEquals(0, BackgroundDatabase.count(str2, ";"));
    }

    @Test
    public void testLineToBd() {
        String line = "TTTCTCACCTgCCGCCATGT,NC_000006.12__143194256__-1__AAG;NC_000001.11__217642717__1__AAG\n";
        String s = "TTTCTCACCTgCCGCCATGT";
        int cnt = 2;
        BackgroundDatabase bd = BackgroundDatabase.fromCsvLine(line);
        Assert.assertEquals(s.toUpperCase(), bd.s);
        Assert.assertEquals(cnt, bd.cnt);
    }

    @Test
    public void testReadCsv() {
        String csvFileUrl = "src/test/resources/bd-demo.csv";
        List<BackgroundDatabase> bdList = BackgroundDatabase.readCsv(csvFileUrl);
        Assert.assertEquals(24, bdList.size());
        BackgroundDatabase bd = bdList.get(0);
        String s = "GATACTTAAATGCTGATGTG";
        int cnt = 7;
        Assert.assertEquals(s, bd.s);
        Assert.assertEquals(cnt, bd.cnt);
    }
}
