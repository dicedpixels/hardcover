package xyz.dicedpixels.hardcover.mixin.bounce;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.recipe.book.RecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.dicedpixels.hardcover.Hardcover;

@Mixin(RecipeBook.class)
abstract class RecipeBookMixin {
    @ModifyReturnValue(method = "shouldDisplay", at = @At("RETURN"))
    private boolean hardcover$toggleBounce(boolean original) {
        return Hardcover.config().bounce && original;
    }
}
