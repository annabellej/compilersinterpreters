package AST;

import Environment.Environment;

/**
 * The Assignment class represents a variable assignment,
 * assigning a variable with a String name to and Expression.
 * Assignment is an instance of a Statement.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructor for objects of class Assignment.
     *
     * @param s the String variable to be assigned.
     * @param e the Expression to be assigned to the variable.
     */
    public Assignment(String s, Expression e)
    {
        exp = e;
        var = s;
    }

    /**
     * Executes this Assignment by setting the value of
     * the variable to the Expression.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}
