package bean;

import java.sql.Timestamp;

/**
 * Created by Michael on 2017/6/9.
 */
public class TimeCard {
    private String id;
    private Timestamp time;
    private int flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
