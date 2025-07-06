package xyz.dicedpixels.hardcover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import net.fabricmc.loader.api.FabricLoader;

public final class Hardcover {
    public static final String MOD_ID = "hardcover";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Text TITLE = Text.literal("Hardcover");
    private static String version = "0.0.0";

    public static Text getVersion() {
        return Text.literal(version).styled(style -> style.withFormatting(Formatting.GRAY));
    }

    public static void init() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> version = container.getMetadata().getVersion().getFriendlyString());
    }
}
