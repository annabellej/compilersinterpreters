package AST;

import Environment.Environment;

/**
 * The Condition class represents any condition
 * based on the six basic relational operators:
 * =, <>, <, >, <=, >=
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Condition extends Expression
{
    private Expression exp1;
    private String relop;
    private Expression exp2;

    /**
     * Constructor for objects of class Condition.
     *
     * @param e1 the lefthand expression to be evaluated.
     * @param ro the relational operator to be used.
     * @param e2 the righthand expression to be evaluated.
     */
    public Condition(Expression e1, String ro, Expression e2)
    {
        exp1 = e1;
        relop = ro;
        exp2 = e2;
    }

    /**
     * Evaluates this condition and sets it to be either true or false.
     *
     * @param env the Environment in which to evaluate the Expression.
     *
     * @return 0 if the Condition is false,
     *         1 if the Condition is true.
     */
    @Override
    public int eval(Environment env)
    {
        int bool = 0;

        switch (relop)
        {
            case "=":
            {
                if (exp1.eval(env) == exp2.eval(env)) bool = 1;
                break;
            }
            case "<>":
            {
                if (exp1.eval(env) != exp2.eval(env)) bool = 1;
                break;
            }
            case "<":
            {
                if (exp1.eval(env) < exp2.eval(env)) bool = 1;
                break;
            }
            case ">":
            {
                if (exp1.eval(env) > exp2.eval(env)) bool = 1;
                break;
            }
            case "<=":
            {
                if (exp1.eval(env) <= exp2.eval(env)) bool = 1;
                break;
            }
            case ">=":
            {
                if (exp1.eval(env) >= exp2.eval(env)) bool = 1;
                break;
            }
            default:
            {
                break;
            }
        }

        return bool;
    }
}
