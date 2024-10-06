package io.github.dicedpixels.hardcover.mixin.mousewheelscrolling;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;

@Mixin(RecipeBookWidget.class)
interface AccessorRecipeBookWidget {
	@Accessor
	RecipeBookResults getRecipesArea();

	@Accessor
	int getLeftOffset();

	@Accessor
	int getParentWidth();

	@Accessor
	int getParentHeight();
}
