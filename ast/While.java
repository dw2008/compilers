package ast;

/**
 * While class represents an [WHILE cond DO stmt] expression; cond is a Condition and stmt
 * is a Statement
 * @author Daniel Wu
 * @version 03/21/2026
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructor for While, stores a Condition and a Statement
     * @param cond the Condition to store
     * @param stmt the Statement to store
     * @postcondition cond and stmt are assigned values
     */
    public While(Condition cond, Statement stmt)
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
