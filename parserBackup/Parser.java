package parserBackup;

import scanner.ScanErrorException;
import scanner.Scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser is a simple parser for Compilers and Interpreters lab
 * @author Daniel Wu
 * @version 03/11/2026
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
    private Map<String, Integer> vars;

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
        vars = new HashMap<>();
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
     * @return value of parsed int
     * @throws ScanErrorException if not a number
     */
    private int parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(current);
        eat(current);
        return num;
    }

    /**
     * Parses and returns a statement:
     * stmt → WRITELN ( expr ) ; | BEGIN stmts END ;
     * stmts → stmts stmt | ε
     * expr → expr + term | expr - term | term
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num
     * @precondition statement is in valid format
     * @postcondition statement parsed
     * @throws ScanErrorException if format incorrect
     */
    public void parseStatement() throws ScanErrorException
    {
        if(!hasNext())
        {
            System.out.println("EOF");
        }
        else if(current.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpr());
            eat(")");
            eat(";");
        }
        else if(current.equals("BEGIN"))
        {
            eat("BEGIN");
            while(!current.equals("END"))
            {
                parseStatement();
            }
            eat("END");
            eat(";");
        }
        else
        {
            String var = current;
            eat(current);
            eat(":=");
            vars.put(var, parseExpr());
            eat(";");
        }
    }

    /**
     * Calls parseFactorHelper() to parse a factor:
     * factor → ( expr ) | - factor | num
     * @precondition parenthesis for factors must be closed, i.e. each '(' has a ')'
     * @precondition factor must be in valid format
     * @postcondition factor parsed
     * @return value of factor as an int
     * @throws ScanErrorException if incorrect factor format
     */
    private int parseFactor() throws ScanErrorException
    {
        return parseFactorHelper();
    }

    /**
     * Helper for parseFactor
     * @precondition factor must be in valid format
     * @precondition needs a closed parenthesis to stop
     * @postcondition factor parsed
     * @return int value of the factor (currently) and the number of closed parenthesis to eat
     * @throws ScanErrorException if invalid factor
     */
    private int parseFactorHelper() throws ScanErrorException
    {
        switch(current)
        {
            case("-"):
                eat("-");
                return -parseFactor();
            case("("):
                eat("(");
                int res = parseExpr();
                eat(")");
                return res;
            default:
                if(vars.containsKey(current))
                {
                    String var = current;
                    eat(current);
                    return vars.get(var);
                }
                return parseNumber();
        }
    }

    /**
     * Parses a term:
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num
     * @precondition term must be valid format
     * @postcondition term parsed
     * @return value of the term
     * @throws ScanErrorException if invalid term
     */
    private int parseTerm() throws ScanErrorException
    {
        return parseTermHelper(parseFactor());
    }

    /**
     * Helper for parseTerm(), recursive
     * @param val the value of the term to return
     * @precondition term must be valid format
     * @postcondition term parsed
     * @return value of the term
     * @throws ScanErrorException if invalid term
     */
    private int parseTermHelper(int val) throws ScanErrorException
    {
        if(current.equals("*"))
        {
            eat("*");
            val *= parseFactor();
            return parseTermHelper(val);
        }
        if(current.equals("/"))
        {
            eat("/");
            val /= parseFactor();
            return parseTermHelper(val);
        }
        return val;
    }

    /**
     * Parses an expression:
     * expr → expr + term | expr - term | term
     * term → term * factor | term / factor | factor
     * factor → ( expr ) | - factor | num
     * @precondition expr must be valid format
     * @postcondition expr parsed
     * @return value of expression
     * @throws ScanErrorException if invalid expression
     */
    private int parseExpr() throws ScanErrorException
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
    private int parseExprHelper(int val) throws ScanErrorException
    {
        if(current.equals("+"))
        {
            eat("+");
            val += parseTerm();
            return parseExprHelper(val);
        }
        if(current.equals("-"))
        {
            eat("-");
            val -= parseTerm();
            return parseExprHelper(val);
        }
        return val;
    }
}
