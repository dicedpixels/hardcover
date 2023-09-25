package xyz.dicedpixels.hardcover;

import net.fabricmc.api.ModInitializer;
import xyz.dicedpixels.hardcover.option.ConfigHandler;

public class Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        Hardcover.init(ConfigHandler.load());
    }
}
