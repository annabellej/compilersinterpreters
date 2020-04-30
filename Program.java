package AST;

import Environment.Environment;

import java.util.List;

/**
 * The Program class serves as the root of this abstract
 * syntax tree. It includes any number of ProcedureDeclarations
 * followed by a Statement and a period to end the Program.
 * Program is an instance of a Statement.
 *
 * @author Annabelle Ju
 * @version 04/17/2020
 */
public class Program extends Statement
{
    private List<ProcedureDeclaration> procedures;
    private Statement body;

    public Program(List l, Statement s)
    {
        procedures = l;
        body = s;
    }

    /**
     * Executes the ProcedureDeclarations and body Statement
     * of this Program.
     *
     * @param env the Environment in which to execute the Statement.
     */
    @Override
    public void exec(Environment env)
    {
        for (ProcedureDeclaration dec: procedures)
        {
            dec.exec(env);
        }
        if (body!=null)
        {
            body.exec(env);
        }
    }
}
