package com.clear.clearmybackground.mixin.late.controlling;

import com.clear.clearmybackground.ClientHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import us.getfluxed.controlsearch.client.gui.GuiNewKeyBindingList;

@Mixin(value = GuiNewKeyBindingList.class, remap = false)
public class GuiNewKeyBindingListMixin {
    @Inject(method = "drawSelectionBox", at = @At("HEAD"))
    private void enableScissor(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci){
        GuiNewKeyBindingList self = (GuiNewKeyBindingList) (Object) this;
        ClientHelper.scissor(self.left, self.top, self.right - self.left, self.bottom - self.top);
    }

    @Inject(method = "drawSelectionBox", at = @At("RETURN"))
    private void disableScissor(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
