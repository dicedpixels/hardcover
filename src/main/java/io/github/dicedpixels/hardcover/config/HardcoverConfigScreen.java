package io.github.dicedpixels.hardcover.config;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import io.github.dicedpixels.hardcover.Hardcover;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class HardcoverConfigScreen extends Screen {
	private final Screen parentScreen;
	private ConfigListWidget listWidget;

	public HardcoverConfigScreen(Screen parentScreen) {
		super(Text.translatable("hardcover.config.title"));
		this.parentScreen = parentScreen;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context);
		this.listWidget.render(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
		super.render(context, mouseX, mouseY, delta);
	}

	@Override
	protected void init() {
		this.listWidget = new ConfigListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.addSelectableChild(this.listWidget);
		this.addDrawableChild(ButtonWidget.builder(Text.translatable("hardcover.config.done"), button -> {
			Hardcover.CONFIG.save();
			if (this.client != null) {
				this.client.setScreen(this.parentScreen);
			}
		}).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
		this.listWidget.addEntry(createCyclingButtonWidget(Hardcover.CONFIG.recipeBook, "recipe-book", (b, v) -> Hardcover.CONFIG.recipeBook = v));
		this.listWidget.addEntry(createCyclingButtonWidget(Hardcover.CONFIG.unlockAllRecipes, "unlock-all-recipes", (b, v) -> Hardcover.CONFIG.unlockAllRecipes = v), createCyclingButtonWidget(Hardcover.CONFIG.ungroupRecipes, "ungroup-recipes", (b, v) -> {
			Hardcover.CONFIG.ungroupRecipes = v;
			if (this.client != null && this.client.player != null && this.client.world != null) {
				ClientRecipeBook book = this.client.player.getRecipeBook();
				book.reload(this.client.world.getRecipeManager().values(), this.client.world.getRegistryManager());
				this.client.reloadSearchProvider(SearchManager.RECIPE_OUTPUT, book.getOrderedResults());
				book.getOrderedResults().forEach(r -> r.initialize(book));
			}
		}));
		this.listWidget.addEntry(createCyclingButtonWidget(Hardcover.CONFIG.alternativeRecipeButton, "alt-recipe-button", (b, v) -> Hardcover.CONFIG.alternativeRecipeButton = v), createCyclingButtonWidget(Hardcover.CONFIG.darkMode, "dark-mode", (b, v) -> Hardcover.CONFIG.darkMode = v));
		this.listWidget.addEntry(createCyclingButtonWidget(Hardcover.CONFIG.centeredInventory, "centered-inventory", (b, v) -> Hardcover.CONFIG.centeredInventory = v), createCyclingButtonWidget(Hardcover.CONFIG.bounce, "bounce", (b, v) -> Hardcover.CONFIG.bounce = v));
		this.listWidget.addEntry(createCyclingButtonWidget(Hardcover.CONFIG.mouseWheelScrolling, "mouse-wheel-scrolling", (b, v) -> Hardcover.CONFIG.mouseWheelScrolling = v), createCyclingButtonWidget(Hardcover.CONFIG.circularScrolling, "circular-scrolling", (b, v) -> Hardcover.CONFIG.circularScrolling = v));
	}

	private CyclingButtonWidget<?> createCyclingButtonWidget(boolean initial, String translationKey, BiConsumer<CyclingButtonWidget<?>, Boolean> consumer) {
		return CyclingButtonWidget.onOffBuilder().initially(initial).tooltip(t -> Tooltip.of(Text.translatable(MessageFormat.format("hardcover.config.tooltip.{0}", translationKey)))).build(0, 0, 0, 20, Text.translatable(MessageFormat.format("hardcover.config.{0}", translationKey)), consumer::accept);
	}

	static class ConfigListWidget extends ElementListWidget<ConfigEntry> {
		public ConfigListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
			super(client, width, height, top, bottom, itemHeight);
		}

		public void addEntry(ClickableWidget widget) {
			widget.setX(width / 2 - 155);
			widget.setWidth(310);
			this.addEntry(new ConfigEntry(ImmutableList.of(widget)));
		}

		public void addEntry(ClickableWidget leftWidget, ClickableWidget rightWidget) {
			leftWidget.setWidth(150);
			leftWidget.setX(width / 2 - 155);
			rightWidget.setWidth(150);
			rightWidget.setX(width / 2 - 155 + 160);
			this.addEntry(new ConfigEntry(ImmutableList.of(leftWidget, rightWidget)));
		}

		public int getRowWidth() {
			return 400;
		}

		@Override
		protected int getScrollbarPositionX() {
			return super.getScrollbarPositionX() + 32;
		}
	}

	static class ConfigEntry extends ElementListWidget.Entry<ConfigEntry> {
		final List<ClickableWidget> widgets;

		ConfigEntry(List<ClickableWidget> widgets) {
			this.widgets = ImmutableList.copyOf(widgets);
		}

		@Override
		public List<? extends Selectable> selectableChildren() {
			return this.widgets;
		}

		@Override
		public List<? extends Element> children() {
			return this.widgets;
		}

		@Override
		public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float delta) {
			this.widgets.forEach(widget -> {
				widget.setY(y);
				widget.render(context, mouseX, mouseY, delta);
			});
		}
	}
}
