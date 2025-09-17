package io.github.waougri;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ChapterFiveChallengesTest {

  @Test
  void rpnPrinterShouldBeCorrect() {

    Expr challenge = new Expr.Binary(

        // left side: (1 + 2)
        new Expr.Grouping(
            new Expr.Binary(
                new Expr.Literal(1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Expr.Literal(2))),

        // operator: *
        new Token(TokenType.STAR, "*", null, 1),

        // right side: (4 - 3)
        new Expr.Grouping(
            new Expr.Binary(
                new Expr.Literal(4),
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(3))));

    assertEquals(new RPNPrinter().print(challenge), "1 2 + 4 3 - *");

  }
}
