package Environment;

import AST.Statement;

import java.util.HashMap;
import java.util.List;

/**
 * The Environment class remembers the values of any given variables.
 *
 * @author Annabelle Ju
 * @version 04/17/2020
 */
public class Environment
{
    private Environment parent;
    private HashMap<String, Integer> variables;
    private HashMap<String, Statement> procedures;
    private HashMap<String, List<String>> paramNames;

    /**
     * Constructor for objects of class Environment.
     */
    public Environment(Environment parent)
    {
        this.parent = parent;
        variables =  new HashMap<>();
        procedures = new HashMap<>();
        paramNames = new HashMap<>();
    }

    /**
     * The method "setVariable" takes in a variable and sets its value,
     * Saves the variable and its value.
     *
     * @param variable the variable to be stored.
     * @param value    the value of the variable to be stored.
     */
    public void setVariable(String variable, int value)
    {
        if (parent!=null && !variables.containsKey(variable) && parent.variables.containsKey(variable))
        {
            parent.setVariable(variable, value);
        }
        else
        {
            variables.put(variable, value);
        }
    }

    /**
     * The method "getVariable" retrieves the value of a given variable.
     *
     * @param variable the variable name to be retrieved.
     *
     * @return the integer value of the given variable.
     */
    public int getVariable(String variable)
    {
        if (!variables.containsKey(variable))
        {
            return parent.getVariable(variable);
        }
        else
        {
            return variables.get(variable);
        }
    }

    /**
     * Takes in a variable and sets its value in the local environment.
     *
     * @param variable the variable to be declared.
     * @param value    the value of the variable to be declared.
     */
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * The method "setProcedure" takes in a Procedure's name
     * and puts a corresponding Statement into its contents.
     *
     * @param name the name of this Procedure.
     * @param st   the statement that this Procedure contains.
     */
    public void setProcedure(String name, Statement st)
    {
        if (parent!=null)
        {
            parent.setProcedure(name, st);
        }
        else
        {
            if (!procedures.containsKey(name))
            {
                procedures.put(name, st);
            }
            else
            {
                procedures.replace(name, st);
            }
        }
    }

    /**
     * The method "getProcedure" retrieves the Statement within
     * the given Procedure.
     *
     * @param name the name of the Procedure to be retrieved.
     *
     * @return the Statement within the given Procedure.
     */
    public Statement getProcedure(String name)
    {
        if (parent!=null)
        {
            return parent.getProcedure(name);
        }
        else
        {
            return procedures.get(name);
        }
    }

    /**
     * Takes in the String names of a Procedure's parameters.
     *
     * @param name the name of the Procedure.
     * @param params the list of parameters.
     */
    public void setParamNames(String name, List<String> params)
    {
        if (!paramNames.containsKey(name))
        {
            paramNames.put(name, params);
        }
        else
        {
            paramNames.replace(name, params);
        }
    }

    /**
     * The method "getParamNames" retrieves the list of parameter names
     * for this Procedure.
     *
     * @param name the name of the Procedure to be retrieved.
     *
     * @return the List of this Procedure's parameter names.
     */
    public List getParamNames(String name)
    {
        return paramNames.get(name);
    }
}
