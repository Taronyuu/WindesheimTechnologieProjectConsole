package nl.windesheim.codeparser.console;

import nl.windesheim.codeparser.FileAnalysisProvider;
import nl.windesheim.codeparser.patterns.IDesignPattern;
import nl.windesheim.reporting.Report;
import nl.windesheim.reporting.builders.CodeReportBuilder;
import nl.windesheim.reporting.components.CodeReport;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * App for the Console application code parser.
 */
public final class ConsoleApp {

    /**
     * Private constructor.
     *
     * @param args default arguments
     */
    private ConsoleApp(final String[] args) {
        File file;

        if (args.length == 0) {
            System.out.println("Files in argument given.");
        } else {
            for (String path : args) {
                file = new File(path);
                List<IDesignPattern> patterns = analyze(file);
                report(patterns);
            }
        }
    }

    /**
     * Main function for the console.
     *
     * @param args default arguments
     */
    public static void main(final String[] args) {
        new ConsoleApp(args);
    }

    /**
     * Analyze the design patterns in this file.
     *
     * @param file
     * @return
     */
    private List<IDesignPattern> analyze(final File file) {
        FileAnalysisProvider analysis = FileAnalysisProvider
                .getConfiguredFileAnalysisProvider();

        try {
            if (file.isDirectory()) {
                return analysis.analyzeDirectory(file.toPath());
            } else {
                return analysis.analyzeFile(file.toURL());
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }

        return null;
    }

    /**
     * Report the design patterns.
     *
     * @param patterns found design patterns.
     */
    private void report(final List<IDesignPattern> patterns) {
        CodeReportBuilder codeReportBuilder = Report.create();
        for (IDesignPattern p : patterns) {
            codeReportBuilder.addFoundPatternBuilder(Report.getMapper().getBuilder(p));
        }

        CodeReport codeReport = codeReportBuilder.buildReport();
        System.out.println(codeReport.getReport());
    }
}
