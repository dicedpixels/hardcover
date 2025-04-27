package xyz.dicedpixels.hardcover;

import net.fabricmc.api.ModInitializer;

import xyz.dicedpixels.hardcover.config.ConfigIO;
import xyz.dicedpixels.hardcover.feature.QuickCraft;
import xyz.dicedpixels.hardcover.feature.RecipeBook;
import xyz.dicedpixels.hardcover.feature.ResourcePack;

public final class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigIO.readFile();
        RecipeBook.init();
        QuickCraft.init();
        ResourcePack.init();
    }
}
