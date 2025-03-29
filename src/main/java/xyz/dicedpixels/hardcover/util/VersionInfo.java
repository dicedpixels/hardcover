package xyz.dicedpixels.hardcover.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.fabricmc.loader.api.ModContainer;

import xyz.dicedpixels.hardcover.Hardcover;

public class VersionInfo {
    private static String version = "";

    public static Text getTitleWithVersion() {
        return Text.translatable("hardcover.gui.config.title_template", Hardcover.TITLE, Text.literal(version).formatted(Formatting.DARK_GRAY));
    }

    public static void setVersion(ModContainer container) {
        VersionInfo.version = container.getMetadata().getVersion().getFriendlyString();
    }
}
