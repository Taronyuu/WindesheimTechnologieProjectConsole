package nl.windesheim.codeparser.console;

/**
 * Hello world!
 */
public final class App {

    /**
     * Private constructor.
     */
    private App() {
    }

    /**
     * main function for the console.
     *
     * @param args default arguments
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println("Files in argument given.");
        } else {
            for (String file : args) {
                /* TODO : pass this file to the parser library */
                System.out.println(file);
            }
        }
    }
}
