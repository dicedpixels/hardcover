package xyz.dicedpixels.hardcover.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import xyz.dicedpixels.hardcover.screen.ConfigurationScreen;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigurationScreen::new;
    }
}
