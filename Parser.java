package Parser;

import AST.*;
import AST.Number;
import Environment.Environment;
import Scanner.ScanErrorException;
import Scanner.Scanner;
import java.io.*;
import java.util.ArrayList;

/**
 * Parser is a top-down recursive descent parser that performs the syntax analysis
 * portion of compilation/interpretation. It employs recursive methods for each
 * variable in an un-ambiguous, right-recursive, and deterministic grammar.
 *
 * @author  Annabelle Ju
 * @version 04/17/2020
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

    /**
     * Constructor for objects of class Parser
     *
     * @param scan the given scanner to be used by this Parser.
     *
     * @throws IOException error handler for scanner method nextToken.
     * @throws ScanErrorException error handler for scanner method nextToken.
     */
    public Parser(Scanner scan) throws IOException, ScanErrorException
    {
        scanner = scan;
        currentToken = scan.nextToken();
    }

    /**
     * The "eat" method compares an expected token to the current token.
     * If the two tokens match, then the currentToken pointer moves to the next scanned token.
     * If the tokens do not match, an error is thrown.
     *
     * @param token the expected token to be compared to the current token.
     *
     * @throws IOException error handler for scanner method nextToken.
     * @throws ScanErrorException error handler for scanner method nextToken.
     * @throws IllegalArgumentException if the expected token does not match the current token.
     */
    private void eat(String token) throws IOException, ScanErrorException, IllegalArgumentException
    {
        if (token.equals(currentToken))
        {
            currentToken = scanner.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected token "+token+", found "+currentToken);
        }
    }

    /**
     * The "parseNumber" method eats an entire number token.
     *
     * Precondition: the current token is an integer.
     * Postcondition: the number token is eaten, the current token is the first token after the number.
     *
     * @return the value of the parsed integer.
     *
     * @throws IOException error handler for eat method.
     * @throws ScanErrorException error handler for eat method.
     */
    private Number parseNumber() throws IOException, ScanErrorException
    {
        int number = Integer.parseInt(currentToken);
        eat(currentToken);

        return new Number(number);
    }

    /**
     * The "parseStatement" method eats both simple WRITELN statement
     * And block statements starting with BEGIN and ending with END.
     * This method also prints the value of the statement.
     *
     * Precondition: the current token is at the start of the WRITELN or BEGIN/END statement.
     * Postcondition: the statement token is eaten, the current token is the first token after the statement.
     *
     * @throws IOException error handler for eat method.
     * @throws ScanErrorException error handler for eat method.
     */
    private Statement parseStatement() throws IOException, ScanErrorException
    {
        while (currentToken.equals("")) eat(currentToken);

        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            while (currentToken.equals(")")) eat(")");
            if (currentToken.equals(";")) eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            eat("");
            ArrayList<Statement> statements = new ArrayList();
            while (!currentToken.equals("END"))
            {
                statements.add(parseStatement());

                if (currentToken.equals("")) eat(currentToken);
            }
            return new Block(statements);
        }
        else if (currentToken.equals("IF"))
        {
            eat("IF");
            eat("");
            Expression e1 = parseExpression();
            while (currentToken.equals("")) eat("");
            String ro = currentToken;
            eat(currentToken);
            while (currentToken.equals("")) eat("");
            Expression e2 = parseExpression();
            while (currentToken.equals("")) eat("");
            eat("THEN");
            eat("");
            Statement state = parseStatement();
            return new If(new Condition(e1, ro, e2), state);
        }
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            eat("");
            Expression e1 = parseExpression();
            while (currentToken.equals("")) eat("");
            String ro = currentToken;
            eat(currentToken);
            while (currentToken.equals("")) eat("");
            Expression e2 = parseExpression();
            while (currentToken.equals("")) eat("");
            eat("DO");
            eat("");
            Statement state = parseStatement();
            return new While(new Condition(e1, ro, e2), state);
        }
        else if (!currentToken.equals("END"))
        {
            String key = currentToken;
            eat(currentToken);
            eat("");
            eat(":=");
            eat("");
            Expression exp = parseExpression();
            while (currentToken.equals(")")) eat(")");
            if (currentToken.equals(";")) eat(";");
            return new Assignment(key, exp);
        }
        else
        {
            eat("END");
            eat(";");
            return null;
        }
    }

    /**
     * The "parseFactor" method eats and returns a factor,
     * Where factor refers to any expression that can be multiplied/divided.
     *
     * @return the value of the factor.
     * @throws IOException error handler for eat method.
     * @throws ScanErrorException error handler for eat method.
     */
    private Expression parseFactor() throws IOException, ScanErrorException
    {
        if (currentToken.equals("-"))
        {
            eat(currentToken);
            return new BinOp("-", new Number(0), parseFactor());
        }
        else if (currentToken.equals("(") || currentToken.equals(")"))
        {
            eat(currentToken);
            return parseExpression();
        }
        else if (currentToken.equals(""))
        {
            eat(currentToken);
            return parseFactor();
        }
        else
        {
            try
            {
                Integer.parseInt(currentToken);
            }
            catch (NumberFormatException n)
            {
                String name = currentToken;
                eat(currentToken);
                if (currentToken.equals("("))
                {
                    eat(currentToken);
                    ArrayList<Expression> args = new ArrayList<>();
                    while (!currentToken.equals(")") && !currentToken.equals(";"))
                    {
                        args.add(parseExpression());
                        if (currentToken.equals(",")) eat(currentToken);
                        if (currentToken.equals("")) eat(currentToken);
                    }

                    if (currentToken.equals(")")) eat(")");
                    return new ProcedureCall(name, args);
                }
                else
                {
                    return new Variable(name);
                }
            }

            return parseNumber();
        }
    }

    /**
     * The "parseTerm" method eats and returns the value of a term,
     * Where a term is any expression that can be added/subtracted.
     *
     * @return the value of the term.
     * @throws IOException error handler for eat method.
     * @throws ScanErrorException error handler for eat method.
     */
    private Expression parseTerm() throws IOException, ScanErrorException
    {
        Expression term = parseFactor();

        while (currentToken.equals("*") || currentToken.equals("/") || currentToken.equals(""))
        {
            if (currentToken.equals("*"))
            {
                eat(currentToken);
                term = new BinOp("*", term, parseFactor());
            }
            else if (currentToken.equals("/"))
            {
                eat(currentToken);
                term = new BinOp("/", term, parseFactor());
            }

            eat(currentToken);
        }

        return term;
    }

    /**
     * The "parseExpression" method eats and returns the value of an expression,
     * Where an expression is any mathematical phrase made of terms and factors.
     *
     * @return the value of the expression.
     * @throws IOException error handler for eat method.
     * @throws ScanErrorException error handler for eat method.
     */
    private Expression parseExpression() throws IOException, ScanErrorException
    {
        Expression value = parseTerm();

        while (currentToken.equals("+") || currentToken.equals("-") || currentToken.equals("") || currentToken.equals(")"))
        {
            if (currentToken.equals("+"))
            {
                eat(currentToken);
                return new BinOp("+", value, parseTerm());
            }
            else if (currentToken.equals("-"))
            {
                eat(currentToken);
                return new BinOp("-", value, parseTerm());
            }

            eat(currentToken);
        }

        return value;
    }

    /**
     * The "parseProgram" method eats and returns a full Program
     * comprised of any number of Procedures followed by a Statement
     * that represents the main body of the Program.
     *
     * @return the Program parsed by this method.
     */
    public Program parseProgram() throws IOException, ScanErrorException
    {
        ArrayList<ProcedureDeclaration> procedures = new ArrayList<>();

        while (currentToken.equals("PROCEDURE"))
        {
            eat(currentToken);
            eat("");
            String name = currentToken;
            eat(currentToken);
            eat("(");

            ArrayList<String> params = new ArrayList<>();
            while (!currentToken.equals(")"))
            {
                params.add(currentToken);
                eat(currentToken);
                if (currentToken.equals(",")) eat(currentToken);
                if (currentToken.equals("")) eat(currentToken);
            }

            eat(")");
            eat(";");
            if (currentToken.equals("")) eat(currentToken);
            Statement contents = parseStatement();
            if (currentToken.equals("")) eat(currentToken);

            procedures.add(new ProcedureDeclaration(name, contents, params));
            if (currentToken.equals("END")) eat(currentToken);
            if (currentToken.equals(";")) eat(currentToken);
            if (currentToken.equals("")) eat(currentToken);
        }

        Statement state = parseStatement();

        return new Program(procedures, state);
    }

    /**
     * Main method for running this Parser.
     *
     * @param args String arguments for main method.
     *
     * @throws FileNotFoundException error handler for file reader.
     * @throws IOException error handler for parseStatement.
     * @throws ScanErrorException error handler for parseStatement.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ScanErrorException
    {
        FileInputStream inStream =
                new FileInputStream(new File("parserTest8.txt"));
        Scanner lex = new Scanner(inStream);
        Parser par = new Parser(lex);
        Environment env = new Environment(null);

        while(lex.hasNext())
        {
            Statement prog = par.parseProgram();
            if (prog != null)
            {
                prog.exec(env);
            }
        }
    }
}
