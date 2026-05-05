package ast;

/**
 * While class represents an [FOR cond DO stmt] expression; cond is a Condition and stmt
 * is a Statement
 * @author Daniel Wu
 * @version 04/07/2026
 */
public class For extends Statement
{
    private Assignment assignment;
    private Number num;
    private Statement stmt;

    /**
     * Constructor for For, stores a Condition and a Statement
     * @param assignment the Assignment to store
     * @param num the Number to store
     * @param stmt the Statement to store
     * @postcondition cond and body are assigned values
     */
    public For(Assignment assignment, Number num, Statement stmt)
    {
        this.assignment = assignment;
        this.num = num;
        this.stmt = stmt;
    }

    /**
     * Simple getAssignment method
     * @return assignment
     */
    public Assignment getAssignment()
    {
        return assignment;
    }

    /**
     * Simple getNum method
     * @return num
     */
    public Number getNum()
    {
        return num;
    }

    /**
     * Simple getStmt method
     * @return stmt
     */
    public Statement getStmt()
    {
        return stmt;
    }
}
