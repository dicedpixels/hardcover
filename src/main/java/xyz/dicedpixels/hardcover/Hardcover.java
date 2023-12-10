package xyz.dicedpixels.hardcover;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.search.SearchManager;

import xyz.dicedpixels.hardcover.config.Configuration;
import xyz.dicedpixels.pixel.config.ConfigurationHandler;

public class Hardcover {
    private static final ConfigurationHandler HANDLER =
            ConfigurationHandler.builder().with("hardcover").build();
    private static Configuration CONFIG = HANDLER.load(Configuration.class);

    public static void init() {}

    public static Configuration configuration() {
        return CONFIG;
    }

    public static void reset() {
        CONFIG = new Configuration();
        HANDLER.save(CONFIG);
    }

    public static void save(Runnable change) {
        change.run();
        HANDLER.save(CONFIG);
    }

    public static void reloadSearchProvider(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null) {
            var book = client.player.getRecipeBook();
            book.reload(client.world.getRecipeManager().values(), client.world.getRegistryManager());
            client.reloadSearchProvider(SearchManager.RECIPE_OUTPUT, book.getOrderedResults());
            book.getOrderedResults().forEach(r -> r.initialize(book));
        }
    }
}
