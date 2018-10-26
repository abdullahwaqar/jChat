package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class UniqueIdentifier {

    private static List<Integer> ids = new ArrayList<Integer>();
    private static final int RANGE_LIMIT = 100;

    private static int index = 0;

    static {
        for (int i = 0; i < RANGE_LIMIT; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);
    }

    private UniqueIdentifier() {}

    public static int getIdentifier() {
        if (index > ids.size() - 1)
            index = 0;
        return ids.get(index++);
    }
}