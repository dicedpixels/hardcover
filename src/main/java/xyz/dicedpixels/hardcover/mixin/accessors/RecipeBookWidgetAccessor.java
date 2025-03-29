package xyz.dicedpixels.hardcover.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {
    @Accessor("leftOffset")
    int hardcover$getLeftOffset();

    @Accessor("parentHeight")
    int hardcover$getParentHeight();

    @Accessor("parentWidth")
    int hardcover$getParentWidth();

    @Accessor("recipesArea")
    RecipeBookResults hardcover$getRecipesArea();
}
