package ru.ob11to.inputoutput.ionio.task;

import ru.ob11to.inputoutput.ionio.Search;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void packFiles(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File file : sources) {
                zip.putNextEntry(new ZipEntry(file.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Some arguments are absent.");
        }
        ArgsName argsName = ArgsName.of(args);
        File directory = new File(argsName.get("d"));
        File exclude = new File(argsName.get("e"));
        File target = new File(argsName.get("o"));
        if (!directory.exists()) {
            throw new IllegalArgumentException("The directory %s does not exist.");
        }
        List<File> sources = Search.search(directory.toPath(),
                        path -> !path.toFile().getName().endsWith(exclude.getName()))
                .stream().map(Path::toFile).collect(Collectors.toList());
        packFiles(sources, target);
    }
}