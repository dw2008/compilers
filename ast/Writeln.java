package ast;

/**
 * Definition for class Writeln for ast, extends Statement and has an Expression exp
 * @author Daniel Wu
 * @version 3/18/26
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for Writeln assigns this.exp a value
     * @param exp the value to assign
     * @postcondition this.exp equal to input exp
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
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