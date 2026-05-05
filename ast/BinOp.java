package ast;

/**
 * Definition for class BinOp for ast, extends Expression and has a String op, and
 * two Expressions: exp1 and exp2
 * @author Daniel Wu
 * @version 3/18/26
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for BinOp instantiates op, exp1, exp2 with input values
     * @param op value for this.op
     * @param exp1 Expression value for this.exp1
     * @param exp2 Expression value for this.exp2
     * @postcondition values for op, exp1, exp2 populated with input values
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Simple getOp method
     * @return op
     */
    public String getOp()
    {
        return op;
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
}