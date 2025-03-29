package xyz.dicedpixels.hardcover.config;

import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import net.fabricmc.loader.api.FabricLoader;

import xyz.dicedpixels.hardcover.Hardcover;

public final class ConfigIO {
    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(BooleanConfig.class, new BooleanConfig.Serializer())
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve(String.format("%s.json", Hardcover.MOD_ID));

    public static void readFile() {
        if (Files.exists(PATH)) {
            try (var reader = Files.newBufferedReader(PATH)) {
                var jsonElement = JsonParser.parseReader(reader);

                for (var field : Configs.class.getDeclaredFields()) {
                    var value = jsonElement.getAsJsonObject().get(field.getName());

                    if (value != null) {
                        var fieldType = field.getType();

                        if (BooleanConfig.class.isAssignableFrom(fieldType)) {
                            var booleanValue = value.getAsBoolean();
                            ((BooleanConfig) field.get(null)).setValue(booleanValue);
                        }
                    }
                }
            } catch (Exception exception) {
                Hardcover.LOGGER.error("Failed to load configs at '{}'. Using default values.", PATH, exception);
            }
        } else {
            writeFile();
        }
    }

    public static void writeFile() {
        try (var writer = Files.newBufferedWriter(PATH)) {
            GSON.toJson(new Configs(), writer);
        } catch (Exception exception) {
            Hardcover.LOGGER.error("Failed to save configs to '{}'.", PATH, exception);
        }
    }
}
