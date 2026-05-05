package ast;

/**
 * Definition for class Readln for ast, extends Statement and has a String var
 * @author Daniel Wu
 * @version 04/07/26
 */
public class Readln extends Statement
{
    private String var;

    /**
     * Constructor for Writeln assigns this.exp a value
     * @param var the value to assign
     * @postcondition this.exp equal to input exp
     */
    public Readln(String var)
    {
        this.var = var;
    }

    /**
     * Simple getter for String
     * @return var
     */
    public String getString()
    {
        return var;
    }
}
