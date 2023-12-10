package xyz.dicedpixels.hardcover.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;

@Mixin(RecipeAlternativesWidget.class)
public interface RecipeAlternativesWidgetAccessor {
    @Accessor("time")
    float hardcover$time();

    @Accessor("client")
    MinecraftClient hardcover$client();
}
