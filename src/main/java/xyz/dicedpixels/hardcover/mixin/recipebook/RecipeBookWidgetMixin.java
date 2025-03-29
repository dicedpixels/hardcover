package xyz.dicedpixels.hardcover.mixin.recipebook;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.AbstractRecipeScreenHandler;

import xyz.dicedpixels.hardcover.config.Configs;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin<T extends AbstractRecipeScreenHandler> {
    @Shadow
    @Final
    protected T craftingScreenHandler;

    @Inject(method = "initialize", at = @At("TAIL"))
    private void hardcover$toggleRecipeBookOff(int parentWidth, int parentHeight, MinecraftClient client, boolean narrow, CallbackInfo callbackInfo) {
        if (!Configs.recipeBook.getValue()) {
            if (craftingScreenHandler != null) {
                setOpen(false);
            }
        }
    }

    @Shadow
    protected abstract void setOpen(boolean opened);
}
