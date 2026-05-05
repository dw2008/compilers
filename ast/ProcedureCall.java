package ast;

import java.util.List;

/**
 * ProcedureCall represents a procedure call statement, has a procedure name
 * @author Daniel Wu
 * @version 04/21/26
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> args;

    /**
     * Constructor for ProcedureCall
     * @param name the string name
     * @param args the args to pass in
     */
    public ProcedureCall(String name, List<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    /**
     * Simple getName method
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Simple getArgs method
     * @return args
     */
    public List<Expression> getArgs()
    {
        return args;
    }
}