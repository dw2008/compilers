package parserBackup;

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
        InputStream in = new FileInputStream("./parserBackup/parserTest0.txt");
        Scanner scanner = new Scanner(in);
        Parser parser = new Parser(scanner);

        while(parser.hasNext())
        {
            parser.parseStatement();
        }
        parser.parseStatement();
        System.out.println(parser.hasNext());
    }
}
