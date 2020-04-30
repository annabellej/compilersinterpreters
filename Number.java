package AST;

import Environment.Environment;

/**
 * The Number class represents an integer value.
 * Number is an instance of an Expression object.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructor for objects of class Number.
     *
     * @param n the value of this integer Number.
     */
    public Number(Integer n)
    {
        value = n;
    }

    /**
     * Retrieves the integer value of this Number.
     *
     * @param env the Environment in which to evaluate the Expression.
     *
     * @return the integer value of this Number.
     */
    @Override
    public int eval(Environment env)
    {
        return value;
    }
}
