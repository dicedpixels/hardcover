package xyz.dicedpixels.hardcover.mixin.invokers;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetInvoker {
    @Invoker("setOpen")
    void hardcover$setOpen(boolean opened);
}
