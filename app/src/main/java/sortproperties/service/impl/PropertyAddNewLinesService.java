package sortproperties.service.impl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import sortproperties.App;
import sortproperties.service.PropertyService;

/**
 * PropertyAddNewLinesService
 */
public class PropertyAddNewLinesService implements PropertyService {

    @Override
    public void execute(Path messagesPath) {
        try {
            List<String> addNew = this.readFromAddNew();
            List<String> newLinesToAdd = !addNew.isEmpty() ? convertToUnicodeCodePoints(addNew) : Collections.emptyList();

            // add in new lines
            List<String> originalFile = Files.readAllLines(messagesPath);
            originalFile.addAll(newLinesToAdd);
            try (PrintWriter pw = new PrintWriter(new FileWriter(messagesPath.toString()))) {
                pw.print(String.join("\n", originalFile));
                pw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readFromAddNew() throws IOException {
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(App.class.getClassLoader().getResourceAsStream("add.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        }
        return content;
    }

    private String convertLineToUnicodeCodePoints(String line) {
        String[] arr = line.split("=");
        StringBuilder sb = new StringBuilder();
        sb.append(arr[0]).append("=");
        char[] chars = arr[1].toCharArray();
        for (char c : chars) {
            sb.append("\\u").append(Integer.toHexString(c | 0x10000).toUpperCase().substring(1));
        }

        return sb.toString();
    }

    private List<String> convertToUnicodeCodePoints(List<String> inputList) {
        return inputList.stream().map(this::convertLineToUnicodeCodePoints).collect(Collectors.toList());
    }
}
