import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {

    @Test
    public void example1() {
        final String input = "fox,bug,chicken,grass,sheep";
        final String[] expected = 	{
                "fox,bug,chicken,grass,sheep",
                "chicken eats bug",
                "fox eats chicken",
                "sheep eats grass",
                "fox eats sheep",
                "fox"};
        assertArrayEquals(expected, Dinglemouse.whoEatsWho(input));
    }

    @Test
    public void example2() {
        final String input = "bear,little-fish,big-fish,big-fish,big-fish";
        final String[] expected = 	{
                "fox,bug,chicken,grass,sheep",
                "chicken eats bug",
                "fox eats chicken",
                "sheep eats grass",
                "fox eats sheep",
                "fox"};
        String[] result = Dinglemouse.whoEatsWho(input);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expected, result);
    }

    @Test
    public void example3() {
        final String input = "busker, leaves, big-fish, bicycle";
        final String[] expected = 	{
                "fox,bug,chicken,grass,sheep",
                "chicken eats bug",
                "fox eats chicken",
                "sheep eats grass",
                "fox eats sheep",
                "fox"};
        assertArrayEquals(expected, Dinglemouse.whoEatsWho(input));
    }

    @Test
    public void example() {
        final String input = "cow, bear";
        final String[] expected = 	{
                "fox,bug,chicken,grass,sheep",
                "chicken eats bug",
                "fox eats chicken",
                "sheep eats grass",
                "fox eats sheep",
                "fox"};
        assertArrayEquals(expected, Dinglemouse.whoEatsWho(input));
    }

    @Test
    public void eatLeftSingle() {
        final String input = "chicken,fox,leaves,bug,grass,sheep";
        //chicken,fox,leaves,bug,grass,sheep
        //fox,leaves,bug,grass,sheep
        //fox,bug,grass,sheep
        //fox,bug,sheep


        final String[] expected = 	{
                "chicken,fox,leaves,bug,grass,sheep",
                "fox eats chicken",
                "bug eats leaves",
                "sheep eats grass",
                "fox,bug,sheep"};
        assertArrayEquals(expected, Dinglemouse.whoEatsWho(input));
    }

    @Test
    public void random40() {
        final String input = "cow,panda,bear,cow,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana";
        final String[] expected = 	{
                "cow,panda,bear,cow,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana",
                "bear eats cow",
                "cow,panda,bear,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana"};
        String[] result = Dinglemouse.whoEatsWho(input);
        System.out.println("RESULT:");
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expected, result);
    }
}

// bear, little-fish, big-fish, big-fish, big-fish
// busker, leaves, big-fish, bicycle
// cow, bear

//chicken,fox,leaves,bug,grass,sheep

//cow,panda,bear,cow,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana
//cow,panda,bear,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana
//cow,panda,bear,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana

//expected
//cow,panda,bear,banana,big-fish,lion,busker,lion,little-fish,bear,grass,banana


//bear eats cow