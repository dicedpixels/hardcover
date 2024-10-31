package xyz.dicedpixels.hardcover.gui;

import java.util.List;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectImmutableList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import xyz.dicedpixels.hardcover.Config;
import xyz.dicedpixels.hardcover.Hardcover;
import xyz.dicedpixels.hardcover.Translations;
import xyz.dicedpixels.hardcover.Translations.Pair;

public class ConfigScreen extends Screen {
    private static final OnOffCyclingButtonWidget ALTERNATIVE_RECIPE_BUTTON = OnOffCyclingButtonWidget.of("alternativeRecipeButton", Translations.ALTERNATIVE_RECIPE_BUTTON, () -> !Config.instance().ungroupRecipes);
    private static final OnOffCyclingButtonWidget BOUNCE = OnOffCyclingButtonWidget.of("bounce", true, Translations.BOUNCE);
    private static final OnOffCyclingButtonWidget CENTERED_INVENTORY = OnOffCyclingButtonWidget.of("centeredInventory", Translations.CENTERED_INVENTORY);
    private static final OnOffCyclingButtonWidget CIRCULAR_SCROLLING = OnOffCyclingButtonWidget.of("circularScrolling", Translations.CIRCULAR_SCROLLING);
    private static final OnOffCyclingButtonWidget DARK_MODE = OnOffCyclingButtonWidget.of("darkMode", Translations.DARK_MODE, () -> !Config.instance().ungroupRecipes && Config.instance().alternativeRecipeButton);
    private static final OnOffCyclingButtonWidget MOUSE_WHEEL_SCROLLING = OnOffCyclingButtonWidget.of("mouseWheelScrolling", Translations.MOUSE_WHEEL_SCROLLING);
    private static final OnOffCyclingButtonWidget RECIPE_BOOK = OnOffCyclingButtonWidget.of("recipeBook", true, Translations.RECIPE_BOOK);
    private static final OnOffCyclingButtonWidget UNGROUP_RECIPES = OnOffCyclingButtonWidget.of("ungroupRecipes", Translations.UNGROUP_RECIPES, () -> Hardcover.refreshSearchManager(MinecraftClient.getInstance()));
    private static final OnOffCyclingButtonWidget UNLOCK_ALL_RECIPES = OnOffCyclingButtonWidget.of("unlockAllRecipes", Translations.UNLOCK_ALL_RECIPES);
    private static final List<OnOffCyclingButtonWidget> BUTTONS = ObjectImmutableList.of(ALTERNATIVE_RECIPE_BUTTON, BOUNCE, CENTERED_INVENTORY, CIRCULAR_SCROLLING, DARK_MODE, MOUSE_WHEEL_SCROLLING, RECIPE_BOOK, UNGROUP_RECIPES, UNLOCK_ALL_RECIPES);
    private final Screen parentScreen;
    private ConfigListWidget list;

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
        list = addDrawableChild(new ConfigListWidget(client, width, height - 65, 10, 25));

        initEntries();

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

    private void initEntries() {
        list.addEntry(ConfigListWidget.ConfigEntry.of(RECIPE_BOOK));
        list.addEntry(ConfigListWidget.ConfigEntry.of(UNLOCK_ALL_RECIPES, UNGROUP_RECIPES));
        list.addEntry(ConfigListWidget.ConfigEntry.of(ALTERNATIVE_RECIPE_BUTTON, DARK_MODE));
        list.addEntry(ConfigListWidget.ConfigEntry.of(CENTERED_INVENTORY, BOUNCE));
        list.addEntry(ConfigListWidget.ConfigEntry.of(MOUSE_WHEEL_SCROLLING, CIRCULAR_SCROLLING));

        BUTTONS.forEach(OnOffCyclingButtonWidget::blur);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        BUTTONS.forEach(OnOffCyclingButtonWidget::update);
    }

    public static class OnOffCyclingButtonWidget {
        private final Supplier<Boolean> activationCondition;
        private final CyclingButtonWidget<Boolean> button;
        private final String property;
        boolean defaultValue;

        private OnOffCyclingButtonWidget(String property, boolean defaultValue, Pair translations, Supplier<Boolean> activationCondition, Runnable callback) {
            this.property = property;
            this.activationCondition = activationCondition;
            this.defaultValue = defaultValue;
            this.button = CyclingButtonWidget.onOffBuilder().initially(getConfigValue(property)).tooltip(value -> translations.getTooltip()).build(translations.getMessage(), (button, value) -> {
                setConfigValue(property, value);
                callback.run();
            });
        }

        private static boolean getConfigValue(String property) {
            try {
                return Config.class.getDeclaredField(property).getBoolean(Config.instance());
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        public static OnOffCyclingButtonWidget of(String property, Pair translations, Supplier<Boolean> activationCondition) {
            return new OnOffCyclingButtonWidget(property, false, translations, activationCondition, () -> {});
        }

        public static OnOffCyclingButtonWidget of(String property, boolean defaultValue, Pair translations) {
            return new OnOffCyclingButtonWidget(property, defaultValue, translations, () -> true, () -> {});
        }

        public static OnOffCyclingButtonWidget of(String property, Pair translations) {
            return new OnOffCyclingButtonWidget(property, false, translations, () -> true, () -> {});
        }

        public static OnOffCyclingButtonWidget of(String property, Pair translations, Runnable callback) {
            return new OnOffCyclingButtonWidget(property, false, translations, () -> true, callback);
        }

        private static void setConfigValue(String property, boolean value) {
            try {
                Config.class.getDeclaredField(property).setBoolean(Config.instance(), value);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }

            Config.instance().save();
        }

        public void blur() {
            button.setFocused(false);
        }

        public CyclingButtonWidget<Boolean> getButton() {
            return button;
        }

        public void update() {
            var state = activationCondition.get();

            if (!state) {
                button.setValue(defaultValue);
                setConfigValue(property, defaultValue);
            }

            button.active = state;
        }
    }
}
