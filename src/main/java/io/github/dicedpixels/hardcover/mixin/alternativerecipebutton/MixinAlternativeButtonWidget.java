package io.github.dicedpixels.hardcover.mixin.alternativerecipebutton;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.dicedpixels.hardcover.Hardcover;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeAlternativesWidget.AlternativeButtonWidget.InputSlot;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(AlternativeButtonWidget.class)
abstract class MixinAlternativeButtonWidget extends ClickableWidget implements RecipeGridAligner<Ingredient> {
	private static final Identifier BACKGROUND_TEXTURE = new Identifier("hardcover:textures/gui/recipe_book.png");
	private static final MinecraftClient client = MinecraftClient.getInstance();

	@Final
	@Shadow
	protected List<InputSlot> slots;
	@Final
	@Shadow
	Recipe<?> recipe;
	@Shadow(aliases = "field_3113")
	RecipeAlternativesWidget parent;
	@Final
	@Shadow
	private boolean craftable;

	public MixinAlternativeButtonWidget(int x, int y, int width, int height, Text message) {
		super(x, y, width, height, message);
	}

	private static Text hardcover$getIngredientList(Recipe<?> recipe) {
		Map<Item, List<Ingredient>> collect = recipe.getIngredients().stream().filter(i -> !i.isEmpty()).collect(groupingBy(g -> {
			Optional<ItemStack> stack = Arrays.stream(g.getMatchingStacks()).findFirst();
			return stack.isPresent() ? stack.get().getItem() : Items.AIR;
		}));
		Map<String, Integer> collected = collect.entrySet().stream().filter(e -> e.getKey() != Items.AIR).collect(toMap(e -> e.getKey().getTranslationKey(), e -> e.getValue().size()));
		MutableText text = Text.empty();
		int i = 0;
		for (Map.Entry<String, Integer> entry : collected.entrySet()) {
			if (i == collected.size() - 1) {
				text.append(Text.translatable(entry.getKey())).append(" x").append(String.valueOf(entry.getValue()));
				continue;
			}
			text.append(Text.translatable(entry.getKey())).append(" x").append(String.valueOf(entry.getValue())).append("\n");
			i++;
		}
		return text;
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void hardcover$MixinAlternativeButtonWidgetConstructor(RecipeAlternativesWidget recipeAlternativesWidget, int x, int y, Recipe<?> recipe, boolean craftable, CallbackInfo ci) {
		setTooltip(Tooltip.of(hardcover$getIngredientList(recipe)));
	}

	@Inject(method = "renderButton", at = @At("HEAD"), cancellable = true)
	private void hardcover$renderCustomAlternativeButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (Hardcover.CONFIG.alternativeRecipeButton && client != null && client.world != null) {
			RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
			int v = 0;
			int u = 0;
			if (!this.craftable) {
				u += 26;
			}
			if (this.isHovered()) {
				v += 26;
			}
			if (Hardcover.CONFIG.darkMode) {
				v += 52;
			}
			drawTexture(matrices, this.getX(), this.getY(), u, v, this.width, this.height);
			matrices.push();
			matrices.translate(0, 0, -35);
			client.getItemRenderer().renderInGuiWithOverrides(matrices, this.recipe.getOutput(client.world.getRegistryManager()), this.getX() + 4, this.getY() + 4);
			matrices.pop();
			ci.cancel();
		}
	}
}
