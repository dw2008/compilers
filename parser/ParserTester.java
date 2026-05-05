package parser;

import environment.Environment;
import ast.Evaluator;
import scanner.ScanErrorException;
import scanner.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * ParserTester is a simple tester for Parser, loops through parseStatement until EOF
 * @author Daniel Wu
 * @version 03/11/2026
 */
public class ParserTester
{
    public static void main(String[] args) throws FileNotFoundException, ScanErrorException
    {
        InputStream in = new FileInputStream("./parser/parserTest8.txt");
        Scanner scanner = new Scanner(in);
        Parser parser = new Parser(scanner);
        Evaluator e = new Evaluator();
        Environment env = new Environment();
        e.exec(parser.parseProgram(), env);
    }
}
