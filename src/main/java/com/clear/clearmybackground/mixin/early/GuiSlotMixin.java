package com.clear.clearmybackground.mixin.early;

import com.clear.clearmybackground.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(GuiSlot.class)
public class GuiSlotMixin {
    @Shadow
    public int left;

    @Shadow
    public int top;

    @Shadow
    public int right;

    @Shadow
    public int bottom;

    @Shadow
    @Final
    protected Minecraft mc;

    @Shadow
    protected float amountScrolled;

    @Inject(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;disableTexture2D()V",
                    ordinal = 0
            )
    )
    private void drawFooterAndHeader(int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        ClientHelper.renderListSeparators(this.mc, this.left, this.top, this.right, this.bottom);
    }

    @Redirect(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()V",
                    ordinal = 0
            )
    )
    private void cancelTopShadowDrawing(@Nonnull Tessellator instance) {
        BufferBuilder origBuffer = instance.getBuffer();
        origBuffer.finishDrawing();
        origBuffer.reset();
    }

    @Redirect(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()V",
                    ordinal = 1
            )
    )
    private void cancelBottomShadowDrawing(@Nonnull Tessellator instance) {
        BufferBuilder origBuffer = instance.getBuffer();
        origBuffer.finishDrawing();
        origBuffer.reset();
    }

    @Inject(
            method = "overlayBackground",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelOverlayDirtDrawing(int startY, int endY, int startAlpha, int endAlpha, @Nonnull CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "drawContainerBackground",
            at = @At("HEAD"),
            cancellable = true,
            remap = false // This method is created by forge
    )
    private void modifyBackground(@Nonnull CallbackInfo ci) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        ClientHelper.renderListBackground(this.mc, this.left, this.top, this.right, this.bottom, this.amountScrolled);
        ci.cancel();
    }

    @Inject(method = "drawSelectionBox", at = @At("HEAD"))
    private void enableScissor(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci){
        ClientHelper.scissor(this.left, this.top, this.right - this.left, this.bottom - this.top);
    }

    @Inject(method = "drawSelectionBox", at = @At("RETURN"))
    private void disableScissor(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
