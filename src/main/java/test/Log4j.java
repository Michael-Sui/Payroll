package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created by Michael on 2017/6/1.
 */
public class Log4j {
    @Test
    public void Log4jTest() {
        Logger LOG = LogManager.getLogger(Log4j.class);
    }
}
