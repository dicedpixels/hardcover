package xyz.dicedpixels.hardcover.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Translations;

public class ConfigScreen extends Screen {
    private final Screen parentScreen;

    public ConfigScreen(Screen parentScreen) {
        super(Text.literal("Hardcover"));
        this.parentScreen = parentScreen;
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parentScreen);
        }
    }

    @Override
    protected void init() {
        var mainGrid = new GridWidget().setSpacing(5);
        var adder = mainGrid.createAdder(1);
        var buttonsLayout = DirectionalLayoutWidget.horizontal().spacing(5);

        adder.add(new TextWidget(title, textRenderer));

        buttonsLayout.add(ButtonWidget.builder(Translations.RESET, button -> {}).width(200 / 4).build());
        buttonsLayout.add(ButtonWidget.builder(Translations.DONE, button -> close()).width((200 / 4 * 3) - 5).build());

        adder.add(buttonsLayout);

        mainGrid.refreshPositions();
        mainGrid.setPosition(10, height - mainGrid.getHeight() - 10);
        mainGrid.forEachChild(this::addDrawableChild);
    }
}
