package aze.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void statusChanges_togglesCorrectly_success() {
        Deadline deadline = new Deadline("Submit report", "2024-11-30");
        deadline.markAsDone();
        assertEquals("[D][X] Submit report [LOW] (by: Nov 30 2024)", deadline.toString());
        deadline.markAsNotDone();
        assertEquals("[D][ ] Submit report [LOW] (by: Nov 30 2024)", deadline.toString());
    }

    @Test
    public void toString_validDateFormat_success() {
        Deadline deadline = new Deadline("Complete assignment", "2024-12-25");
        assertEquals("[D][ ] Complete assignment [LOW] (by: Dec 25 2024)", deadline.toString());
    }

    @Test
    public void deadline_invalidDateFormat_exceptionThrown() {
        try {
            new Deadline("Bad task", "25-12-2024");
            fail();
        } catch (Exception e) {
            // Expected exception due to invalid date format
        }
    }
}
