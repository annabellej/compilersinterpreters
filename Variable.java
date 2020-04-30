package AST;

import Environment.Environment;

/**
 * The Variable class represents a variable.
 * Variables are an instance of an Expression.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for objects of class Variable.
     *
     * @param s the string name of this Variable.
     */
    public Variable(String s)
    {
        name = s;
    }

    /**
     * Evaluates this Variable and finds its value.
     *
     * @param env the Environment in which to evaluate the Expression.
     *
     * @return the value of this Variable
     */
    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
