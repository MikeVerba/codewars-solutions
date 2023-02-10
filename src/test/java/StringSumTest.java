import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringSumTest {
    @Test
    void test1() {
        assertEquals("579", StringSum.sumStrings("123", "456"));
    }

    @Test
    void test2() {
        assertEquals("4108431021543841150431691711", StringSum.sumStrings("4108431021543841150230303240", "201388471"));
    }

    @Test
    void test3() {
        assertEquals("105385119967790", StringSum.sumStrings("27742115564584", "77643004403206"));
    }

    @Test
    void test4() {
        assertEquals("55618615192650589712", StringSum.sumStrings("055617837051314243077", "778141336346635"));
    }

}