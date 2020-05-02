package AST;

import Environment.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * The ProcedureCall class represents a call to a Procedure,
 * denoted by name(); and executes the statement inside the given Procedure.
 * ProcedureCall is an instance of an Expression.
 *
 * @author Annabelle Ju
 * @version 04/17/2020
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> parameters;

    /**
     * Constructor for object sof class ProcedureCall.
     *
     * @param str the name of the Procedure to be executed.
     */
    public ProcedureCall(String str, List l)
    {
        name = str;
        parameters = l;
    }

    /**
     * Evaluates this Procedure by executing the corresponding
     * Statement within it.
     *
     * @param env the Environment in which to evaluate the Expression.
     */
    @Override
    public int eval(Environment env)
    {
        Environment local = new Environment(env);

        Statement procedure = env.getProcedure(name);
        ArrayList<Integer> arguments = new ArrayList<>();
        List<String> paramNames = env.getParamNames(name);

        for (Expression e: parameters)
        {
            arguments.add(e.eval(env));
        }

        local.setVariable(name, 0);
        for (int i = 0; i < arguments.size(); i++)
        {
            local.declareVariable(paramNames.get(i), arguments.get(i));
        }

        local.getProcedure(name).exec(local);
        return local.getVariable(name);
    }
}
