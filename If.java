package AST;

import Environment.Environment;

/**
 * The If class represents a conditional IF Statement
 * that evaluates a condition and executes a Statement
 * if the condition is true.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class If extends Statement
{
    private Condition cond;
    private Statement state;

    /**
     * Constructor for objects of class If.
     *
     * @param c the Condition to be evaluated.
     * @param s the Statement to be executed if c is true.
     */
    public If(Condition c, Statement s)
    {
        cond = c;
        state = s;
    }

    /**
     * Executes an If Statement based on whether its Condition
     * is true. If so, then the subsequent Statements will be executed.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        if (cond.eval(env) > 0)
        {
            state.exec(env);
        }
    }
}
