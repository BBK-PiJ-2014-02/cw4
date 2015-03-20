package contactManager;

import java.util.Comparator;

/**
 * Comparator to check between two meeting dates.
 * 
 * @author Vasco
 *
 */
public class MeetingComparator implements Comparator<Meeting> {
    /**
     * Compare between two Meeting dates.
     */
    @Override
    public int compare(Meeting o1, Meeting o2) {
        if ( o1.getDate().after(o2.getDate()) ) return 1;
        if ( o1.getDate().before(o2.getDate()) ) return -1;
        return 0;
    }

}
