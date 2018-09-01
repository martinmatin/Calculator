
package be.ecam.calculator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by kurtis on 2015-10-04.
 * Calculator class for performing operations
 * Sets all the word and numbers into an array
 * Recursively parses each value in the array until it gets a return value
 */
public class Calculator {

    final static Logger logger = Logger.getLogger(Calculator.class);

    //command is initial string, tokens is array holding the words and numbers
    //spot keeps track of where we are in the array
    //variable array for storing calculated values from let statements, range a-z (26)
    private String command;
    private String[] tokens;
    private long result;
    private int spot;
    private long[] variables;

    public Calculator() {
        initLogger();
        logger.info("Calculator created");
    }

    public long execute() {
        logger.info("Calculator class executing");
        //check empty or null command
        if(command == null || command.isEmpty()) {
            logger.error("command string in calculator class is null or empty, program exiting");
            System.out.println("No commands found");
            System.exit(0);
        }
        //26 possible vars
        variables = new long[26];
        //split by non-word characters
        tokens = command.split("[\\W]+");
        logger.info("tokens[]: " + (Arrays.toString(tokens)));
        result = parseTokens();
        logger.info("result: " + result);
        return result;
    }

    //uses spot variable to remember position
    private long parseTokens() {
        long value = 0;
        switch (tokens[spot]) {
            case "add":
                logger.info("add detected");
                value = add();
                break;
            case "sub":
                logger.info("sub detected");
                value = sub();
                break;
            case "mult":
                logger.info("mult detected");
                value = mult();
                break;
            case "div":
                logger.info("div detected");
                value = div();
                break;
            case "mod":
                logger.info("div detected");
                value = mod();
                break;
            case "let":
                logger.info("let detected");
                value = let();
                spot++;
                value = parseTokens();
                break;
            default:
                char ch = tokens[spot].charAt(0);
                //test if variable previously set, then return that value
                if(tokens[spot].length() == 1 && (ch >= 'a' && ch <= 'z')) {
                    logger.info("array value detected: " + variables[ch-97]);
                    return variables[ch-97];
                }
                else {
                    //else number found
                    logger.info("number detected " + tokens[spot]);
                    value = Long.parseLong(tokens[spot]);
                }
                break;
        }
        return value;
    }

    private long mod() {
        spot++;
        long v1 = parseTokens();
        spot++;
        long v2 = parseTokens();
        long value = v1%v2;
        logger.info("mod statement produced: " + value);
        return value;
    }

    //let statement: let(var, value/expression, expression using var)
    //differs from other methods as it returns parseTokens for the expression using the variable
    private long let() {
        spot++;
        char val = tokens[spot].charAt(0);
        spot++;
        variables[val-97] = parseTokens();
        long value = parseTokens();
        logger.info("let statement produced: " + value);
        return value;
    }

    //add method: add(value/expression, value/expression)
    //gets the value using parseTokens at each value/expression and adding them
    //incrementing global spot variable which keeps track of our spot in the tokens[] array
    //same algorithm is used for sub, div, and mult
    private long add() {
        spot++;
        long v1 = parseTokens();
        spot++;
        long v2 = parseTokens();
        long value = v1+v2;
        logger.info("add statement produced: " + value);
        return value;
    }

    //subtraction method,
    private long sub() {
        spot++;
        long v1 = parseTokens();
        spot++;
        long v2 = parseTokens();
        long value = v1-v2;
        logger.info("sub statement produced: " + value);
        return value;
    }

    //multiplication method
    private long mult() {
        spot++;
        long v1 = parseTokens();
        spot++;
        long v2 = parseTokens();
        long value = v1*v2;
        logger.info("mult statement produced: " + value);
        return value;
    }

    //division method
    private long div() {
        spot++;
        long v1 = parseTokens();
        spot++;
        long v2 = parseTokens();
        long value = v1/v2;
        logger.info("div statement produced: " + value);
        return value;
    }

    //set up the logger
    private void initLogger() {
        try {
            Properties props = new Properties();
            FileInputStream istream = new FileInputStream("src/main/resources/log4j.properties");
            props.load(istream);
            istream.close();
            PropertyConfigurator.configure(props);
            //set the level to debug
            Logger.getRootLogger().setLevel(Level.DEBUG);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("WARNING: Unable to initialize logger\n");
        }
    }

    //setter for command string
    public void setCommand(String command) {
        this.command = command;
    }
    //getter for command string
    public String getCommand() {
        return command;
    }
}
