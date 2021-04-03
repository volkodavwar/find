package me.volkov.find;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

// Класс, реализующий парсинг аргументов из массива строк
public class Parser {

    @Option(name = "-d", usage = "Директория поиска")
    private File directory;

    @Option(name = "-r", usage = "Искать в дочерних директориях")
    private boolean recursive;

    @Argument(required = true, usage = "Имя искомого файла")
    private String filename;

    private final CmdLineParser cmdLineParser;
    private final String[] args;

    public Parser(String[] args) {
        cmdLineParser = new CmdLineParser(this);
        this.args = args;
    }

    // Производит парсинг аргументов args, тем самым инициализируя аннотированные поля класса
    public void parse() {
        try {
            cmdLineParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(-1);
        }

        // Если флаг -d не задан, то directory = текущая директория
        if (directory == null) {
            directory = new File("").getAbsoluteFile();
        } else if (!directory.isDirectory()) {
            System.err.println("Directory (" + directory + ") not found!");
            System.exit(-1);
        }

    }


    public boolean isRecursive() {
        return recursive;
    }

    public String getFilename() {
        return filename;
    }

    public File getDirectory() {
        return directory;
    }
}
