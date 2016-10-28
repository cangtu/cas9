package org.cangtu.cas9;

import org.junit.*;

import java.util.List;

/**
 * Created by CangTu on 16-10-25.
 * Test class Cas9
 */
public class Cas9Test {
    @Test
    public void testInstantiate() {
        String id = "NC_000001.11__10451__1";
        String seq = "aaccctaaccctaaccctcgCGG";
        Cas9 cas9 = new Cas9(id, seq);
        Assert.assertEquals(cas9.id, id);
        Assert.assertEquals(cas9.seq, seq.toUpperCase());
    }

    @Test
    public void testFromCsvLine() {
        String id = "NC_000001.11__10451__1";
        String seq = "aaccctaaccctaaccctcgCGG";
        String line = "NC_000001.11__10451__1,aaccctaaccctaaccctcgCGG\n";
        Cas9 cas9 = Cas9.fromCsvLine(line);
        Assert.assertEquals(cas9.id, id);
        Assert.assertEquals(cas9.seq, seq.toUpperCase());
    }

    @Test
    public void testReadCsv() {
        String id = "NC_000001.11__10451__1";
        String seq = "aaccctaaccctaaccctcgCGG";
        String fileUrl = "src/test/resources/cas9s-demo.csv";
        List<Cas9> cas9s = Cas9.readCsv(fileUrl);
        Assert.assertEquals(10, cas9s.size());
        Cas9 cas9 = cas9s.get(0);
        Assert.assertEquals(id, cas9.id);
        Assert.assertEquals(seq.toUpperCase(), cas9.seq);
    }
}
