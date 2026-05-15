package ast;

import emitter.Emitter;

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

    /**
     * Compiles a number, loads its value into $v0
     * @param e the emitter to use
     */
    public void compile (Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
