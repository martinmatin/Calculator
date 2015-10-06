import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by kurtis on 2015-10-04.
 * Main class, creates a calculator class
 * sets the command from the arg[0]
 * executes it and gets return value
 */
public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        initLogger();
        logger.info("program starting");
        if(args.length < 1) {
            logger.error("no command line arguments found, program exiting");
            System.out.println("No arguments found");
            System.out.println("Example argument: \"add(2,2)\"");
            System.exit(0);
        }
        logger.info("arguments: " + args[0]);
        //create calculator, set the command and run
        Calculator calculator = new Calculator();
        calculator.setCommand(args[0]);
        long result = calculator.execute();
        System.out.println(result);
        logger.info("program complete with value: " + result);
    }

    //set our logger properites, file specified is in resources folder
    private static void initLogger() {
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
}