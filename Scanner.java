package Scanner;

import java.io.*;

/**
 * Scanner.Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1.
 * The Scanner.Scanner performs the lexical analysis portion of the compiler/interpreter.
 * It reads the input, identifies the lexemes, and produces a string of tokens.
 *
 * @author  Annabelle Ju
 * @version 04/17/2020
 *
 * Usage:
 * FileInputStream inStream = new FileInputStream(new File("<file name>");
 * Scanner.Scanner lex = new Scanner.Scanner(inStream);
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner.Scanner constructor for construction of a scanner that
     * uses an InputStream object for input.
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File("<file name>");
     * Scanner.Scanner lex = new Scanner.Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner.Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner.Scanner lex = new Scanner.Scanner(input_string);
     *
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Method: getNextChar properly sets currentChar to the next character to be read in the input.
     * Also identifies if the Scanner.Scanner is at the end of the file, including encountering a period.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1 || currentChar == '.')
                eof = true;
            else
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Method: eat compares the values of the inputted expected value to currentChar.
     * If the values are equal, the Scanner.Scanner is advanced one character using getNextChar.
     * Else, if the values are unequal, a Scanner.ScanErrorException is thrown.
     *
     * @param expected the expected value of currentChar.
     *
     * @throws ScanErrorException if the given char does not match currentChar
     */

    private void eat(char expected) throws ScanErrorException
    {
        if (expected==currentChar)
            getNextChar();
        else
            throw new ScanErrorException("Illegal character: expected "+currentChar+
                    " found "+expected);
    }

    /**
     * Method: scanNumber scans the input character and returns the number lexeme in the input.
     * If no lexeme is found, then the method throws an error.
     *
     * @throws ScanErrorException error handler for the eat helper method.
     *
     * @return the number lexeme found in the input.
     */
    private String scanNumber() throws ScanErrorException
    {
        String resultLexeme = "";

        while (isDigit(currentChar))
        {
            resultLexeme += currentChar;
            eat(currentChar);
        }

        return resultLexeme;
    }

    /**
     * Method: scanIdentifier scans the input character, returns the identifier lexeme in the input.
     * If no lexeme is found, then the method throws an error.
     *
     * @throws ScanErrorException error handler for the eat helper method.
     *
     * @return the identifier lexeme found in the input.
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String resultLexeme = "";

        while (isLetter(currentChar) | isDigit(currentChar))
        {
            resultLexeme += currentChar;
            eat(currentChar);
        }

        return resultLexeme;
    }

    /**
     * Method: scanOperand scans the input character and returns the operand lexeme in the input.
     * If no lexeme is found, then the method throws an error.
     *
     * @throws ScanErrorException error handler for the eat helper method.
     *
     * @return the operand lexeme found in the input.
     */
    private String scanOperand() throws ScanErrorException
    {
        String resultLexeme = "";

        if (currentChar == '<' || currentChar == '>' || currentChar == ':')
        {
            while (isOperand(currentChar))
            {
                resultLexeme += currentChar;
                eat(currentChar);
            }
        }
        else
        {
            if (isOperand(currentChar))
            {
                resultLexeme += currentChar;
                eat(currentChar);
            }
        }

        return resultLexeme;
    }

    /**
     * Method: hasNext determines whether the input has more characters to read.
     *
     * @return true if there are more characters in the input to read.
     *         false if the Scanner.Scanner is at the end of the file.
     */
    public boolean hasNext()
    {
        return !eof && currentChar!='.';
    }

    /**
     * Method: nextToken scans the input stream and finds the next token,
     * Disregarding white spaces, and handles single-line comments.
     *
     * @return the lexeme found in the input or "END" if the input stream has already ended.
     */
    public String nextToken() throws ScanErrorException, IOException
    {
        if (!hasNext() || currentChar=='.')
        {
            eof = true;
            return "END";
        }

        String lexeme = "";

        try
        {
            if (isWhiteSpace(currentChar)&&hasNext())
            {
                while (isWhiteSpace(currentChar)&&hasNext())
                    eat(currentChar);
            }
            else
            {
                if (isDigit(currentChar))
                    lexeme = scanNumber();
                else if (isLetter(currentChar))
                    lexeme = scanIdentifier();
                else if (isOperand(currentChar))
                {
                    lexeme = scanOperand();

                    if (lexeme.equals("//"))
                    {
                        lexeme = "";
                        in.readLine();
                    }
                }
            }
        }
        catch (ScanErrorException e)
        {
            System.exit(-1);
        }

        return lexeme;
    }

    /**
     * Helper method: isOperand determines whether a given character is an operand,
     * According to regular expression digit := ['=','+','-','*','/','%','(',')']
     *
     * @param c the character to be assessed.
     * @return true  if the given character is an operand,
     *         false if the given character is not an operand.
     */
    public static boolean isOperand(char c)
    {
        return (c == '=') || (c =='+') || (c =='-') || (c =='*') ||
                (c =='/') || (c =='%') || (c =='(') || (c ==')') ||
                (c == ';') || (c == ':' || (c == '<') || (c == '>') || (c == ','));
    }

    /**
     * Helper method: isDigit determines whether a given character is a digit,
     * According to regular expression digit := [0-9]
     *
     * @param c the character to be assessed.
     * @return true  if the given character is a digit,
     *         false if the given character is not a digit.
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    /**
     * Helper method: isLetter determines whether a given character is a letter,
     * According to regular expression letter := [A-Z, a-z]
     *
     * @param c the character to be assessed.
     * @return true  if the given character is a letter,
     *         false if the given character is not a letter.
     */
    public static boolean isLetter(char c)
    {
        return (c>='A' && c<='Z') || (c>='a' && c<='z');
    }

    /**
     * Helper method: isWhiteSpace determines whether a given character is a white space,
     * According to regular expression letter := [' ', '\t', '\r', '\n']
     *
     * @param c the character to be assessed.
     * @return true  if the given character is a white space,
     *         false if the given character is not a white space.
     */
    public static boolean isWhiteSpace(char c)
    {
        return (c == ' ') || (c =='\t') || (c =='\r') || (c =='\n');
    }

    /**
     * The main method for the Scanner.Scanner. Initiates a new Scanner.Scanner,
     * Continues producing tokens until the end of the input file and
     * Produces a string of these tokens with spaces between each token.
     *
     * @param args String arguments for main method.
     *
     * @throws IOException errorHandler for file reading.
     * @throws ScanErrorException error handler for the nextToken method.
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        FileInputStream inStream =
                new FileInputStream(new File("scannerTestAdvanced.txt"));
        Scanner lex = new Scanner(inStream);

        String outputTokens = "";
        String token;

        while (!lex.eof)
        {
            token = lex.nextToken();
            outputTokens += token + " ";
        }

        System.out.println(outputTokens);
    }
}
