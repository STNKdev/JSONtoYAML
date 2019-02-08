import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

public class JSONtoYAML {

    public static void main (String args[]) {

        String pathIn;
        String pathOut;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            if (args.length == 2) {
                pathIn = checkPath(args[0]);
                pathOut = checkPath(args[1]);
            } else if (args.length == 1) {
                pathIn = checkPath(args[0]);
                pathOut = "";
            } else {
                p("Введите путь к файлу JSON:");
                pathIn = checkPath(br.readLine());
                p("Введите куда сохранить файл (нажатие Enter сохранит рядом с файлом JSON):");
                pathOut = checkPath(br.readLine());
            }

            convertJSONtoYAML(pathIn, pathOut);
        } catch (IOException err) {
            p("Ошибка: " + err);
            err.printStackTrace();
        }

    }

    private static String checkPath (String path) {
        File file = new File(path.trim());
        if (file.isFile() && file.canRead()) {
            p(file.getName().toLowerCase().endsWith(".json") ? "Является файлом JSON" : "Не является файлом JSON");
            p("Имя файла: " + file.getName());
            p("Путь: " + file.getParent());
            p("Абсолютный путь: " + file.getAbsolutePath());
            return file.getAbsolutePath();
        } else if (file.isDirectory()) {
            p("Директория: " + file.getPath());
            return file.getAbsolutePath();
        } else {
            p("Пустое значение");
            //p("Вы ввели: " + path.nextLine());
        }

        return "";
    }

    private static void p (String s) {
        System.out.println(s);
    }

    private static void convertJSONtoYAML (String pathIn, String pathOut) {
        try {
            File file = new File(pathIn);
            if (pathOut.equals("")) {
                pathOut = file.getParent() + File.separator + file.getName().toLowerCase().replace("json", "yml");
            } else {
                pathOut = pathOut.concat(File.separator + file.getName().toLowerCase().replace("json", "yml"));
            }
            //Парсим файл JSON
            JsonNode jsonNodeTree = new ObjectMapper().readTree(file);
            //Конвентируем объект JSON в строку YAML объекта
            //String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
            //Конвертируем JSON в YAML и сохраняем в файле
            new YAMLMapper().writeValue(new File(pathOut), jsonNodeTree);

        } catch (IOException err) {
            p("Ошибка ввода-вывода: " + err);
            err.printStackTrace();
        }
    }
}