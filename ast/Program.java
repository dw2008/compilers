package ast;

import emitter.Emitter;

import java.util.List;

/**
 * Program class epresents a program, has list of procedures and a statement
 * @author Daniel Wu
 * @version 04/21/26
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private List<String> vars;
    private Statement stmt;

    /**
     * Constructor for Program
     * @param procedures list of procedures declared
     * @param stmt the statement to execute for the program
     */
    public Program(List<ProcedureDeclaration> procedures, List<String> vars, Statement stmt)
    {
        this.procedures = procedures;
        this.vars = vars;
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

    /**
     * Simple getVars method
     * @return vars
     */
    public List<String> getVars()
    {
        return vars;
    }

    /**
     * Uses emitter to write a program to a filename
     * @param filename the file to write to
     */
    public void compile(String filename)
    {
        Emitter e = new Emitter(filename);
        e.emit(".data");

        for(String var: vars)
        {
            e.emit("var" + var + ": .word 0");
        }

        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:   #Mars will automatically look for main");
        stmt.compile(e);
        e.emit("li $v0 10       # normal termination");
        e.emit("syscall");
        e.close();
    }
}
