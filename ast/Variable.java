package ast;

import emitter.Emitter;

/**
 * Definition for class Variable for ast, extends Expression and has a String name
 * @author Daniel Wu
 * @version 3/18/26
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for Variable gives name a value
     * @param name the name to be stored
     * @postcondition name has a value
     */
    public Variable(String name)
    {
        this.name = name;
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
     * Compiles a variable
     * @param e the emitter to use
     */
    public void compile(Emitter e)
    {
        e.emit("la $t0 var" + name);
        e.emit("lw $v0 ($t0) #puts value of variable in $v0");
    }
}
