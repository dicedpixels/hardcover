package xyz.dicedpixels.hardcover.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.AbstractRecipeScreenHandler;

@Mixin(RecipeBookScreen.class)
public interface RecipeBookScreenAccessor<T extends AbstractRecipeScreenHandler> {
    @Accessor("recipeBook")
    RecipeBookWidget<T> hardcover$recipeBook();
}
