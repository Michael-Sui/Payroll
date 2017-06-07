package test;

import bean.PersonalInformation;
import org.junit.Test;
import utils.TimeCardState;
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

    @Test
    public void test2() {
        utils.Database database = new utils.Database();
        database.connect();
        PersonalInformation personalInformation = database.getPersonalInformation("1001");
        database.disconnect();
        assertEquals(personalInformation.getId(), "1001");
        assertEquals(personalInformation.getName(), "张三");
        assertEquals(personalInformation.getGender(), "male");
        assertEquals(personalInformation.getPhoneNumber(), "12366667799");
        assertEquals(personalInformation.getEmail(), "13800000000@qq.com");
        assertEquals(personalInformation.getAge(), 37);
        assertEquals(personalInformation.getAddress(), "北京市朝阳区");
        assertEquals(personalInformation.getPaymentMethod(), 0);
    }

    @Test
    public void test3() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setId("1001");
        personalInformation.setName("张三");
        personalInformation.setGender("male");
        personalInformation.setPhoneNumber("12366667799");
        personalInformation.setEmail("13800000000@qq.com");
        personalInformation.setAge(37);
        personalInformation.setAddress("北京市朝阳区");
        personalInformation.setPaymentMethod(0);
        utils.Database database = new utils.Database();
        database.connect();
        database.updatePersonalInformation(personalInformation);
        database.disconnect();
    }

    @Test
    public void test4() {
        TimeCardState timeCardState1 = null;
        TimeCardState timeCardState2 = null;
        utils.Database database = new utils.Database();
        database.connect();
        timeCardState1 = database.getTimeCardState("1001");
        timeCardState2 = database.getTimeCardState("0000");
        database.disconnect();
        assertEquals(timeCardState1.toString(), "OFF_DUTY");
        assertEquals(timeCardState2.toString(), "OFF_DUTY");
    }
}
