package com.clear.clearmybackground.mixin.early;

import com.clear.clearmybackground.ClientHelper;
import com.clear.clearmybackground.mixininterface.IGuiMainMenuMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnusedMixin")
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public GuiScreen currentScreen;

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiScreen;updateScreen()V")
    )
    private void updateTicker(CallbackInfo ci) {
        // FIXME: tick more properly
        if (this.currentScreen instanceof GuiMainMenu) return;
        ((IGuiMainMenuMixin) ClientHelper.MENU_INSTANCE).clearMyBackground$tickPanoramaTimer();
    }
}
