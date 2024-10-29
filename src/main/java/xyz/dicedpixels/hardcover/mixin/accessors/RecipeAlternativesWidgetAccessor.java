package xyz.dicedpixels.hardcover.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;

@Mixin(RecipeAlternativesWidget.class)
public interface RecipeAlternativesWidgetAccessor {
    @Accessor("client")
    MinecraftClient hardcover$client();

    @Accessor("time")
    float hardcover$time();
}
