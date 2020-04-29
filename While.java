package AST;

import Environment.Environment;

/**
 * The While class represents a while loop that continually
 * checks a Condition, and while that condition is true,
 * the enclosed Statements are executed.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class While extends Statement
{
    private Condition cond;
    private Statement state;

    /**
     * Constructor for objects of class While.
     *
     * @param c the Condition to be evaluated.
     * @param s the Statement to be executed.
     */
    public While(Condition c, Statement s)
    {
        cond = c;
        state = s;
    }

    /**
     * Executes a while loop based on whether its Condition is true.
     * If so, continually runs the Statement enclosed.
     * If false, exits this loop.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        while (cond.eval(env)>0)
        {
            state.exec(env);
        }
    }
}
