package xyz.dicedpixels.hardcover.mixin.autocloserecipebook;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;

import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.mixin.accessors.RecipeBookScreenAccessor;
import xyz.dicedpixels.hardcover.mixin.invokers.RecipeBookWidgetInvoker;

@Mixin(HandledScreen.class)
abstract class HandledScreenMixin {
    @Inject(method = "close", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;close()V"))
    private void hardcover$closeRecipeBook(CallbackInfo callbackInfo) {
        if (Configs.autoCloseRecipeBook.getValue()) {
            if (((Object) this) instanceof RecipeBookScreen<?> screen) {
                var recipeBook = ((RecipeBookScreenAccessor<?>) screen).hardcover$getRecipeBook();

                ((RecipeBookWidgetInvoker) recipeBook).hardcover$setOpen(false);
            }
        }
    }
}
