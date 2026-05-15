package ast;

import emitter.Emitter;

/**
 * If class represents an [IF cond THEN stmt] expression; cond is a Condition and stmt
 * is a Statement
 * @author Daniel Wu
 * @version 04/07/2026
 */
public class If extends Statement
{
    private Condition cond;
    private Statement thenStmt;
    private Statement elseStmt;

    /**
     * Constructor for IF THEN, stores a Condition and a Statement
     * @param cond the Condition to store
     * @param thenStmt the Statement to store
     * @postcondition cond and stmt are assigned values
     */
    public If(Condition cond, Statement thenStmt)
    {
        this.cond = cond;
        this.thenStmt = thenStmt;
    }

    /**
     * Constructor for IF THEN ELSE, stores a Condition and a Statement
     * @param cond the Condition to store
     * @param thenStmt the Statement to store
     * @postcondition cond and stmt are assigned values
     */
    public If(Condition cond, Statement thenStmt, Statement elseStmt)
    {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
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
     * Simple getThenStmt method
     * @return thenStmt
     */
    public Statement getThenStmt()
    {
        return thenStmt;
    }

    /**
     * Simple getElseStmt method
     * @return elseStmt
     */
    public Statement getElseStmt()
    {
        return elseStmt;
    }

    /**
     * Compile method for if
     * @param e the emitter to use
     */
    public void compile(Emitter e)
    {
        int id = e.nextLabelID();

        if(elseStmt == null)
        {
            String endLabel = "endif" + id;
            cond.compile(e, endLabel);
            thenStmt.compile(e);
            e.emit(endLabel + ":");
        }

        else
        {
            cond.compile(e, "else" + id);
            thenStmt.compile(e);
            e.emit("j endif" + id);
            e.emit("else" + id + ":");
            elseStmt.compile(e);
            e.emit("endif" + id + ":");
        }
    }
}
