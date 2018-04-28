package nl.windesheim.codeparser.console;

import nl.windesheim.codeparser.FileAnalysisProvider;
import nl.windesheim.codeparser.patterns.IDesignPattern;
import nl.windesheim.reporting.Report;
import nl.windesheim.reporting.builders.CodeReportBuilder;
import nl.windesheim.reporting.components.CodeReport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * App for the Console application code parser.
 */
public final class App {

    /**
     * Private constructor.
     *
     * @param args default arguments
     */
    private App(final String[] args) {
        if (args.length == 0) {
            System.out.println("Files in argument given.");
        } else {
            for (String path : args) {
                File file = new File(path);
                FileAnalysisProvider analysis = FileAnalysisProvider
                        .getConfiguredFileAnalysisProvider();

                ArrayList<IDesignPattern> patterns = new ArrayList<>();

                try {
                    if (file.isDirectory()) {
                        patterns = analysis.analyzeDirectory(file.toPath());
                    } else {
                        patterns = analysis.analyzeFile(file.toURL());
                    }
                } catch (IOException ex) {
                    System.out.println("Something went wrong: "
                            + ex.getMessage());
                }

                CodeReportBuilder codeReportBuilder = Report.create();
                for (IDesignPattern p : patterns) {
                    try {
                        codeReportBuilder.addFoundPatternBuilder(Report.getMapper().getBuilder(p));
                    } catch (NullPointerException ex) {
                        System.out.println("Something went wrong: " + ex.getMessage());
                    }
                }

                CodeReport codeReport = codeReportBuilder.buildReport();
                System.out.println(codeReport.getReport());
            }
        }
    }

    /**
     * Main function for the console.
     *
     * @param args default arguments
     */
    public static void main(final String[] args) {
        new App(args);
    }
}
