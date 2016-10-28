package org.cangtu.cas9;

import org.junit.*;

import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void testConfig() {
        String configFileUrl = "src/test/resources/demo.conf";
        Map<String, String> configMap = App.config(configFileUrl);
        Assert.assertEquals(6, configMap.size());
        Assert.assertTrue(configMap.get("/data/got/9606/005").equals("/data/got/res/9606/005"));
    }
}
