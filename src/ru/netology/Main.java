package ru.netology;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    private static GameProgress x;

    public static void main(String[] args) {

        GameProgress games1 = new GameProgress(3, 4, 5, 65.34);
        GameProgress games2 = new GameProgress(5, 8, 1, 25.12);
        GameProgress games3 = new GameProgress(1, 1, 7, 105.89);

        saveGame("D://Games/savegames/save1.dat", games1);
        saveGame("D://Games/savegames/save2.dat", games2);
        saveGame("D://Games/savegames/save3.dat", games3);

        String[] file = {"D://Games/savegames/save1.dat", "D://Games/savegames/save2.dat", "D://Games/savegames/save3.dat"};
        zipFiles(file, "D://Games/savegames/zip.zip");

        for (int i = 0; i < file.length; i++) {
            try {
                Path path = Path.of(file[i]);
                Files.delete(path);
            } catch (IOException x) {
                System.err.println(x);
            }
        }

    }

    public static void saveGame(String way, GameProgress number) { // в качестве аргумента принимаем путь и номер сохранения
        try (FileOutputStream fos = new FileOutputStream(way);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(number);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

//        String a = way.toString();
//        System.out.println(a);
    }

    public static void zipFiles(String[] file, String zipFile) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (int i = 0; i < file.length; i++) {
                FileInputStream fis = new FileInputStream(file[i]); // имя файла который хотим записать
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
