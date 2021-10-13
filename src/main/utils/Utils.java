package main.utils;

import java.util.HashMap;

public class Utils {
    private static HashMap<String, String> map = new HashMap<>();
    private static String buttonSource;

    static {
        map.put("dr_x", "src/db/dr_x.txt");
        map.put("dr_y", "src/db/dr_y.txt");
        map.put("dr_z_neuro", "src/db/dr_z_neuro.txt");
        map.put("dr_z_ortho", "src/db/dr_z_ortho.txt");
    }

    public static String getDBFile() {
        return map.get(buttonSource);
    }

    public static String doctorFile(String docName) {
        return map.get(docName);
    }

    public static void setButtonSource(String source) {
        buttonSource = source;
    }

    public static String getButtonSource(String file) {
        for (String dr : map.keySet()) {
            if (map.get(dr).equals(file))
                return dr;
        }
        return "";
    }
}
