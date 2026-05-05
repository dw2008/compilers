package ast;

import java.util.List;

/**
 * Definition for class Block for ast, extends Statement and has a List of Statements stmts
 * @author Daniel Wu
 * @version 3/18/26
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Constructor for Block populates stmts with the inputted List
     * @param stmts
     * @postcondition this.stmts populated with input stmts
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Simple getStmts method
     * @return stmts
     */
    public List<Statement> getStmts()
    {
        return stmts;
    }
}
