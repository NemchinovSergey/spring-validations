package com.nsergey.validation.testing;

import lombok.SneakyThrows;

import java.io.InputStream;

public abstract class TestResourceLoader {

    /**
     * Загружает содержимое файла из ресурсов.
     * Название файла может быть относительно класса или относительно корня ресурсов.
     * <ul>
     * <li>"filename.json" - относительно класса clazz (в том же пакете)
     * <li>"/filename.json" - относительно корня ресурсов
     * </ul>
     * @param clazz класс, относительно которого искать ресурс
     * @param filename название файла
     * @return содержимое файла
     */
    @SneakyThrows
    public static String loadFileContent(Class<?> clazz, String filename) {
        InputStream resourceAsStream = clazz.getResourceAsStream(filename);
        return new String(resourceAsStream.readAllBytes());
    }

}
