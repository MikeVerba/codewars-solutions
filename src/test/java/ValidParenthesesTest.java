import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidParenthesesTest {
    @Test
    public void sampleTest1() {
        // assertEquals("expected", "actual");
        assertTrue(ValidParentheses.validParentheses("()"));
    }

    @Test
    public void sampleTest2() {
        // assertEquals("expected", "actual");
        assertFalse(ValidParentheses.validParentheses("())"));
    }

    @Test
    public void sampleTest3() {
        // assertEquals("expected", "actual");
        assertTrue(ValidParentheses.validParentheses("32423(sgsdg)"));
    }

    @Test
    public void sampleTest4() {
        // assertEquals("expected", "actual");
        assertFalse(ValidParentheses.validParentheses("(dsgdsg))2432"));
    }

    @Test
    public void sampleTest5() {
        // assertEquals("expected", "actual");
        assertTrue(ValidParentheses.validParentheses("adasdasfa"));
    }

    @Test
    public void sampleTest6() {
        // assertEquals("expected", "actual");
        assertFalse(ValidParentheses.validParentheses("())("));
    }
}