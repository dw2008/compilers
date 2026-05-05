package scanner;

import java.io.*;

/**
 * ScannerTester is a simple tester for Scanner, checks hasNext(), isDigit(), isLetter(),
 * isWhitespace(), and uses test files to test Scanner
 * @author Daniel Wu
 * @version 2/3/26
 */
public class ScannerTester
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner = new Scanner("x = pleasework.");
            if (scanner.hasNext())
            {
                System.out.println("hasNext() returns true");
            }
            if (Scanner.isDigit('0') && Scanner.isDigit('8') && !Scanner.isDigit('a'))
            {
                System.out.println("isDigit pass");
            }
            if (Scanner.isLetter('B') && Scanner.isLetter('z') && !Scanner.isLetter('9'))
            {
                System.out.println("isLetter pass");
            }
            if (Scanner.isWhiteSpace(' ') && Scanner.isWhiteSpace('\n') && Scanner.isWhiteSpace('\t')
                    && Scanner.isWhiteSpace('\r') && !Scanner.isWhiteSpace('j'))
            {
                System.out.println("isWhiteSpace pass");
            }

//            InputStream in = new FileInputStream("./scanner/ScannerTest.txt");
//            Scanner s = new Scanner(in);
//            while(s.hasNext())
//            {
//                System.out.println(s.nextToken());
//            }

            InputStream adv = new FileInputStream("./scanner/scannerTestAdvanced.txt");
            Scanner sAdv = new Scanner(adv);
            while(sAdv.hasNext())
            {
                System.out.println(sAdv.nextToken());
            }
            System.out.println("ended");
        }
        catch (FileNotFoundException e)
        {
            System.err.println("FileNotFound exception");
        }
    }
}