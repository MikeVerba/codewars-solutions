import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LCSTest {
    @Test
    public void exampleTest1() {
        assertEquals("", LCS.lcs("a", "b"));
    }

    @Test
    public void exampleTest2() {
        assertEquals("abc", LCS.lcs("abcdef", "abc"));
    }
}