package ast;

import java.util.List;

/**
 * Program class epresents a program, has list of procedures and a statement
 * @author Daniel Wu
 * @version 04/21/26
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Constructor for Program
     * @param procedures list of procedures declared
     * @param stmt the statement to execute for the program
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    /**
     * Simple getProcedures method
     * @return procedures
     */
    public List<ProcedureDeclaration> getProcedures()
    {
        return procedures;
    }

    /**
     * Simple getStatement method
     * @return stmt
     */
    public Statement getStatement()
    {
        return stmt;
    }
}
