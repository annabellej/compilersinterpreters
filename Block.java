package AST;

import Environment.Environment;

import java.util.List;

/**
 * The Block class represents a block of input denoted by
 * a BEGIN token and ending with an END token. A Block
 * includes a list of different other Statements.
 * Block is an instance of a Statement.
 *
 * @author Annabelle Ju
 * @version 03/27/2020
 */
public class Block extends Statement
{
    private List<Statement> statements;

    /**
     * Constructor for objects of class Block.
     *
     * @param l the list of Statements in this Block.
     */
    public Block(List l)
    {
        statements = l;
    }

    /**
     * Executes the Statement lines in this Block.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        for (Statement curState: statements)
        {
            curState.exec(env);
        }
    }
}
