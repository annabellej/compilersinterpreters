package AST;

import Environment.Environment;

/**
 * The Writeln class represents a WRITELN statement that
 * prints different Expressions.
 * Writeln is an instance of a Statement.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class Writeln.
     *
     * @param e the expression that the Writeln statement will print at execution.
     */
    public Writeln(Expression e)
    {
        exp = e;
    }

    /**
     * Executes this WRITELN Statement by printing out
     * the enclosed Expression.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
