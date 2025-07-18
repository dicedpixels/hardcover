package xyz.dicedpixels.hardcover.config;

import com.google.common.base.CaseFormat;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public abstract class AbstractConfig<T> {
    private final Text message;
    private final Tooltip tooltip;
    private T value;

    public AbstractConfig(String key, T defaultValue) {
        value = defaultValue;
        message = Text.translatable("hardcover.config." + toSnakeCase(key));
        tooltip = Tooltip.of(Text.translatable("hardcover.config." + toSnakeCase(key) + ".tooltip"));
    }

    private static String toSnakeCase(String text) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, text);
    }

    public abstract ClickableWidget createWidget(Runnable runnable);

    public ClickableWidget createWidget() {
        return createWidget(() -> {});
    }

    public Text getMessage() {
        return message;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        ConfigManager.writeFile();
    }
}
