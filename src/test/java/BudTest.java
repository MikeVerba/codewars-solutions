import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudTest {

    private static void testing(long start, long limit, String expected) {
        System.out.println("start: " + start);
        System.out.println("limit: " + limit);
        String actual = Bud.buddy(start, limit);
        System.out.println("Expect: " + expected);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNothing() {
        testing(2382, 3679, "Nothing");
    }
    @Test
    public void test1() {
        testing(381, 4318, "(1050 1925)");
    }
    @Test
    @Disabled
    public void test2() {
        testing(1071625, 1103735, "(1081184 1331967)");
    }
    @Test
    public void test3() {
        testing(1071625, 1103735, "(320, 441)");
    }



}