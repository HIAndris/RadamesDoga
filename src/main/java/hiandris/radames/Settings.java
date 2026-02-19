package hiandris.radames;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Provides personal settings from user home
 * <p> KEYS:
 *     language
 *     windowWidth
 *     windowHeight
 *     darkMode
 * </p>
 */
public class Settings {
    private static final String APP_DIR_NAME = ".radamesdoga";
    private static final String FILE_NAME = "settings.properties";
    private final Properties props = new Properties();
    private final Path configPath;

    private static Settings instance;

    private Settings() {
        // Search for user home
        String userHome = System.getProperty("user.home");
        Path configDir = Paths.get(userHome, APP_DIR_NAME);
        this.configPath = configDir.resolve(FILE_NAME);

        // Build path
        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Couldn't build config dir path: " + e.getMessage());
        }

        load();
    }

    /**
     * Gives back the instance
     * @return Instance
     */
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private boolean load() {
        // Load defaults
        setDefaults();

        // If config file exists, load
        if (Files.exists(configPath)) {
            try (InputStream input = new FileInputStream(configPath.toFile())) {
                props.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't load settings: " + e.getMessage());
            }
        }
        return true;
    }

    /**
     * Restores the unsaved settings
     * @return True if restored successfully, false if failure
     */
    public boolean restore() {
        props.clear();
        return load();
    }

    /**
     * Save the current settings
     * @return True if saved successfully, false if failure
     */
    public boolean save() {
        try (OutputStream output = new FileOutputStream(configPath.toFile())) {
            props.store(output, "RadamesDoga Settings");
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save settings: " + e.getMessage());
        }
    }

    /**
     * Default values
     */
    private void setDefaults() {
        if (!props.containsKey("language")) props.setProperty("language", "");
        if (!props.containsKey("windowWidth")) props.setProperty("windowWidth", "");
        if (!props.containsKey("windowHeight")) props.setProperty("windowHeight", "");
        if (!props.containsKey("darkMode")) props.setProperty("darkMode", "true");
    }

    /**
     * Get the value for a key
     * @param key Key name
     * @return Value
     */
    public String get(String key) {
        return props.getProperty(key);
    }

    /**
     * Set the value for a key
     * @param key Key name
     * @param value New value
     */
    public void set(String key, String value) {
        props.setProperty(key, value);
    }

    /**
     * Get the boolean value for a key
     * @param key Key name
     * @return Boolean value
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }

    /**
     * Get the integer value for a key
     * @param key Key name
     * @throws NumberFormatException If the setting format is wrong
     * @return Integer value
     */
    public int getInt(String key) throws NumberFormatException {
        return Integer.parseInt(props.getProperty(key));
    }

    /**
     * Get the double value for a key
     * @param key Key name
     * @throws NumberFormatException If the setting format is wrong
     * @return Double value
     */
    public double getDouble(String key) {
        return Double.parseDouble(props.getProperty(key));
    }
}