package ast;

import java.util.List;

/**
 * ProcedureDeclaration represents a procedure declaration statement, has a statment and a procedure name
 * @author Daniel Wu
 * @version 4/17/26
 */
public class ProcedureDeclaration extends Statement
{
    private Statement stmt;
    private String name;
    private List<String> params;

    /**
     * Constructor for ProcedureDeclaration, takes Statement and String
     * @param stmt Statement to take in
     * @param name String name to take in
     */
    public ProcedureDeclaration(Statement stmt, String name, List<String> params)
    {
        this.stmt = stmt;
        this.name = name;
        this.params = params;
    }

    /**
     * Simple getStmt() method
     * @return stmt
     */
    public Statement getStmt()
    {
        return stmt;
    }

    /**
     * Simple getName() method
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Simple getParams method
     * @return params
     */
    public List<String> getParams()
    {
        return params;
    }
}
