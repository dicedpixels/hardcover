package xyz.dicedpixels.hardcover.feature;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

import xyz.dicedpixels.hardcover.Hardcover;

public final class ResourcePack {
    private static final Text DEFAULT_DARK = Text.literal("Hardcover: Default Dark Mode");
    private static final Text VANILLA_TWEAKS = Text.literal("Hardcover: Vanilla Tweaks");

    public static void init() {
        FabricLoader.getInstance().getModContainer(Hardcover.MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of(Hardcover.MOD_ID, "default_dark_mode"), container, DEFAULT_DARK, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of(Hardcover.MOD_ID, "vanilla_tweaks"), container, VANILLA_TWEAKS, ResourcePackActivationType.NORMAL);
        });
    }
}
