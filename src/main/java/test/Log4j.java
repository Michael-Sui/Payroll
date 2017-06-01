package test;

import org.junit.Test;

/**
 * Created by Michael on 2017/6/1.
 */
public class Log4j {
    @Test
    public void test() {
        org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(Log4j.class);
    }
}
