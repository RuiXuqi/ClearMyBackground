package com.clear.clearmybackground.mixin.early;

import com.clear.clearmybackground.ClientHelper;
import net.minecraft.client.gui.GuiSelectWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnusedMixin")
@Mixin(GuiSelectWorld.class)
public class GuiSelectWorldMixin {
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void drawBG(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        GuiSelectWorld self = (GuiSelectWorld) (Object) this;
        ClientHelper.renderWorldBackground(self.mc, self.width, self.height);
    }
}
