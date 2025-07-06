package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.config.ConfigCategory;
import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.feature.RecipeBook;

public final class ConfigScreen extends Screen {
    private final ClickableWidget alternativeRecipeBookLayout = Configs.alternativeRecipeBookLayout.createWidget();
    private final ClickableWidget alternativeRecipeButton = Configs.alternativeRecipeButton.createWidget();
    private final ClickableWidget autoCloseRecipeBook = Configs.autoCloseRecipeBook.createWidget();
    private final ClickableWidget bounce = Configs.bounce.createWidget();
    private final ClickableWidget centeredInventory = Configs.centeredInventory.createWidget();
    private final ClickableWidget circularScrolling = Configs.circularScrolling.createWidget();
    private final ClickableWidget compactCreativeTabs = Configs.compactCreativeTabs.createWidget();
    private final ClickableWidget creativeTabs = Configs.creativeTabs.createWidget(() -> RecipeBook.refreshRecipeBook(client));
    private final ClickableWidget invertScrollDirection = Configs.invertScrollDirection.createWidget();
    private final ClickableWidget mouseWheelScrolling = Configs.mouseWheelScrolling.createWidget();
    private final Screen parent;
    private final ClickableWidget quickCraft = Configs.quickCraft.createWidget();
    private final ClickableWidget recipeBook = Configs.recipeBook.createWidget();
    private final ClickableWidget ungroupRecipes = Configs.ungroupRecipes.createWidget(() -> RecipeBook.refreshRecipeBook(client));
    private final ClickableWidget unlockAllRecipes = Configs.unlockAllRecipes.createWidget();

    public ConfigScreen(Screen parent) {
        super(Hardcover.TITLE);
        this.parent = parent;
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }

    @Override
    protected void init() {
        var list = addDrawableChild(new ConfigListWidget(client, width, height - 65, 10, 25));

        list.createAndAddEntry(ConfigCategory.FEATURES);
        list.createAndAddEntry(recipeBook, quickCraft);
        list.createAndAddEntry(creativeTabs);

        list.createAndAddEntry(ConfigCategory.RECIPE_BOOK);
        list.createAndAddEntry(alternativeRecipeButton, alternativeRecipeBookLayout);
        list.createAndAddEntry(compactCreativeTabs, autoCloseRecipeBook);
        list.createAndAddEntry(bounce);

        list.createAndAddEntry(ConfigCategory.RECIPES);
        list.createAndAddEntry(unlockAllRecipes, ungroupRecipes);

        list.createAndAddEntry(ConfigCategory.NAVIGATION);
        list.createAndAddEntry(mouseWheelScrolling, circularScrolling);
        list.createAndAddEntry(invertScrollDirection, centeredInventory);

        var grid = new GridWidget().setSpacing(5);
        var adder = grid.createAdder(1);
        var titleLayout = DirectionalLayoutWidget.horizontal().spacing(5);

        titleLayout.add(new TextWidget(title, textRenderer));
        titleLayout.add(new TextWidget(Hardcover.getVersion(), textRenderer));

        adder.add(titleLayout);
        adder.add(ButtonWidget.builder(Text.translatable("hardcover.gui.config.screen.done"), button -> close()).width(160).build());

        grid.refreshPositions();
        grid.setPosition(10, height - grid.getHeight() - 10);
        grid.forEachChild(this::addDrawableChild);
    }
}
