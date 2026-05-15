package parser;

import ast.*;
import ast.Number;
import scanner.ScanErrorException;
import scanner.Scanner;

import java.util.ArrayList;
import java.util.List;
/**
 * Parser is a simple parser for Compilers and Interpreters lab (does not execute,
 * only parses statements)
 * @author Daniel Wu
 * @version 04/07/2026
 * Usage: Can parse statements with the following grammar (using parseStatement()):
 * stmt → WRITELN ( expr ) ; | BEGIN stmts END ;
 * stmts → stmts stmt | ε
 * expr → expr + term | expr - term | term
 * term → term * factor | term / factor | factor
 * factor → ( expr ) | - factor | num
 */
public class Parser
{
    private final Scanner scanner;
    private String current;

    /**
     * Parser constructor that takes in a Scanner, also takes in nextToken from scanner
     * @param in the scanner to use
     * @precondition in must be a valid scanner
     * @postcondition scanner and current are given values
     */
    public Parser(Scanner in)
    {
        scanner = in;
        current = scanner.nextToken();
    }

    /**
     * Check for if eof or not
     * @return eof or not
     */
    public boolean hasNext()
    {
        return scanner.hasNext();
    }

    /**
     * Takes in expected token, if matches, asks scanner for next token and stores this
     * as current token, otherwise throws IllegalArgumentException
     * @param expected the expected token
     * @postcondition current token is eaten, moves to next token
     * @throws ScanErrorException if found token does not match expected
     */
    private void eat(String expected) throws ScanErrorException
    {
        if(current.equals(expected))
        {
            current = scanner.nextToken();
        }
        else
        {
            throw new ScanErrorException("Expected '" + expected + "' found '" + current + "'");
        }
    }

    /**
     * Parses and returns the value of a number token
     * @precondition current token is a number
     * @postcondition number token has been eaten
     * @return parsed Number
     * @throws ScanErrorException if not a number
     */
    private Number parseNumber() throws ScanErrorException
    {
        Number num = new Number(Integer.parseInt(current));
        eat(current);
        return num;
    }

    /**
     * Parses a program
     * program → VARS var, ..., ; | PROCEDURE id ( ) ; stmt program | stmt .
     * @return new program
     * @throws ScanErrorException if not a proper program
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<ProcedureDeclaration> procedures = new ArrayList<>();
        List<String> vars = new ArrayList<>();

        while(current.equals("VAR"))
        {
            eat("VAR");
            vars.add(current);
            eat(current);
            while(current.equals(","))
            {
                eat(",");
                vars.add(current);
                eat(current);
            }
            eat(";");
        }

        while(current.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = current;
            eat(current);
            eat("(");
            List<String> params = new ArrayList<>();
            if(!current.equals(")"))
            {
                params.add(current);
                eat(current);
                while(current.equals(","))
                {
                    eat(",");
                    params.add(current);
                    eat(current);
                }
            }
            eat(")");
            eat(";");
            Statement stmt = parseStatement();
            procedures.add(new ProcedureDeclaration(stmt, name, params));
        }

        Statement body = parseStatement();
        eat(".");
        return new Program(procedures, vars, body);
    }

    /**
     * stmt → WRITELN ( expr ) ; | BEGIN stmts END ; | id := expr ;
     * | IF cond THEN stmt | WHILE cond DO stmt
     * stmts → stmts stmt | ε
     * expr → expr + term | expr - term | term
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num | id
     * cond → expr relop expr
     * relop → = | <> | < | > | <= | >=
     * @precondition statement is in valid format
     * @postcondition statement parsed
     * @return Statement parsed as a Writeln (WRITELN), Block (BEGIN, END), or Assignment (variable)
     * @throws ScanErrorException if format incorrect
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if(current.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpr();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(current.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String var = current;
            eat(current);
            eat(")");
            eat(";");
            return new Readln(var);
        }
        else if(current.equals("BEGIN"))
        {
            List<Statement> stmts = new ArrayList<>();
            eat("BEGIN");
            while(!current.equals("END"))
            {
                stmts.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(stmts);
        }
        else if(current.equals("IF"))
        {
            eat("IF");
            Expression exp1 = parseExpr();
            String relop = current;
            eat(current);
            Expression exp2 = parseExpr();
            eat("THEN");
            Statement thenStmt = parseStatement();
            Condition cond = new Condition(exp1, exp2, relop);
            if(current.equals("ELSE"))
            {
                eat("ELSE");
                Statement elseStmt = parseStatement();
                return new If(cond, thenStmt, elseStmt);
            }
            return new If(cond, thenStmt);
        }
        else if(current.equals("WHILE"))
        {
            eat("WHILE");
            Expression exp1 = parseExpr();
            String relop = current;
            eat(current);
            Expression exp2 = parseExpr();
            eat("DO");
            Statement stmt = parseStatement();
            Condition cond = new Condition(exp1, exp2, relop);
            return new While(cond, stmt);
        }
        else if(current.equals("FOR"))
        {
            eat("FOR");
            String var = current;
            eat(current);
            eat(":=");
            Expression expr = parseExpr();
            Assignment assignment = new Assignment(var, expr);
            eat("TO");
            Number num = parseNumber();
            eat("DO");
            Statement stmt = parseStatement();
            return new For(assignment, num, stmt);
        }
        else if(current.equals("REPEAT"))
        {
            eat("REPEAT");
            Statement stmt = parseStatement();
            eat("UNTIL");
            Expression exp1 = parseExpr();
            String relop = current;
            eat(current);
            Expression exp2 = parseExpr();
            eat(";");
            return new Repeat(new Condition(exp1, exp2, relop), stmt);
        }
        else
        {
            String var = current;
            eat(current);
            eat(":=");
            Expression expr = parseExpr();
            eat(";");
            return new Assignment(var, expr);
        }
    }

    /**
     * Parses a factor:
     * factor → ( expr ) | - factor | num | id
     * @precondition parenthesis for factors must be closed, i.e. each '(' has a ')'
     * @precondition factor must be in valid format
     * @postcondition factor parsed
     * @return value of factor as an Expression (can be a Number or Variable)
     * @throws ScanErrorException if incorrect factor format
     */
    private Expression parseFactor() throws ScanErrorException
    {
        switch(current)
        {
            case ("-"):
                eat("-");
                return new BinOp("-", new Number(0), parseFactor());
            case ("("):
                eat("(");
                Expression expr = parseExpr();
                eat(")");
                return expr;
            default:
                try
                {
                    return parseNumber();
                }
                catch (NumberFormatException e)
                {
                    String var = current;
                    eat(current);
                    if(current.equals("("))
                    {
                        eat("(");
                        List<Expression> args = new ArrayList<>();
                        if(!current.equals(")"))
                        {
                            args.add(parseExpr());
                            while(current.equals(","))
                            {
                                eat(",");
                                args.add(parseExpr());
                            }
                        }
                        eat(")");
                        return new ProcedureCall(var, args);
                    }
                    return new Variable(var);
                }
        }
    }

    /**
     * Parses a term:
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num | id
     * @precondition term must be valid format
     * @postcondition term parsed
     * @return Expression parsed
     * @throws ScanErrorException if invalid term
     */
    private Expression parseTerm() throws ScanErrorException
    {
        return parseTermHelper(parseFactor());
    }

    /**
     * Helper for parseTerm(), recursive
     * @param val the value of the term to return
     * @precondition term must be valid format
     * @postcondition term parsed
     * @return Expression parsed
     * @throws ScanErrorException if invalid term
     */
    private Expression parseTermHelper(Expression val) throws ScanErrorException
    {
        if(current.equals("*"))
        {
            eat("*");
            val = new BinOp("*", val, parseTerm());
            return parseTermHelper(val);
        }
        if(current.equals("/"))
        {
            eat("/");
            val = new BinOp("/", val, parseTerm());
            return parseTermHelper(val);
        }
        return val;
    }

    /**
     * Parses an expression:
     * expr → expr + term | expr - term | term
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num | id
     * @precondition expr must be valid format
     * @postcondition expr parsed
     * @return Expression parsed
     * @throws ScanErrorException if invalid expression
     */
    private Expression parseExpr() throws ScanErrorException
    {
        return parseExprHelper(parseTerm());
    }

    /**
     * Helper for parseExpr, recursive
     * @param val value of expression
     * @precondition expr must be valid format
     * @postcondition expr parsed
     * @return value of expression
     * @throws ScanErrorException if invalid expression
     */
    private Expression parseExprHelper(Expression val) throws ScanErrorException
    {
        if(current.equals("+"))
        {
            eat("+");
            val = new BinOp("+", val, parseTerm());
            return parseExprHelper(val);
        }
        if(current.equals("-"))
        {
            eat("-");
            val = new BinOp("-", val, parseTerm());
            return parseExprHelper(val);
        }
        return val;
    }
}
