package io.github.dicedpixels.hardcover.mixin.mousewheelscrolling;

import io.github.dicedpixels.hardcover.Hardcover;
import io.github.dicedpixels.hardcover.interfaces.HardcoverMouseScrolled;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookResults;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.text.Text;

@Mixin(HandledScreen.class)
abstract class MixinHandledScreen extends Screen {
	protected MixinHandledScreen(Text title) {
		super(title);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		if (Hardcover.CONFIG.mouseWheelScrolling) {
			if (this instanceof RecipeBookProvider recipeBookProvider) {
				RecipeBookWidget widget = recipeBookProvider.getRecipeBookWidget();
				RecipeBookResults recipesArea = ((AccessorRecipeBookWidget) widget).getRecipesArea();

				int x = (((AccessorRecipeBookWidget) widget).getParentWidth() - 147) / 2 - ((AccessorRecipeBookWidget) widget).getLeftOffset();
				var y = (((AccessorRecipeBookWidget) widget).getParentHeight() - 166) / 2;

				if (mouseX >= x && mouseY >= y && mouseX < x + 147 && mouseY < 166 + 31) {
					((HardcoverMouseScrolled) recipesArea).mouseScrolled(mouseX, mouseY, amount);
				}

				return true;
			}
		}
		return super.mouseScrolled(mouseX, mouseY, amount);
	}
}
