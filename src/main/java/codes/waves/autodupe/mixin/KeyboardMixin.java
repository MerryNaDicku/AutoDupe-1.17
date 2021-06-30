package codes.waves.autodupe.mixin;

import codes.waves.autodupe.AutoDupe;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static codes.waves.autodupe.AutoDupe.duping;
import static codes.waves.autodupe.AutoDupe.mc;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (action != GLFW.GLFW_PRESS)
            return;

        if (!AutoDupe.keyBind.matchesKey(key, scancode))
            return;

        if (!(mc.currentScreen instanceof AnvilScreen))
        {
            mc.player.sendMessage(new LiteralText("&8[&dAD&8]&c You must be in an anvil screen to use this.".replace("&", "§")), false);
            return;
        }

        duping.setDuping(!duping.isDuping());
        if (duping.isDuping())
            mc.player.sendMessage(new LiteralText("&8[&dAD&8]&a Duping started.".replace("&", "§")), false);
        else
            mc.player.sendMessage(new LiteralText("&8[&dAD&8]&c Duping stopped.".replace("&", "§")), false);

    }
}
