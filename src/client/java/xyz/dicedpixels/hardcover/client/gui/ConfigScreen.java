package xyz.dicedpixels.hardcover.client.gui;

import java.util.List;
import java.util.function.BiConsumer;
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
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.search.SearchManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.apache.commons.compress.utils.Lists;
import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.client.util.Texts;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Hardcover"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        var list = new ListWidget(client, width, height, 25, this.height - 30, 25);
        addDrawableChild(list);
        footer();
        var darkModeButton = cyclingButtonWidget(
                Hardcover.config().darkMode, Texts.DARK_MODE, (button, value) -> Hardcover.config().darkMode = value);
        darkModeButton.active = Hardcover.config().alternativeRecipeButton;
        list.add(cyclingButtonWidget(
                Hardcover.config().recipeBook,
                Texts.RECIPE_BOOK,
                (button, value) -> Hardcover.config().recipeBook = value));
        list.add(
                cyclingButtonWidget(
                        Hardcover.config().unlockAllRecipes,
                        Texts.UNLOCK_ALL_RECIPES,
                        (button, value) -> Hardcover.config().unlockAllRecipes = value),
                cyclingButtonWidget(Hardcover.config().ungroupRecipes, Texts.UNGROUP_RECIPES, (button, value) -> {
                    Hardcover.config().ungroupRecipes = value;
                    if (client != null && client.player != null && client.world != null) {
                        var book = client.player.getRecipeBook();
                        book.reload(client.world.getRecipeManager().values(), client.world.getRegistryManager());
                        client.reloadSearchProvider(SearchManager.RECIPE_OUTPUT, book.getOrderedResults());
                        book.getOrderedResults().forEach(r -> r.initialize(book));
                    }
                }));
        list.add(
                cyclingButtonWidget(
                        Hardcover.config().alternativeRecipeButton,
                        Texts.ALTERNATIVE_RECIPE_BUTTON,
                        (button, value) -> {
                            Hardcover.config().alternativeRecipeButton = value;
                            darkModeButton.active = value;
                            if (!value) {
                                darkModeButton.setValue(false);
                                Hardcover.config().darkMode = false;
                            }
                        }),
                darkModeButton);
        list.add(
                cyclingButtonWidget(
                        Hardcover.config().centeredInventory,
                        Texts.CENTERED_INVENTORY,
                        (button, value) -> Hardcover.config().centeredInventory = value),
                cyclingButtonWidget(
                        Hardcover.config().bounce, Texts.BOUNCE, (button, value) -> Hardcover.config().bounce = value));
        list.add(
                cyclingButtonWidget(
                        Hardcover.config().mouseWheelScrolling,
                        Texts.MOUSE_WHEEL_SCROLLING,
                        (button, value) -> Hardcover.config().mouseWheelScrolling = value),
                cyclingButtonWidget(
                        Hardcover.config().circularScrolling,
                        Texts.CIRCULAR_SCROLLING,
                        (button, value) -> Hardcover.config().circularScrolling = value));
    }

    private CyclingButtonWidget<Boolean> cyclingButtonWidget(
            boolean initially,
            Texts.TextPair<Text> component,
            BiConsumer<CyclingButtonWidget<Boolean>, Boolean> consumer) {
        return CyclingButtonWidget.onOffBuilder()
                .initially(initially)
                .tooltip(tooltip -> Tooltip.of(component.tooltip()))
                .build(0, 0, 0, 20, component.display(), consumer::accept);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 10, 0xFFFFFF);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackgroundTexture(context);
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }

    private void footer() {
        var grid = new GridWidget().setColumnSpacing(5);
        var adder = grid.createAdder(1);
        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> {
                    Hardcover.saveConfig();
                    close();
                })
                .build());
        grid.forEachChild(this::addDrawableChild);
        grid.refreshPositions();
        grid.setPosition(width / 2 - grid.getWidth() / 2, height - grid.getHeight() - 5);
    }

    private static class ListWidget extends ElementListWidget<Entry> {
        public ListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
            super(client, width, height, top, bottom, itemHeight);
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth() + 85;
        }

        public void add(ClickableWidget... widgets) {
            var grid = new GridWidget().setColumnSpacing(5);
            var adder = grid.createAdder(widgets.length);
            for (var widget : widgets) {
                if (widgets.length == 1) {
                    widget.setWidth((ButtonWidget.DEFAULT_WIDTH * 2) + 5);
                } else if (widgets.length == 2) {
                    widget.setWidth(ButtonWidget.DEFAULT_WIDTH);
                }
                adder.add(widget);
            }
            grid.refreshPositions();
            grid.setX(width / 2 - grid.getWidth() / 2);
            addEntry(new ConfigScreen.Entry(grid));
        }

        @Override
        protected int getScrollbarPositionX() {
            return super.getScrollbarPositionX() + 35;
        }
    }

    private static class Entry extends ElementListWidget.Entry<Entry> {
        private final GridWidget grid;
        private final List<ClickableWidget> children = Lists.newArrayList();

        public Entry(GridWidget grid) {
            this.grid = grid;
            this.grid.forEachChild(children::add);
            this.grid.refreshPositions();
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return children;
        }

        @Override
        public List<? extends Element> children() {
            return children;
        }

        @Override
        public void render(
                DrawContext context,
                int index,
                int y,
                int x,
                int entryWidth,
                int entryHeight,
                int mouseX,
                int mouseY,
                boolean hovered,
                float delta) {
            grid.forEachChild(child -> {
                child.setY(y);
                child.render(context, mouseX, mouseY, delta);
            });
        }
    }
}
