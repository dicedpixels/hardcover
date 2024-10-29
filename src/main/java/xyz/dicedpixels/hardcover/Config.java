package xyz.dicedpixels.hardcover;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("hardcover.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Config config;

    public boolean alternativeRecipeButton;
    public boolean bounce = true;
    public boolean centeredInventory;
    public boolean circularScrolling;
    public boolean darkMode = false;
    public boolean mouseWheelScrolling = true;
    public boolean recipeBook = true;
    public boolean ungroupRecipes;
    public boolean unlockAllRecipes;

    private Config() {}

    public static Config instance() {
        return config == null ? config = load() : config;
    }

    private static Config load() {
        if (Files.exists(CONFIG_FILE)) {
            try (var reader = Files.newBufferedReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, Config.class);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        return new Config();
    }

    public void save() {
        try (var writer = Files.newBufferedWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
