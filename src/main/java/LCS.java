import java.util.Comparator;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LCS {
    public static String lcs(String x, String y) {
        Node[][] nodes = createArray(x, y);

        IntStream.rangeClosed(0, x.length()).forEach(xIterator ->
                IntStream.rangeClosed(0, y.length())
                        .forEach(yIterator -> assignNode(x, y, nodes, xIterator, yIterator))
        );
        return createLCS(nodes, x, y);
    }

    private static Node[][] createArray(String x, String y) {
        return new Node[x.length() + 1][y.length() + 1];
    }

    private static void assignNode(String x, String y, Node[][] nodes, int xIterator, int yIterator) {
        if (isZeroRowOrColumn(xIterator, yIterator)) {
            assignEmptyNode(nodes, xIterator, yIterator);
        } else if (hasCommonLetter(x, y, xIterator, yIterator)) {
            assignNodeWithCommonLetter(x, nodes, xIterator, yIterator);
        } else {
            assignDefaultNode(nodes, xIterator, yIterator);
        }
    }

    private static boolean isZeroRowOrColumn(int xIterator, int yIterator) {
        return xIterator == 0 || yIterator == 0;
    }

    private static boolean hasCommonLetter(String x, String y, int xIterator, int yIterator) {
        return x.charAt(xIterator - 1) == y.charAt(yIterator - 1);
    }

    private static void assignDefaultNode(Node[][] nodes, int xIterator, int yIterator) {
        //get node from left or from above
        Coordinates leftNodeCoordinates = getLeftNodeCoordinates(new Coordinates(xIterator, yIterator));
        Coordinates upperNodeCoordinates = getUpperNodeCoordinates(new Coordinates(xIterator, yIterator));
        //find node with max value
        Node max = findMax(nodes[leftNodeCoordinates.x()][leftNodeCoordinates.y()],
                nodes[upperNodeCoordinates.x()][upperNodeCoordinates.y()]);
        //copy its value
        //point to that node
        nodes[xIterator][yIterator] = new Node(max.value(), max, new Coordinates(xIterator, yIterator), null);
    }

    private static void assignNodeWithCommonLetter(String x, Node[][] nodes, int xIterator, int yIterator) {
        //point to diagonal
        Coordinates diagonalCoordinates = getLeftDiagonalNodeCoordinates(new Coordinates(xIterator, yIterator));
        //getDiagonalNode
        Node diagonalNode = nodes[diagonalCoordinates.x()][diagonalCoordinates.y()];
        //take value from diagonal and add one to current node
        char commonLetter = x.charAt(xIterator - 1);
        nodes[xIterator][yIterator] = new Node(diagonalNode.value() + 1,
                diagonalNode,
                new Coordinates(xIterator, yIterator), commonLetter);
    }

    private static void assignEmptyNode(Node[][] nodes, int xIterator, int yIterator) {
        nodes[xIterator][yIterator] = Node.empty();
    }

    public static Coordinates getLeftDiagonalNodeCoordinates(Coordinates coordinates) {
        return new Coordinates(coordinates.x() - 1, coordinates.y() - 1);
    }

    public static Coordinates getLeftNodeCoordinates(Coordinates coordinates) {
        return new Coordinates(coordinates.x(), coordinates.y() - 1);
    }

    public static Coordinates getUpperNodeCoordinates(Coordinates coordinates) {
        return new Coordinates(coordinates.x() - 1, coordinates.y());
    }

    public static Node findMax(Node n1, Node n2) {
        return Stream.of(n1, n2)
                .max(Comparator.comparingInt(Node::value))
                .orElse(n1);
    }

    public static String createLCS(Node[][] matrix, String x, String y) {
        int lastX = matrix.length - 1;
        int lastY = matrix[0].length - 1;
        Node lastNode = matrix[lastX][lastY];
        BinaryOperator<StringBuilder> noOperator = (ctx1, ctx2) -> {
            throw new IllegalStateException("This should never be used");
        };

        return Stream.iterate(lastNode, n -> n.getPointer().isPresent(), Node::pointer)
                .reduce(new StringBuilder(), LCS::appendCommonLetter, noOperator)
                .reverse()
                .toString();
    }

    private static StringBuilder appendCommonLetter(StringBuilder builder, Node node) {
        node.getCommonLetter().ifPresent(builder::append);
        return builder;
    }
}

record Node(Integer value, Node pointer, Coordinates coordinates, Character commonLetter) {
    static Node empty() {
        return new Node(0, null, null, null);
    }

    public Optional<Node> getPointer() {
        return Optional.ofNullable(pointer);
    }

    public Optional<Character> getCommonLetter() {
        return Optional.ofNullable(commonLetter);
    }
}

record Coordinates(Integer x, Integer y) {
}