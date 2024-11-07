package xyz.dicedpixels.hardcover;

import net.minecraft.client.MinecraftClient;

public class Hardcover {
    public static void refreshSearchManager(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null && client.getNetworkHandler() != null) {
            client.player.getRecipeBook().refresh();
            client.getNetworkHandler().refreshSearchManager();
        }
    }
}
