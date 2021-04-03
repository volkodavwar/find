package me.volkov.find;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

// Класс, реализующий поиск файла
public class Find {

    private final File directory;
    private final boolean recursive;

    public Find(File directory, boolean recursive) {
        if (directory == null) {
            throw new IllegalArgumentException("Parameter directory must not be null!");
        }

        this.directory = directory;
        this.recursive = recursive;
    }

    // Возвращает список найденных файлов с именем filename внутри директории directory
    public List<File> find(String filename) {
        List<File> found = new ArrayList<>(1);

        forEachDirectory(directory, recursive, file -> {
            File[] innerFiles = file.listFiles();
            if (innerFiles != null) {
                Arrays.stream(innerFiles).forEach(innerFile -> {
                    if (innerFile.isFile() && filename.equals(innerFile.getName())) {
                        found.add(innerFile);
                    }
                });
            }
            return null;
        });

        return found;
    }

    // функция, вызывающая функцию work для директории [и для всех поддиректорий, если recursive = true] и передающая данную директорию в качестве параметра
    private void forEachDirectory(File directory, boolean recursive, Function<File, Void> work) {
        if (recursive) {
            work.apply(directory);
            File[] innerFiles = directory.listFiles();
            if (innerFiles != null) {
                Arrays.stream(innerFiles).forEach(file -> {
                    if (file.isDirectory()) {
                        forEachDirectory(file, true, work);
                    }
                });
            }

        } else {
            work.apply(directory);
        }
    }
}
