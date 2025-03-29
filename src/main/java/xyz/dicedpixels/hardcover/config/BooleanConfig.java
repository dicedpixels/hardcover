package xyz.dicedpixels.hardcover.config;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public final class BooleanConfig extends AbstractConfig<Boolean> {
    private BooleanConfig(String key, boolean defaultValue) {
        super(key, defaultValue);
    }

    public static BooleanConfig of(String key, boolean defaultValue) {
        return new BooleanConfig(key, defaultValue);
    }

    public static BooleanConfig of(String key) {
        return new BooleanConfig(key, false);
    }

    @Override
    public ClickableWidget createWidget(Runnable callback) {
        return CyclingButtonWidget.onOffBuilder(Text.translatable("hardcover.gui.config.on"), Text.translatable("hardcover.gui.config.off"))
            .initially(getValue())
            .tooltip(value -> getTooltip())
            .build(getMessage(), (button, value) -> {
                setValue(value);
                callback.run();
            });
    }

    public static class Serializer implements JsonSerializer<BooleanConfig> {
        @Override
        public JsonElement serialize(BooleanConfig value, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(value.getValue());
        }
    }
}
