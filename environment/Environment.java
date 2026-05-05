package environment;

import ast.ProcedureCall;
import ast.ProcedureDeclaration;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment keeps track of variables and their values
 * @author Daniel Wu
 * @version 03/20/26
 */
public class Environment
{
    private Map<String, Integer> vars;
    private Map<String, ProcedureDeclaration> procedures;
    private Environment parent;

    /**
     * Constructor for environment initializes vars
     * @postcondition vars is initialized
     */
    public Environment()
    {
        vars = new HashMap<>();
        procedures = new HashMap<>();
        parent = null;
    }

    public Environment(Environment parent)
    {
        vars = new HashMap<>();
        procedures = new HashMap<>();
        this.parent = parent;
    }

    private Environment getGlobal()
    {
        Environment current = this;
        while(current.getParent() != null)
        {
            current = current.getParent();
        }
        return current;
    }
    /**
     * declareVariable associates the given variable name with the given value in current env
     * @param variable the String value of the variable
     * @param value the int value of the variable's value
     * @postcondition variable value pair added to vars
     */
    public void declareVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    public void setVariable(String variable, int value)
    {
        if(vars.containsKey(variable))
        {
            vars.put(variable, value);
        }
        else
        {
            Environment global = getGlobal();
            if(global.getVars().containsKey(variable))
            {
                global.declareVariable(variable, value);
            }
            else
            {
                declareVariable(variable, value);
            }
        }
    }

    /**
     * getVariable returns the thing associated with the given variable (goes to parent if not found)
     * @param variable name of variable to get value
     * @return value associated with variable
     */
    public int getVariable(String variable)
    {
        if(vars.containsKey(variable))
        {
            return vars.get(variable);
        }
        if(parent != null)
        {
            return parent.getVariable(variable);
        }
        return 0;
    }

    /**
     * Simple getVars method
     * @return vars
     */
    public Map<String, Integer> getVars()
    {
        return vars;
    }

    /**
     * setProcedure associates the given variable name with the given value
     * @param variable the String value of the variable
     * @param procedure the int value of the variable's value
     * @postcondition variable value pair added to vars
     */
    public void setProcedure(String variable, ProcedureDeclaration procedure)
    {
        if(parent == null)
        {
            procedures.put(variable, procedure);
        }
        else
        {
            getGlobal().setProcedure(variable, procedure);
        }
    }

    /**
     * getProcedure returns the global procedure associated with the given variable
     * @param variable name of variable to get value
     * @return value associated with variable
     */
    public ProcedureDeclaration getProcedure(String variable)
    {
        if(parent == null)
        {
            return procedures.get(variable);
        }
        return getGlobal().getProcedure(variable);
    }

    /**
     * Simple getParent method
     * @return parent
     */
    public Environment getParent()
    {
        return parent;
    }
}
