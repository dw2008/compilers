package ast;

/**
 * Definition for class Number for ast, extends Expression and has an int value
 * @author Daniel Wu
 * @version 3/18/26
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructor for Number assigns value to value
     * @param value the value to be assigned
     * @postcondition this.value has a value
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Simple getValue method
     * @return value
     */
    public int getValue()
    {
        return value;
    }
}
