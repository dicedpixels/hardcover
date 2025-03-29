package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.config.Configs;
import xyz.dicedpixels.hardcover.util.VersionInfo;

public final class ConfigScreen extends Screen {
    private final ClickableWidget alternativeRecipeBookLayout = Configs.alternativeRecipeBookLayout.createWidget();
    private final ClickableWidget alternativeRecipeButton = Configs.alternativeRecipeButton.createWidget();
    private final ClickableWidget bounce = Configs.bounce.createWidget();
    private final ClickableWidget centeredInventory = Configs.centeredInventory.createWidget();
    private final ClickableWidget circularScrolling = Configs.circularScrolling.createWidget();
    private final ClickableWidget invertScrollDirection = Configs.invertScrollDirection.createWidget();
    private final ClickableWidget mouseWheelScrolling = Configs.mouseWheelScrolling.createWidget();
    private final Screen parentScreen;
    private final ClickableWidget quickCraft = Configs.quickCraft.createWidget();
    private final ClickableWidget recipeBook = Configs.recipeBook.createWidget();
    private final ClickableWidget ungroupRecipes = Configs.ungroupRecipes.createWidget(() -> refreshSearchManager(client));
    private final ClickableWidget unlockAllRecipes = Configs.unlockAllRecipes.createWidget();

    public ConfigScreen(Screen parentScreen) {
        super(Hardcover.TITLE);
        this.parentScreen = parentScreen;
    }

    private static void refreshSearchManager(MinecraftClient client) {
        if (client != null && client.player != null && client.world != null && client.getNetworkHandler() != null) {
            client.player.getRecipeBook().refresh();
            client.getNetworkHandler().refreshSearchManager();
        }
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parentScreen);
        }
    }

    @Override
    protected void init() {
        var list = addDrawableChild(new ConfigListWidget(client, width, height - 65, 10, 25));

        list.addConfigEntry(ConfigEntry.create(recipeBook, unlockAllRecipes));
        list.addConfigEntry(ConfigEntry.create(ungroupRecipes, alternativeRecipeBookLayout));
        list.addConfigEntry(ConfigEntry.create(alternativeRecipeButton, bounce));
        list.addConfigEntry(ConfigEntry.create(centeredInventory, mouseWheelScrolling));
        list.addConfigEntry(ConfigEntry.create(circularScrolling, invertScrollDirection));
        list.addConfigEntry(ConfigEntry.create(quickCraft));

        var grid = new GridWidget().setSpacing(5);
        var adder = grid.createAdder(1);

        adder.add(new TextWidget(VersionInfo.getTitleWithVersion(), textRenderer));
        adder.add(ButtonWidget.builder(Text.translatable("hardcover.gui.config.back"), button -> close()).width(160).build());

        grid.refreshPositions();
        grid.setPosition(10, height - grid.getHeight() - 10);
        grid.forEachChild(this::addDrawableChild);
    }
}
