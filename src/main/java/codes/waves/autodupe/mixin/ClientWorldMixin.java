package codes.waves.autodupe.mixin;

import codes.waves.autodupe.AutoDupe;
import codes.waves.autodupe.event.events.TickEvent;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "tickEntities", at = @At("HEAD"), cancellable = true)
    public void tickEntities(CallbackInfo info) {
        TickEvent t = new TickEvent();
        AutoDupe.eventBus.post(t);
        if (t.isCancelled())
            info.cancel();
    }

}
