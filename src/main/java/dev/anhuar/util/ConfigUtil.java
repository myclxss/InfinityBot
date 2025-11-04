package dev.anhuar.util;

/*
 * ========================================================
 * InfinityBot - ConfigUtil.java
 *
 * @author Anhuar Ruiz | Anhuar Dev | myclass
 * @web https://anhuar.dev
 * @date 27/10/25
 *
 * License: MIT License - See LICENSE file for details.
 * Copyright (c) 2025 Anhuar Dev. All rights reserved.
 * ========================================================
 */

import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public final class ConfigUtil {

    private static final Logger LOGGER = Logger.getLogger("Metis");
    private static final String DEFAULT_ENCODING = "UTF-8";

    private static final Map<String, String[]> PATH_CACHE = new ConcurrentHashMap<>();
    private static final ThreadLocal<Yaml> YAML_INSTANCE = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);
        return new Yaml(options);
    });

    private final File file;
    private final Map<String, Object> config;
    @Getter
    private final String basePath;
    private final boolean isRoot;

    public ConfigUtil(String fileName) {
        this.basePath = "";
        this.isRoot = true;
        this.file = initializeFile(fileName);
        this.config = loadConfig();
    }

    private ConfigUtil(File file, Map<String, Object> config, String basePath) {
        this.file = file;
        this.config = config;
        this.basePath = basePath;
        this.isRoot = false;
    }

    private File initializeFile(String fileName) {
        Path configDir = Paths.get("resources");
        String filenameWithExtension = fileName + ".yml";
        File targetFile = configDir.resolve(filenameWithExtension).toFile();

        try {
            Files.createDirectories(configDir);

            if (!targetFile.exists()) {
                createFileFromResource(fileName, targetFile);
            }
        } catch (IOException e) {
            LOGGER.severe("Error inicializando archivo " + fileName + ": " + e.getMessage());
        }

        return targetFile;
    }

    private void createFileFromResource(String fileName, File targetFile) throws IOException {
        String resourceName = fileName + ".yml";
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (resourceStream != null) {
                Files.copy(resourceStream, targetFile.toPath());
            } else {
                Files.createFile(targetFile.toPath());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> loadConfig() {
        if (!file.exists() || file.length() == 0) {
            return new ConcurrentHashMap<>();
        }

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file), DEFAULT_ENCODING)) {

            Object loaded = YAML_INSTANCE.get().load(reader);
            if (loaded instanceof Map) {
                return new ConcurrentHashMap<>((Map<String, Object>) loaded);
            } else if (loaded == null) {
                LOGGER.warning("El archivo " + file.getName() + " está vacío o solo tiene comentarios.");
                return new ConcurrentHashMap<>();
            } else {
                LOGGER.severe("El archivo " + file.getName() + " no tiene un mapa como raíz. Corrige la estructura YAML.");
                return new ConcurrentHashMap<>();
            }
        } catch (IOException e) {
            LOGGER.severe("Error cargando " + file.getName() + ": " + e.getMessage());
        }

        return new ConcurrentHashMap<>();
    }

    public synchronized void save() {
        if (!isRoot) {
            throw new UnsupportedOperationException("Solo la instancia raíz puede guardar");
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(file), DEFAULT_ENCODING)) {
            YAML_INSTANCE.get().dump(config, writer);
        } catch (IOException e) {
            LOGGER.severe("Error guardando " + file.getName() + ": " + e.getMessage());
        }
    }

    public synchronized void reload() {
        if (!isRoot) {
            throw new UnsupportedOperationException("Solo la instancia raíz puede recargar");
        }

        config.clear();
        config.putAll(loadConfig());
        PATH_CACHE.clear();
    }

    public ConfigUtil getCursor(String path) {
        Objects.requireNonNull(path, "La ruta no puede ser null");
        String fullPath = buildFullPath(path);
        return new ConfigUtil(file, config, fullPath);
    }

    private String buildFullPath(String relativePath) {
        if (relativePath.isEmpty()) {
            return basePath;
        }

        return basePath.isEmpty() ? relativePath : basePath + "." + relativePath;
    }

    public boolean exists(String path) {
        return getValue(path) != null;
    }

    public boolean exists() {
        return getValue("") != null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> getKeys(String path) {
        Object value = getValue(path);
        if (value instanceof Map) {
            return ((Map<String, Object>) value).keySet();
        }
        return Collections.emptySet();
    }

    public Set<String> getKeys() {
        return getKeys("");
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        Object value = getValue(path);
        return value instanceof Boolean ? (Boolean) value : defaultValue;
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public int getInt(String path, int defaultValue) {
        Object value = getValue(path);
        return value instanceof Number ? ((Number) value).intValue() : defaultValue;
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public long getLong(String path, long defaultValue) {
        Object value = getValue(path);
        return value instanceof Number ? ((Number) value).longValue() : defaultValue;
    }

    public long getLong(String path) {
        return getLong(path, 0L);
    }

    public double getDouble(String path, double defaultValue) {
        Object value = getValue(path);
        return value instanceof Number ? ((Number) value).doubleValue() : defaultValue;
    }

    public double getDouble(String path) {
        return getDouble(path, 0.0);
    }

    public String getString(String path, String defaultValue) {
        Object value = getValue(path);
        return value != null ? value.toString() : defaultValue;
    }

    public String getString(String path) {
        return getString(path, null);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(String path) {
        Object value = getValue(path);
        if (!(value instanceof List)) {
            return Collections.emptyList();
        }

        List<Object> list = (List<Object>) value;
        return list.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .toList();
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getIntList(String path) {
        Object value = getValue(path);
        if (!(value instanceof List)) {
            return Collections.emptyList();
        }

        List<Object> list = (List<Object>) value;
        return list.stream()
                .filter(item -> item instanceof Number)
                .map(item -> ((Number) item).intValue())
                .toList();
    }

    public String getMessage(String path, String... replacements) {
        String message = getString(path);
        if (message == null || replacements.length == 0) {
            return message;
        }

        StringBuilder result = new StringBuilder(message);
        for (int i = 0; i < replacements.length - 1; i += 2) {
            String placeholder = "%" + replacements[i] + "%";
            String replacement = replacements[i + 1];
            replaceAll(result, placeholder, replacement);
        }

        return result.toString();
    }

    public String getMessage(String path, Map<String, String> replacements) {
        String message = getString(path);
        if (message == null || replacements.isEmpty()) {
            return message;
        }

        StringBuilder result = new StringBuilder(message);
        replacements.forEach((key, value) -> {
            String placeholder = "%" + key + "%";
            replaceAll(result, placeholder, value);
        });

        return result.toString();
    }

    private static void replaceAll(StringBuilder sb, String target, String replacement) {
        int index = 0;
        while ((index = sb.indexOf(target, index)) != -1) {
            sb.replace(index, index + target.length(), replacement);
            index += replacement.length();
        }
    }

    public void set(String path, Object value) {
        Objects.requireNonNull(path, "La ruta no puede ser null");
        String fullPath = buildFullPath(path);

        if (fullPath.isEmpty()) {
            handleRootSet(value);
            return;
        }

        String[] pathParts = parsePathCached(fullPath);
        setNestedValue(pathParts, value);
    }

    public void set(Object value) {
        set("", value);
    }

    @SuppressWarnings("unchecked")
    private void handleRootSet(Object value) {
        if (value instanceof Map) {
            config.clear();
            config.putAll((Map<String, Object>) value);
        }
    }

    @SuppressWarnings("unchecked")
    private void setNestedValue(String[] pathParts, Object value) {
        Map<String, Object> current = config;

        for (int i = 0; i < pathParts.length - 1; i++) {
            String part = pathParts[i];
            current = (Map<String, Object>) current.computeIfAbsent(part, k -> new ConcurrentHashMap<>());
        }

        String lastKey = pathParts[pathParts.length - 1];
        if (value == null) {
            current.remove(lastKey);
        } else {
            current.put(lastKey, value);
        }
    }

    public void remove(String path) {
        set(path, null);
    }

    public boolean isEmpty(String path) {
        Object value = getValue(path);

        if (value instanceof Map) return ((Map<?, ?>) value).isEmpty();
        if (value instanceof Collection) return ((Collection<?>) value).isEmpty();
        if (value instanceof String) return ((String) value).trim().isEmpty();

        return value == null;
    }

    public int size(String path) {
        Object value = getValue(path);

        if (value instanceof Map) return ((Map<?, ?>) value).size();
        if (value instanceof Collection) return ((Collection<?>) value).size();
        if (value instanceof String) return ((String) value).length();

        return 0;
    }

    public void copySection(String fromPath, String toPath) {
        Object value = getValue(fromPath);
        if (value != null) {
            set(toPath, deepCopy(value));
        }
    }

    private Object getValue(String path) {
        String fullPath = buildFullPath(path);

        if (fullPath.isEmpty()) {
            return config;
        }

        String[] pathParts = parsePathCached(fullPath);
        return getNestedValue(pathParts);
    }

    private String[] parsePathCached(String path) {
        return PATH_CACHE.computeIfAbsent(path, p -> p.split("\\."));
    }

    @SuppressWarnings("unchecked")
    private Object getNestedValue(String[] pathParts) {
        Map<String, Object> current = config;

        for (int i = 0; i < pathParts.length - 1; i++) {
            Object value = current.get(pathParts[i]);
            if (!(value instanceof Map)) {
                return null;
            }
            current = (Map<String, Object>) value;
        }

        return current.get(pathParts[pathParts.length - 1]);
    }

    @SuppressWarnings("unchecked")
    private Object deepCopy(Object obj) {
        if (obj instanceof Map) {
            Map<String, Object> copy = new ConcurrentHashMap<>();
            ((Map<String, Object>) obj).forEach((k, v) -> copy.put(k, deepCopy(v)));
            return copy;
        }

        if (obj instanceof List) {
            return ((List<Object>) obj).stream().map(this::deepCopy).toList();
        }

        return obj;
    }

    public Map<String, Object> getConfig() {
        return Collections.unmodifiableMap(config);
    }

    @Override
    public String toString() {
        return String.format("ConfigUtil{file=%s, basePath='%s', isRoot=%s}",
                file.getName(), basePath, isRoot);
    }
}