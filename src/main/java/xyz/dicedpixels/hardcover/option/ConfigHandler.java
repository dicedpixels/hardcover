package xyz.dicedpixels.hardcover.option;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("hardcover.json");

    public static Config load() {
        if (Files.exists(PATH)) {
            try (var reader = Files.newBufferedReader(PATH)) {
                return GSON.fromJson(reader, Config.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new Config();
    }

    public static void save(Config config) {
        try (var writer = Files.newBufferedWriter(PATH)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
