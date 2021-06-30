package codes.waves.autodupe.duping;

import codes.waves.autodupe.AutoDupe;
import codes.waves.autodupe.event.events.TickEvent;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.LiteralText;

import java.lang.reflect.Field;

import static codes.waves.autodupe.AutoDupe.mc;

public class Duping {
    private boolean duping = false;

    public boolean isDuping() { return duping; }
    public void setDuping(boolean duping) { this.duping = duping; }
    private static Field nameField;

    public Duping() {
        AutoDupe.eventBus.register(this);
        try {
            nameField = AnvilScreen.class.
                   getDeclaredField("nameField");

            nameField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            try {
                nameField = AnvilScreen.class.
                        getDeclaredField("field_2821");

                nameField.setAccessible(true);
            } catch (NoSuchFieldException noSuchFieldException) {
                System.out.println("Failed to find field_2821 in AnvilScreen. This mod will not work.");


            }

            System.out.println("Failed to find nameField in AnvilScreen.");
        }

    }

    private int attemptIndex = 0;

    @Subscribe
    public void onTick(TickEvent unused) {
        // Reset the index if we are not duping.
        if (!duping && attemptIndex != 0)
            attemptIndex = 0;

        // If we are not duping return.
        if (!duping)
            return;

        // If we are not in anvil screen disable duping and return.
        if (!(mc.currentScreen instanceof AnvilScreen))
        {
            if (attemptIndex > 0)
                mc.player.sendMessage(new LiteralText(String.format("&8[&dAD&8]&d Duping complete after %d attempts.".replace("&", "§"),    attemptIndex+1)), false);
            else
                mc.player.sendMessage(new LiteralText("&8[&dAD&8]&c Duping stopped.".replace("&", "§")), false);
            duping = false;
            return;
        }

        AnvilScreen as = (AnvilScreen) mc.currentScreen;
        AnvilScreenHandler ash = (AnvilScreenHandler)as.getScreenHandler();
        ScreenHandler sh = as.getScreenHandler();
        Slot inSlot = ash.getSlot(0);
        Slot outSlot = ash.getSlot(2);

        if (!inSlot.hasStack() && attemptIndex <= 0)
        {
            mc.player.sendMessage(new LiteralText("&8[&dAD&8]&c No item in main slot. Stopped.".replace("&", "§")), false);
            duping = false;
            return;
        }

        try {
            TextFieldWidget nf = (TextFieldWidget) nameField.get(as);
            if (nf.getText().equals(inSlot.getStack().getName().asString()))
                nf.setText(attemptIndex+"");
            else
                nf.setText(attemptIndex+" ");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (attemptIndex > 0)
            mc.interactionManager.clickSlot(sh.syncId, inSlot.id, 0, SlotActionType.PICKUP, mc.player);

        if (mc.player.experienceLevel < 1)
        {
            mc.player.sendMessage(new LiteralText("&8[&dAD&8]&c Not enough experience to continue. Stopped.".replace("&", "§")), false);
            duping = false;
            return;
        }

        if (outSlot.hasStack())  {
            mc.interactionManager.clickSlot(sh.syncId ,outSlot.id, 0, SlotActionType.PICKUP, mc.player);
            attemptIndex++;
        }
    }
}
