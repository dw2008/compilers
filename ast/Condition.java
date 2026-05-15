package ast;

import emitter.Emitter;

/**
 * Condition class represents a conditional in an IF statement, has two expressions
 * and a relop (can be =, >, <, >=, <=, <>)
 * @author Daniel Wu
 * @version 3/20/26
 */
public class Condition extends Expression
{
    private Expression exp1;
    private Expression exp2;
    private String relop;

    public Condition(Expression exp1, Expression exp2, String relop)
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.relop = relop;
    }

    /**
     * Simple getExp1 method
     * @return exp1
     */
    public Expression getExp1()
    {
        return exp1;
    }

    /**
     * Simple getExp2 method
     * @return exp2
     */
    public Expression getExp2()
    {
        return exp2;
    }

    /**
     * Simple getRelop method
     * @return relop
     */
    public String getRelop()
    {
        return relop;
    }

    /**
     * Compiles a condition
     * @param e the emitter to use
     */
    public void compile(Emitter e, String targetLabel)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        switch(relop)
        {
            case "=":
                e.emit("bne $t0, $v0, " + targetLabel);
                break;
            case "<>":
                e.emit("beq $t0, $v0, " + targetLabel);
                break;
            case ">":
                e.emit("ble $t0, $v0, " + targetLabel);
                break;
            case "<":
                e.emit("bge $t0, $v0, " + targetLabel);
                break;
            case ">=":
                e.emit("blt $t0, $v0, " + targetLabel);
                break;
            case "<=":
                e.emit("bgt $t0, $v0, " + targetLabel);
                break;
        }
    }
}
