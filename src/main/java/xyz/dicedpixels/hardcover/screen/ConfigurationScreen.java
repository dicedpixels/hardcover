package xyz.dicedpixels.hardcover.screen;

import java.util.function.Consumer;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.screen.entry.ConfigurationEntry;
import xyz.dicedpixels.hardcover.screen.widget.ListWidget;
import xyz.dicedpixels.hardcover.util.Texts;
import xyz.dicedpixels.hardcover.util.Texts.Pair;
import xyz.dicedpixels.pixel.client.screen.AbstractScreen;

public class ConfigurationScreen extends AbstractScreen {
    private final Screen parent;
    ListWidget list;

    public ConfigurationScreen(Screen parent) {
        super(Text.literal("Hardcover"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        list = addDrawableChild(new ListWidget(client, width, height - 65, 10, 25));

        initEntries();

        var grid = new GridWidget().setSpacing(5);
        var adder = grid.createAdder(1);
        var buttons = DirectionalLayoutWidget.horizontal().spacing(5);

        adder.add(new TextWidget(title, textRenderer));

        buttons.add(ButtonWidget.builder(Texts.RESET, button -> {
                    list.reset();
                    Hardcover.reset();
                    initEntries();
                })
                .width(200 / 4)
                .build());

        buttons.add(ButtonWidget.builder(Texts.DONE, button -> close())
                .width((200 / 4 * 3) - 5)
                .build());

        adder.add(buttons);

        grid.refreshPositions();
        grid.setPosition(10, height - grid.getHeight() - 10);
        grid.forEachChild(this::addDrawableChild);
    }

    private void initEntries() {
        list.addEntry(ConfigurationEntry.of(createWidget(
                Hardcover.configuration().recipeBook,
                Texts.RECIPE_BOOK,
                value -> Hardcover.configuration().recipeBook = value)));

        var darkMode = createWidget(
                Hardcover.configuration().darkMode,
                Texts.DARK_MODE,
                value -> Hardcover.configuration().darkMode = value);

        var alternativeRecipeButton = createWidget(
                Hardcover.configuration().alternativeRecipeButton,
                Texts.ALTERNATIVE_RECIPE_BUTTON.display(),
                Texts.ALTERNATIVE_RECIPE_BUTTON.tooltip().copy().append(Texts.EXPERIMENTAL_TOOLTIP),
                value -> {
                    Hardcover.configuration().alternativeRecipeButton = value;

                    darkMode.active = value;
                    darkMode.setValue(false);

                    Hardcover.configuration().darkMode = false;
                });

        alternativeRecipeButton.active = !Hardcover.configuration().ungroupRecipes;
        darkMode.active = Hardcover.configuration().alternativeRecipeButton;

        list.addEntry(ConfigurationEntry.of(
                createWidget(
                        Hardcover.configuration().unlockAllRecipes,
                        Texts.UNLOCK_ALL_RECIPES,
                        value -> Hardcover.configuration().unlockAllRecipes = value),
                createWidget(Hardcover.configuration().ungroupRecipes, Texts.UNGROUP_RECIPES, value -> {
                    Hardcover.configuration().ungroupRecipes = value;
                    Hardcover.reloadSearchProvider(client);

                    alternativeRecipeButton.active = !value;
                    alternativeRecipeButton.setValue(false);

                    Hardcover.configuration().alternativeRecipeButton = false;

                    if (Hardcover.configuration().darkMode) {
                        darkMode.active = false;
                        darkMode.setValue(false);

                        Hardcover.configuration().darkMode = false;
                    }
                })));

        list.addEntry(ConfigurationEntry.of(alternativeRecipeButton, darkMode));

        list.addEntry(ConfigurationEntry.of(
                createWidget(
                        Hardcover.configuration().centeredInventory,
                        Texts.CENTERED_INVENTORY,
                        value -> Hardcover.configuration().centeredInventory = value),
                createWidget(
                        Hardcover.configuration().bounce,
                        Texts.BOUNCE,
                        value -> Hardcover.configuration().bounce = value)));

        list.addEntry(ConfigurationEntry.of(
                createWidget(
                        Hardcover.configuration().mouseWheelScrolling,
                        Texts.MOUSE_WHEEL_SCROLLING,
                        value -> Hardcover.configuration().mouseWheelScrolling = value),
                createWidget(
                        Hardcover.configuration().circularScrolling,
                        Texts.CIRCULAR_SCROLLING,
                        value -> Hardcover.configuration().circularScrolling = value)));
    }

    private CyclingButtonWidget<Boolean> createWidget(boolean initially, Pair pair, Consumer<Boolean> consumer) {
        return createWidget(initially, pair.display(), pair.tooltip(), consumer);
    }

    private CyclingButtonWidget<Boolean> createWidget(
            boolean initially, Text display, Text tooltip, Consumer<Boolean> consumer) {
        return CyclingButtonWidget.onOffBuilder(Texts.ON, Texts.OFF)
                .initially(initially)
                .tooltip(value -> Tooltip.of(tooltip))
                .build(0, 0, 0, 0, display, (button, value) -> Hardcover.save(() -> consumer.accept(value)));
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }
}
