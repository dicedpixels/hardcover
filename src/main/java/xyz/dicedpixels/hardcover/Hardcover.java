package xyz.dicedpixels.hardcover;

import net.minecraft.client.MinecraftClient;

public class Hardcover {
    public static void refreshSearchManager(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null && client.getNetworkHandler() != null) {
            var book = client.player.getRecipeBook();

            book.reload(client.world.getRecipeManager().values(), client.world.getRegistryManager());
            book.getOrderedResults().forEach(results -> results.initialize(book));
            client.getNetworkHandler().refreshSearchManager();
        }
    }
}
