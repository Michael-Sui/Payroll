package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import test.Database;

/**
 * Created by Michael on 2017/6/1.
 */
public class Test {
    public static void main(String[] args) {
        //Result result = JUnitCore.runClasses(Log4j.class);
        Result result = JUnitCore.runClasses(Database.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
