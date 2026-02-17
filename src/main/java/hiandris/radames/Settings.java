package hiandris.radames;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Provides settings from user home
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
            System.err.println(e.getMessage());
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
                System.err.println("Failed to load settings: " + e.getMessage());
                return false;
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
            System.err.println("Error while saving: " + e.getMessage());
            return false;
        }
    }

    /**
     * Default values
     */
    private void setDefaults() {
        if (!props.containsKey("language")) props.setProperty("language", "");
        if (!props.containsKey("windowWidth")) props.setProperty("windowWidth", "");
        if (!props.containsKey("windowHeight")) props.setProperty("windowHeight", "");
        if (!props.containsKey("darkMode")) props.setProperty("darkMode", "false");
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
     * @return Integer value
     */
    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}