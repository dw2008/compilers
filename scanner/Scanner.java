package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Daniel Wu
 * @version 1/28/26
 * Usage:
 * Has checks isDigit, isLetter, and isWhitespace. Can use nextToken() to check the next token
 * given by the Scanner.
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     * @postcondition eof = false, currentChar set
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     * @postcondition eof = false, currentChar set
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * Attempts to read a value from an input stream and throws an IOException
     * if an I/O error occurs with read(). If we get an end of file indicator from read(),
     * we update eof, otherwise we move the currentChar to the next char.
     * Note: taken from Schoology AP CS DS folder's example and modified
     * @throws RuntimeException if read fails
     * @precondition needs next char to be legal
     * @postcondition eof set true or currentChar incremented
     */
    private void getNextChar() throws RuntimeException
    {
        try
        {
            int inp = in.read();
            if(inp == -1)
                eof = true;
            else
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Method: eat
     * Takes in char expected and compares expected with the currentChar. If matching,
     * calls getNextChar(), or throws an exception if not.
     * @param expected the expected value of currentChar
     * @precondition needs next char to be legal
     */
    private void eat(char expected) throws ScanErrorException
    {
        if(currentChar == expected)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal Expression: expected " + currentChar +
                    " and found " + expected);
        }
    }
    /**
     * Method: hasNext
     * Checks if at end of file
     * @return true if eof == false, false if eof == true
     */
    public boolean hasNext()
    {
        return !eof;
    }
    /**
     * Checks if input char c is a digit
     * @param c char to check
     * @return true if c is a digit between 0-9, false otherwise
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }
    /**
     * Checks if input char c is a letter := [a-z, A-Z]
     * @param c char to check
     * @return true if c is a valid letter, false otherwise
     */
    public static boolean isLetter(char c)
    {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    /**
     * Checks if input char c is a whitespace := [' ', '\t', '\r', '\n']
     * @param c char to check
     * @return true if c is a valid whitespace, false otherwise
     */
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }
    /**
     * Scans input number := digit(digit)* and returns a String representing
     * that number or throws ScanErrorException
     * @precondition currentChar is a digit
     * @throws ScanErrorException if first char is not a digit
     * @return String representing number found in input stream
     */
    private String scanNumber() throws ScanErrorException
    {
        if(!isDigit(currentChar))
        {
            throw new ScanErrorException("Expected digit, found " + currentChar);
        }
        StringBuilder res = new StringBuilder();
        while(hasNext() && isDigit(currentChar))
        {
            res.append(currentChar);
            eat(currentChar);
        }
        return res.toString();
    }
    /**
     * Scans input identifier := letter(letter|digit)* and returns a String
     * representing that identifier or throws ScanErrorException
     * @precondition currentChar is a letter
     * @throws ScanErrorException if first char is not a letter
     * @return String representing identifier found in input stream
     */
    private String scanIdentifier() throws ScanErrorException
    {
        if(!isLetter(currentChar))
        {
            throw new ScanErrorException("Expected letter, found " + currentChar);
        }
        StringBuilder res = new StringBuilder();
        while(hasNext() && (isLetter(currentChar) || isDigit(currentChar)))
        {
            res.append(currentChar);
            eat(currentChar);
        }
        return res.toString();
    }
    /**
     * Scans input operand := [‘=’ ‘+’ ‘-‘ ‘*’ ‘%’ ‘(‘ ‘)’] and returns a String
     * representing an operand or throws ScanErrorException
     * @precondition currentChar is not a digit or a letter or a whitespace
     * @throws ScanErrorException if first char is a digit, number, or whitespace throw exception;
     * if currentChar is not a valid operand, throw exception
     * @return String representing operand found in input stream
     */
    private String scanOperand() throws ScanErrorException
    {
        if(isDigit(currentChar) || isLetter(currentChar) || isWhiteSpace(currentChar))
        {
            throw new ScanErrorException("Expected operand, found " + currentChar);
        }
        StringBuilder res = new StringBuilder();
        switch(currentChar)
        {
            case '+':
            case '=':
            case '-':
            case '*':
            case '%':
            case '(':
            case ')':
            case '/':
            case ';':
            case ',':
            case '.':
                res.append(currentChar);
                eat(currentChar);
                break;
            case ':':
                eat(currentChar);
                res.append(':');
                if(currentChar == '=')
                {
                    eat('=');
                    res.append("=");
                }
                break;
            case '>':
                eat(currentChar);
                res.append('>');
                if(currentChar == '=')
                {
                    eat('=');
                    res.append('=');
                }
                break;
            case '<':
                eat(currentChar);
                res.append('<');
                if(currentChar == '>')
                {
                    res.append('>');
                    eat('>');
                }
                else if(currentChar == '=')
                {
                    res.append('=');
                    eat('=');
                }
                break;
            default:
                throw new ScanErrorException("Invalid operand: " + currentChar);

        }
        return res.toString();
    }
    /**
     * Skips any leading whitespace and examines currentChar, then calls the appropriate methods
     * within scanNumber(), scanIdentifier(), and scanOperand() to scan the next token in the
     * input stream. Can also handle single-line comments starting with "//".
     * @return String representing lexeme, or "EOF" if eof is true when called
     * @postcondition token eaten
     */
    public String nextToken()
    {
        if (eof)
        {
            return "EOF";
        }
        StringBuilder res = new StringBuilder();
        try
        {
            while (hasNext() && isWhiteSpace(currentChar))
            {
                eat(currentChar);
            }
            if (eof)
            {
                return "EOF";
            }
            else if(currentChar == '/')
            {
                eat(currentChar);
                if(currentChar == '/')
                {
                    while(hasNext() && currentChar != '\n')
                    {
                        eat(currentChar);
                    }
                    eat(currentChar);
                }
                else
                {
                    return "/";
                }
            }
            if (isDigit(currentChar))
            {
                res.append(scanNumber());
            }
            else if (isLetter(currentChar))
            {
                res.append(scanIdentifier());
            }
            else
            {
                res.append(scanOperand());
            }
        }
        catch(ScanErrorException e)
        {
            System.err.println("Error while building token. Partial result: '"
                    + res + "': " + e.getMessage());
            System.exit(1);
            //getNextChar();
        }
        return res.toString();
    }    
}
