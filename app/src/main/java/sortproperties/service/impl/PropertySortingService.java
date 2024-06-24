package sortproperties.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sortproperties.service.PropertyService;

/**
 * PropertySortingService
 */
public class PropertySortingService implements PropertyService {

    @Override
    public void execute(Path messagesPath) {
        try {
            String sorted = sortProperties(messagesPath);
            try (PrintWriter pw = new PrintWriter(new FileWriter(messagesPath.toString()))) {
                pw.print(sorted);
                pw.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String sortProperties(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);

        Map<String, String> map = new HashMap<>();
        for (String line : lines) {
            String[] arr = line.split("=", 2);
            if (arr.length >= 2 && !map.containsKey(arr[0]))
                map.put(arr[0], arr[1]);
        }

        List<String> keys = map.entrySet().stream().map(Map.Entry<String, String>::getKey).collect(Collectors.toList());
        keys.sort(Comparable::compareTo);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(map.get(key)).append("\n");
        }

        List<String> sorted = Arrays.stream(sb.toString().split("\n")).collect(Collectors.toList());

        // put in original commented lines
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!sorted.contains(line)) {
                sorted.add(i, line);
            }
        }

        String result = String.join("\n", sorted);
        return result;
    }
}
