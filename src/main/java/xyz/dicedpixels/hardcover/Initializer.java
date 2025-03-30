package xyz.dicedpixels.hardcover;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;

import xyz.dicedpixels.hardcover.config.ConfigIO;
import xyz.dicedpixels.hardcover.util.QuickCraft;
import xyz.dicedpixels.hardcover.util.ResourcePacks;
import xyz.dicedpixels.hardcover.util.VersionInfo;

public final class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(Hardcover.MOD_ID).ifPresent(VersionInfo::setVersion);
        ConfigIO.readFile();
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> Hardcover.hideRecipeBook(screen));
        ClientTickEvents.START_CLIENT_TICK.register(QuickCraft::scheduleCraft);
        QuickCraft.init();
        ResourcePacks.register();
    }
}
