package xyz.dicedpixels.hardcover;

import xyz.dicedpixels.hardcover.option.Config;
import xyz.dicedpixels.hardcover.option.ConfigHandler;

public class Hardcover {
    public static final String MOD_ID = "hardcover";
    private static Config config;

    public static void init(Config config) {
        Hardcover.config = config;
    }

    public static Config config() {
        return config;
    }

    public static void saveConfig() {
        ConfigHandler.save(config);
    }
}
