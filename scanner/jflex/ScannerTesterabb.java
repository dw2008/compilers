package scanner.jflex;

import java.io.*;

/**
 * ScannerabbTester tests Scannerabb. Tests token recognition for
 * EMAIL, TIMESTAMP, DOCUMENT_ID, NUM_DATE, TEXT_DATE, PHONE,
 * and CASE_FILE_ID patterns.
 * @author Anu Datar, Brandon Tully, Daniel Wu
 * @version 2/23/26
 */
public class ScannerTesterabb
{
    public static void main(String[] str) throws FileNotFoundException, IOException
    {
        InputStream input = new FileInputStream("scanner/jflex/fbi_declassified_cases.txt");
        Scannerabb scanner = new Scannerabb(new java.io.InputStreamReader(input));

        String token = "";
        while(!token.equals("EOF"))
        {
            try
            {
                token = scanner.nextToken();
                if (token == null)
                    break;
                System.out.println(token);
            }
            catch (Exception e)
            {
                System.out.println("ScannerTesterabb had error: " + e);
            }
        }
    }
}