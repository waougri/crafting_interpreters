package io.github.waougri;

import io.github.waougri.Expr.Binary;
import io.github.waougri.Expr.Grouping;
import io.github.waougri.Expr.Literal;
import io.github.waougri.Expr.Unary;

public class RPNPrinter implements Expr.Visitor<String> {

  String print(Expr expression) {
    return expression.accept(this);
  }

  @Override
  public String visitBinaryExpr(Binary expr) {
    // left right operator
    return expr.left.accept(this) + " "
        + expr.right.accept(this) + " "
        + expr.operator.lexeme;
  }

  @Override
  public String visitGroupingExpr(Grouping expr) {
    // Grouping just prints inner expression
    return expr.expression.accept(this);
  }

  @Override
  public String visitLiteralExpr(Literal expr) {
    if (expr.value == null)
      return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Unary expr) {
    // Unary in RPN: operand operator
    return expr.right.accept(this) + " " + expr.operator.lexeme;
  }
}
