package xyz.dicedpixels.hardcover;

import net.fabricmc.api.ModInitializer;

import xyz.dicedpixels.hardcover.config.ConfigManager;
import xyz.dicedpixels.hardcover.feature.QuickCraft;
import xyz.dicedpixels.hardcover.feature.RecipeBook;
import xyz.dicedpixels.hardcover.feature.ResourcePack;

public final class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        Hardcover.init();
        ConfigManager.readFile();
        RecipeBook.init();
        QuickCraft.init();
        ResourcePack.init();
    }
}
