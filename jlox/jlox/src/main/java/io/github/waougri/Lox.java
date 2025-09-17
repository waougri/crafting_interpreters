package io.github.waougri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.github.waougri.Expr.Literal;

public class Lox {
  static boolean hadError = false;

  public static void main(String[] args) throws IOException {
    // if (args.length > 1) {
    // System.out.println("Usage: jlox [script]");
    // System.exit(64);
    // } else if (args.length == 1) {
    // runFile(args[0]);
    // } else {
    // runPrompt();
    // }

    Expr expression = new Expr.Binary(
        new Expr.Unary(
            new Token(TokenType.MINUS, "-", null, 1),
            new Expr.Literal(123)),
        new Token(TokenType.STAR, "*", null, 1),
        new Expr.Grouping(
            new Expr.Literal(45.67)));

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

    System.out.println(new AstPrinter().print(expression));
    System.out.println(new RPNPrinter().print(challenge));
  }

  // fire up the interpreter
  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
    if (hadError)
      System.exit(65);
  }

  // repl
  private static void runPrompt() throws IOException {
    var input = new InputStreamReader(System.in);
    var reader = new BufferedReader(input);

    for (;;) {
      System.out.println("[jlox]> ");
      var line = reader.readLine();
      if (line == null)
        break;
      run(line);
    }
  }

  static void error(int line, String message) {
    report(line, "", message);
  }

  private static void report(int line, String where, String message) {
    System.err.println("[line " + line + "] Error " + where + ": " + message);
    hadError = true;
  }

  static void run(String sourec) {
    Scanner scanner = new Scanner(sourec);
    var toks = scanner.scanTokens();
    for (var tok : toks) {
      System.out.println(tok);
    }

  }
}
