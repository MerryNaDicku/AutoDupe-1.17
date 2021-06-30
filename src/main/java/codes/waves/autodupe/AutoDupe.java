package codes.waves.autodupe;

import codes.waves.autodupe.duping.Duping;
import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

import javax.sound.midi.SysexMessage;

public class AutoDupe implements ModInitializer {
    public static EventBus eventBus = new EventBus("codes.waves.autodupe");
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public static Duping duping;
    public static KeyBinding keyBind;

    @Override
    public void onInitialize() {
            keyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Preform dupe",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F12,
                "AutoDupe"
        ));
    }
}
