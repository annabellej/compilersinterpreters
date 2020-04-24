package AST;

import Environment.Environment;

/**
 * The Expression class represents any code that
 * needs to be evaluated and performs passive actions/functions.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public abstract class Expression
{
    /**
     * The method "eval" evaluates any Expression
     * given an Environment.
     *
     * @param env the Environment in which to evaluate the Expression.
     */
    public abstract int eval(Environment env);
}
