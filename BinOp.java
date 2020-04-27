package AST;

import Environment.Environment;

/**
 * The BinOp class represents any binary operator such as
 * multiplication, division, addition, and subtraction.
 * BinOp is an instance of an Expression.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for objects of class BinOp.
     *
     * @param s  the string representation of the binary operator.
     * @param e1 the lefthand expression being operated on.
     * @param e2 the righthand expression being operated on.
     */
    public BinOp(String s, Expression e1, Expression e2)
    {
        op = s;
        exp1 = e1;
        exp2 = e2;
    }

    /**
     * Evaluates this binary operation by performing
     * the operator on the two Expressions.
     *
     * @param env the Environment in which to evaluate the Expression.
     *
     * @return the value of this binary operation Expression.
     */
    @Override
    public int eval(Environment env)
    {
        switch (op)
        {
            case "*":
            {
                return exp1.eval(env) * exp2.eval(env);
            }
            case "/":
            {
                return exp1.eval(env) / exp2.eval(env);
            }
            case "+":
            {
                return exp1.eval(env) + exp2.eval(env);
            }
            case "-":
            {
                return exp1.eval(env) - exp2.eval(env);
            }
            default:
            {
                return 0;
            }
        }
    }
}
