import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextBiggerNumberTest {

    @Test
    public void basicTests() {
        assertEquals(21, NextBiggerNumber.nextBiggerNumber(12));
        assertEquals(531, NextBiggerNumber.nextBiggerNumber(513));
        assertEquals(2071, NextBiggerNumber.nextBiggerNumber(2017));
        assertEquals(441, NextBiggerNumber.nextBiggerNumber(414));
        assertEquals(414, NextBiggerNumber.nextBiggerNumber(144));
        assertEquals(19009, NextBiggerNumber.nextBiggerNumber(10990));
    }

}