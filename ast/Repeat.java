package ast;

/**
 * Repeat class represents an [REPEAT body UNTIL cond] expression; cond is a Condition and stmt
 * is a Statement
 * @author Daniel Wu
 * @version 04/07/2026
 */
public class Repeat extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructor for Repeat, stores a Condition and a Statement
     * @param cond the Condition to store
     * @param stmt the Statement to store
     * @postcondition cond and body are assigned values
     */
    public Repeat(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Simple getCond method
     * @return cond
     */
    public Condition getCond()
    {
        return cond;
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
