package xyz.dicedpixels.hardcover.client.mixin.recipebook;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(RecipeBookWidget.class)
abstract class RecipeBookWidgetMixin {
    @Shadow
    protected abstract void setOpen(boolean opened);

    @Inject(method = "initialize", at = @At("TAIL"))
    private void hardcover$toggleRecipeBookOff(
            int parentWidth,
            int parentHeight,
            MinecraftClient client,
            boolean narrow,
            AbstractRecipeScreenHandler<?> craftingScreenHandler,
            CallbackInfo ci) {
        if (!Hardcover.config().recipeBook) {
            if (craftingScreenHandler != null) {
                setOpen(false);
            }
        }
    }
}
