package test;

import org.junit.Test;
import utils.UserAuthority;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael on 2017/6/1.
 */
public class Database {
    @Test
    public void test() {
        utils.Database database = new utils.Database();
        database.connect();
        UserAuthority ua1 = database.isLogin("aaa", "aaa");
        assertEquals(ua1.toString(), "ERROR");
        UserAuthority ua2 = database.isLogin("1001", "1002");
        assertEquals(ua2.toString(), "ERROR");
        UserAuthority ua3 = database.isLogin("1001", "1001");
        assertEquals(ua3.toString(), "GUEST");
        UserAuthority ua4 = database.isLogin("1002", "1002");
        assertEquals(ua4.toString(), "ADMIN");
        database.disconnect();
    }
}
