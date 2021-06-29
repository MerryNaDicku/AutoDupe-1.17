package codes.waves.autodupe.mixin;

import codes.waves.autodupe.AutoDupe;
import codes.waves.autodupe.duping.Duping;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    public void setup(CallbackInfo unused) {
         AutoDupe.duping = new Duping();
    }
}
