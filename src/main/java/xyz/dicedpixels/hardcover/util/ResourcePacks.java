package xyz.dicedpixels.hardcover.util;

import net.minecraft.text.Text;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

import xyz.dicedpixels.hardcover.Hardcover;

public class ResourcePacks {
    private static final Text DARK_MODE = Text.translatable("hardcover.resourcepacks.dark_mode");

    public static void register() {
        FabricLoader.getInstance().getModContainer(Hardcover.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(Hardcover.identifierOf("default-dark-mode"), modContainer, DARK_MODE, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(Hardcover.identifierOf("vanilla-tweaks"), modContainer, DARK_MODE, ResourcePackActivationType.NORMAL);
        });
    }
}
