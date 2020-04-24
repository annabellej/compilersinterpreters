package AST;

import Environment.Environment;

/**
 * The Statement class represents any code that
 * needs to be executed and performs a certain action/function.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public abstract class Statement
{
    /**
     * The method "exec" executes any Statement
     * given an Environment.
     *
     * @param env the Environment in which to execute the Statement.
     */
    public abstract void exec(Environment env);
}
