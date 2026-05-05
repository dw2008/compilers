package ast;

import environment.Environment;
import scanner.ScanErrorException;
import java.util.List;

/**
 * Evaluator has an exec method for each Statement type and an eval for each Expression type,
 * does the job of the old Parser class
 * @author Daniel Wu
 * @version 03/20/2026
 */
public class Evaluator
{
    /**
     * Reroutes and casts expr to the correct types (Number, Variable, BinOp, If)
     * @param expr the Expression to evaluate
     * @param env current environment
     * @return int value of expression
     * @throws ScanErrorException if invalid call type (or error in BinOp)
     */
    public int eval(Expression expr, Environment env) throws ScanErrorException
    {
        if(expr instanceof Number)
        {
            return eval((Number) expr);
        }
        else if(expr instanceof Variable)
        {
            return eval((Variable) expr, env);
        }
        else if(expr instanceof BinOp)
        {
            return eval((BinOp) expr, env);
        }
        else if(expr instanceof Condition)
        {
            return eval((Condition) expr, env);
        }
        else if(expr instanceof ProcedureCall)
        {
            return eval((ProcedureCall) expr, env);
        }
        else
        {
            throw new ScanErrorException("Invalid eval call type: " + expr.getClass());
        }
    }
    /**
     * Evaluator for Number, returns int value
     * @param num the Number to evaluate
     * @return int value of num
     */
    public int eval(Number num)
    {
        return num.getValue();
    }

    /**
     * Evaluator for Variable, returns int value of var
     * @param var the Variable to get
     * @param env current environment
     * @return int value of var
     */
    public int eval(Variable var, Environment env)
    {
        return env.getVariable(var.getName());
    }

    /**
     * Evaluator for binOp, returns int value of expression
     * @param binop the binop to evaluate
     * @param env current environment
     * @return int value of num
     */
    public int eval(BinOp binop, Environment env) throws ScanErrorException
    {
        int left = eval(binop.getExp1(), env);
        int right = eval(binop.getExp2(), env);
        switch(binop.getOp())
        {
            case("+"):
                return left + right;
            case("-"):
                return left - right;
            case("*"):
                return left * right;
            case("/"):
                return left / right;
            default:
                throw new ScanErrorException("Invalid operator: " + binop.getOp());
        }
    }

    /**
     * Evaluator for procedurecall
     * @param call the call to evaluate
     * @param env current environment
     * @return result of procedurecall
     * @throws ScanErrorException if invalid procedurecall
     */
    public int eval(ProcedureCall call, Environment env) throws ScanErrorException
    {
        ProcedureDeclaration procedure = env.getProcedure(call.getName());
        Environment child = new Environment(env);
        child.declareVariable(call.getName(), 0);
        List<String> params = procedure.getParams();
        List<Expression> args = call.getArgs();
        for(int i = 0; i < args.size(); i++)
        {
            child.declareVariable(params.get(i), eval(args.get(i), env));
        }
        exec(procedure.getStmt(), child);
        return child.getVariable(call.getName());
    }

    /**
     * Evaluates a Condition statement, returns true/false
     * @param cond the condition to evaluate
     * @param env current environment
     * @return 0 for false 1 for true
     * @throws ScanErrorException if invalid relop
     */
    public int eval(Condition cond, Environment env) throws ScanErrorException
    {
        int left = eval(cond.getExp1(), env);
        int right = eval(cond.getExp2(), env);
        switch(cond.getRelop())
        {
            case("<"):
                if(left < right)
                {
                    return 1;
                }
                return 0;
            case(">"):
                if(left > right)
                {
                    return 1;
                }
                return 0;
            case("<="):
                if(left <= right)
                {
                    return 1;
                }
                return 0;
            case(">="):
                if(left >= right)
                {
                    return 1;
                }
                return 0;
            case("<>"):
                if(left != right)
                {
                    return 1;
                }
                return 0;
            case("="):
                if(left == right)
                {
                    return 1;
                }
                return 0;
            default:
                throw new ScanErrorException("Invalid relop: " + cond.getRelop());
        }
    }

    /**
     * Reroutes and casts stmt to the correct types (Writeln, Assignment, Block)
     * @param stmt the Statement to execute
     * @param env current environment
     * @throws ScanErrorException if expression/statement in invalid format
     */
    public void exec(Statement stmt, Environment env) throws ScanErrorException
    {
        if(stmt instanceof Writeln)
        {
            exec((Writeln) stmt, env);
        }
        else if(stmt instanceof Assignment)
        {
            exec((Assignment) stmt, env);
        }
        else if(stmt instanceof Block)
        {
            exec((Block) stmt, env);
        }
        else if(stmt instanceof If)
        {
            exec((If) stmt, env);
        }
        else if(stmt instanceof While)
        {
            exec((While) stmt, env);
        }
        else if(stmt instanceof Readln)
        {
            exec((Readln) stmt, env);
        }
        else if(stmt instanceof Repeat)
        {
            exec((Repeat) stmt, env);
        }
        else if(stmt instanceof For)
        {
            exec((For) stmt, env);
        }
        else
        {
            throw new ScanErrorException("Invalid exec call type: " + stmt.getClass());
        }
    }

    /**
     * Executes Writeln, prints out the expression value in writeln
     * @param writeln the Writeln to execute
     * @param env current environment
     * @throws ScanErrorException if invalid expression
     */
    public void exec(Writeln writeln, Environment env) throws ScanErrorException
    {
        System.out.println(eval(writeln.getExp(), env));
    }

    /**
     * Executes Assignment, assigns variable + value in env
     * @param assign the Assignment to execute
     * @param env current environment
     * @throws ScanErrorException if invalid expression in assign
     */
    public void exec(Assignment assign, Environment env) throws ScanErrorException
    {
        env.setVariable(assign.getVar(), eval(assign.getExp(), env));
    }

    /**
     * Executes all statements in a Block
     * @param block the Block to execute
     * @param env current environment
     * @throws ScanErrorException if invalid statement
     */
    public void exec(Block block, Environment env) throws ScanErrorException
    {
        for(Statement stmt: block.getStmts())
        {
            exec(stmt, env);
        }
    }

    /**
     * Executes an IF cond THEN stmt statement or an IF THEN ELSE statement
     * @param ifs the IF statement to evaluate
     * @param env current environment
     * @throws ScanErrorException if invalid IF THEN (ELSE) format
     */
    public void exec(If ifs, Environment env) throws ScanErrorException
    {
        Condition cond = ifs.getCond();
        if(eval(cond, env) == 1)
        {
            exec(ifs.getThenStmt(), env);
        }
        else if(eval(cond, env) == 0 && ifs.getElseStmt() != null)
        {
            exec(ifs.getElseStmt(), env);
        }
    }

    /**
     * Executes an WHILE statement
     * @param whi the IF statement to evaluate
     * @param env current environment
     * @throws ScanErrorException if invalid WHILE format
     */
    public void exec(While whi, Environment env) throws ScanErrorException
    {
        Condition cond = whi.getCond();
        while(eval(cond, env) == 1)
        {
            exec(whi.getStmt(), env);
        }
    }

    /**
     * Executes a READLN statement
     * @param readln the READLN statement to execute
     * @param env current environment
     * @throws ScanErrorException if invalid READLN format
     */
    public void exec(Readln readln, Environment env) throws ScanErrorException
    {
        String var = readln.getString();
        System.out.println("INPUT FOR " + var + ": ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        env.setVariable(var, scanner.nextInt());
    }

    /**
     * Executes a REPEAT UNTIL statement
     * @param repeat the Repeat statement to execute
     * @param env current environment
     * @throws ScanErrorException if invalid REPEAT UNTIL format
     */
    public void exec(Repeat repeat, Environment env) throws ScanErrorException
    {
        Condition cond = repeat.getCond();
        Statement stmt = repeat.getStmt();
        while(eval(cond, env) == 0)
        {
            exec(stmt, env);
        }
    }

    /**
     * Executes a FOR TO DO statement
     * @param f the For statement to execute
     * @param env current environment
     * @throws ScanErrorException if invalid FOR format
     */
    public void exec(For f, Environment env) throws ScanErrorException
    {
        Assignment assignment = f.getAssignment();
        Number num = f.getNum();
        Statement stmt = f.getStmt();
        exec(assignment, env);
        for(int i = eval(assignment.getExp(), env); i <= num.getValue(); i++)
        {
            exec(stmt, env);
        }
    }

    /**
     * Executes a Program
     * @param program the program to execute
     * @param env current environment
     * @throws ScanErrorException if invalid program format
     */
    public void exec(Program program, Environment env) throws ScanErrorException
    {
        for(ProcedureDeclaration procedure:program.getProcedures())
        {
            env.setProcedure(procedure.getName(), procedure);
        }
        exec(program.getStatement(), env);
    }
}
