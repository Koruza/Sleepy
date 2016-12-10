package emily.sleepy.util;

/**
 * Created by kawo123 on 12/10/2016.
 */
public class Session {

    public long startTime;
    public long endTime;
    public long duration;

    public Session(long startTime, long endTime, long duration){
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

}
