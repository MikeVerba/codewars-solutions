import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

public class BraceChecker {
    private static final Character NORMAL_OPENER = '(';
    private static final Character NORMAL_CLOSER = ')';
    private static final Character CURLY_OPENER = '{';
    private static final Character CURLY_CLOSER = '}';
    private static final Character SQUARE_OPENER = '[';
    private static final Character SQUARE_CLOSER = ']';
    public static final Set<ParenthesesTuple> PARENTHES_TUPLE_SET = Set.of(
            new ParenthesesTuple(NORMAL_OPENER, NORMAL_CLOSER),
            new ParenthesesTuple(CURLY_OPENER, CURLY_CLOSER),
            new ParenthesesTuple(SQUARE_OPENER, SQUARE_CLOSER));

    public boolean isValid(String parens) {
        BinaryOperator<SolutionContext> noOperator = (ctx1, ctx2) -> {
            throw new IllegalStateException("This should never be used");
        };

        SolutionContext context = parens.chars()
                .mapToObj(c -> (char) c)
                .reduce(SolutionContext.initial(),
                        (solutionContext, character) -> solutionContext.processCharacter(solutionContext, character),
                        noOperator);
        return Optional.ofNullable(context).filter(SolutionContext::isValid).isPresent();
    }

    record SolutionContext(Stack<Character> openedBraces,
                           AtomicReference<Boolean> shouldFail) {
        boolean isValid() {
            return openedBraces.isEmpty() &&
                    shouldFail.get().equals(false);
        }

        public static SolutionContext initial() {
            return new SolutionContext(new Stack<>(),
                    new AtomicReference<>(false));
        }

        public SolutionContext processCharacter(SolutionContext context, Character character) {
            if (isAnOpener(character)) {
                context.openedBraces.push(character);
                return new SolutionContext(context.openedBraces, context.shouldFail);
            } else if (isACloserPairedWithCurrentOpener(character)) {
                return closeParenthesesIfPossible(context);
            } else if (isACloser(character)) {
                context.shouldFail.set(true);
                return new SolutionContext(context.openedBraces, context.shouldFail);
            }
            return new SolutionContext(context.openedBraces, context.shouldFail);
        }

        private SolutionContext closeParenthesesIfPossible(SolutionContext context) {
            return Optional.of(context)
                    .filter(ctx -> !ctx.openedBraces.isEmpty())
                    .map(ctx -> {
                                ctx.openedBraces.pop();
                                return new SolutionContext(ctx.openedBraces, ctx.shouldFail);
                            }
                    )
                    .orElse(new SolutionContext(context.openedBraces, new AtomicReference<>(true)));
        }

        private boolean isACloserPairedWithCurrentOpener(Character character) {
            return isACloser(character) && isPairedWithCurrentOpener(character);
        }

        private boolean isPairedWithCurrentOpener(Character character) {
            return PARENTHES_TUPLE_SET.stream()
                    .filter(t -> t.closer.equals(character))
                    .filter(t -> !openedBraces.empty())
                    .anyMatch(t -> openedBraces.peek().equals(t.opener));
        }

        private boolean isAnOpener(Character character) {
            return PARENTHES_TUPLE_SET.stream()
                    .anyMatch(t -> t.opener.equals(character));
        }

        private boolean isACloser(Character character) {
            return PARENTHES_TUPLE_SET.stream()
                    .anyMatch(t -> t.closer.equals(character));
        }
    }

    record ParenthesesTuple(Character opener, Character closer) {
    }
}

