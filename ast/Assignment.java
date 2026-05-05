package ast;

/**
 * Definition for class Assignment for ast, extends Statement and has String var, Expression exp
 * @author Daniel Wu
 * @version 3/18/26
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructor for Assignment, assigns values to var and exp
     * @param var the value for this.var
     * @param exp the value for this.exp
     * @postcondition var, exp equal to inputted values
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Simple getVar method
     * @return var
     */
    public String getVar()
    {
        return var;
    }

    /**
     * Simple getExp method
     * @return exp
     */
    public Expression getExp()
    {
        return exp;
    }
}