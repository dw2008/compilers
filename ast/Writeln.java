package ast;

import emitter.Emitter;

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

    /**
     * Compiles a Writeln, loads print instructions for an exp
     * @param e the emitter to use
     */
    public void compile (Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("li $v0 11   #print character");
        e.emit("li $a0 0xA  #load ASCII code for new line");
        e.emit("syscall");
    }
}