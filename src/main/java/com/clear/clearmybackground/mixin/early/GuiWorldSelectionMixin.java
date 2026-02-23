package com.clear.clearmybackground.mixin.early;

import com.clear.clearmybackground.ClientHelper;
import net.minecraft.client.gui.GuiWorldSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiWorldSelection.class)
public class GuiWorldSelectionMixin {
    @Inject(method = "drawScreen", at = @At("HEAD"))
    private void drawBG(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        GuiWorldSelection self = (GuiWorldSelection) (Object) this;
        ClientHelper.renderWorldBackground(self, self.mc, self.width, self.height);
    }
}
