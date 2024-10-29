package xyz.dicedpixels.hardcover;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class Hardcover {
    public static Identifier identifier(String path) {
        return Identifier.of("hardcover", path);
    }

    public static void refreshSearchManager(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null && client.getNetworkHandler() != null) {
            var book = client.player.getRecipeBook();

            book.reload(client.world.getRecipeManager().values(), client.world.getRegistryManager());
            book.getOrderedResults().forEach(results -> results.initialize(book));
            client.getNetworkHandler().refreshSearchManager();
        }
    }
}
