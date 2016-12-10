package emily.sleepy.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kawo123 on 12/10/2016.
 */
public class History {

    private static final List<Session> history = new ArrayList<Session>();

    private History(){
    }

    public static List<Session> getInstance(){
        return history;
    }

}
