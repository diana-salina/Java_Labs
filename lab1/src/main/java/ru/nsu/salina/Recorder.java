package ru.nsu.salina;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Recorder {
    private static double getFrequency(int number, int count) {
        return ((double)number) / count * 100;
    }

    private static Map<String, Integer> sortMap(Map<String, Integer> unsortedMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            if (entry.getKey().isEmpty()) continue;
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static String createLines(String word, int number, int count) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(getFrequency(number, count));
        return word + ";" + number + ";" + formattedNumber + "%\n";
    }
    public static void buildCSV(BufferedOutputStream bos, int count, Map<String, Integer> map) throws IOException {
        map = sortMap(map);
        for (String word : map.keySet()) {
            String outputStr = createLines(word, map.get(word), count);
            byte[] buffer = outputStr.getBytes();
            bos.write(buffer, 0, buffer.length);
        }
    }
}
