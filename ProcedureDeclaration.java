package AST;

import Environment.Environment;

import java.util.List;

/**
 * The ProcedureDeclaration class represents the instigation
 * of a Procedure, denoted by PROCEDURE name(); and including
 * a single statement as its contents.
 * ProcedureDeclaration is an instance of a Statement.
 *
 * @author Annabelle Ju
 * @version 04/17/2020
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement contents;
    private List<String> parameters;

    /**
     * Constructor for objects of class ProcedureDeclaration.
     *
     * @param s the statement carried by this Procedure.
     */
    public ProcedureDeclaration(String str, Statement s, List l)
    {
        name = str;
        contents = s;
        parameters = l;
    }

    /**
     * Executes this ProcedureDeclaration by setting the name of
     * the Procedure to its corresponding Statement.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        env.setProcedure(name, contents);
        env.setParamNames(name, parameters);
    }
}
