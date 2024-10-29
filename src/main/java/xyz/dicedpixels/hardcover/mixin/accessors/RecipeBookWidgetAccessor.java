package xyz.dicedpixels.hardcover.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {
    @Accessor("leftOffset")
    int hardcover$leftOffset();

    @Accessor("parentHeight")
    int hardcover$parentHeight();

    @Accessor("parentWidth")
    int hardcover$parentWidth();

    @Accessor("recipesArea")
    RecipeBookResults hardcover$recipesArea();
}
