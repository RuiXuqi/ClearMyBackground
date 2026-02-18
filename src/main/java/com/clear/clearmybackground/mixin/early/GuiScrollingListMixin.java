package com.clear.clearmybackground.mixin.early;

import com.clear.clearmybackground.ClientHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import cpw.mods.fml.client.GuiScrollingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@SuppressWarnings("UnusedMixin")
@Mixin(value = GuiScrollingList.class, remap = false)
public abstract class GuiScrollingListMixin {
    @Shadow
    @Final
    protected int left;

    @Shadow
    @Final
    protected int top;

    @Shadow
    @Final
    private int right; // why?

    @Shadow
    @Final
    protected int bottom;

    @Shadow
    @Final
    private Minecraft client;

    @Shadow
    private float scrollDistance;

    @Inject(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V",
                    ordinal = 5
            ),
            remap = false
    )
    private void drawFooterAndHeader(int mouseXIn, int mouseYIn, float partialTicks, CallbackInfo ci) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientHelper.renderListSeparators(this.client, this.left, this.top, this.right, this.bottom);
    }

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
                    ordinal = 0
            )
    )
    private void cancelBind(TextureManager instance, ResourceLocation resource) {
    }

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()I",
                    ordinal = 0
            )
    )
    private int redirectDraw(@Nonnull Tessellator instance) {
        TessellatorAccessor accessor = (TessellatorAccessor) instance;
        accessor.setIsDrawing(false);
        accessor.invokeReset();
        ClientHelper.renderListBackground(this.client, this.left, this.top, this.right, this.bottom, this.scrollDistance);
        return 0;
    }

    @Redirect(
            method = "drawScreen(IIF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcpw/mods/fml/client/GuiScrollingList;drawGradientRect(IIIIII)V",
                    ordinal = 0
            )
    )
    private void redirectWorldDraw(GuiScrollingList instance, int left, int top, int right, int bottom, int color1, int color2) {
        ClientHelper.renderListBackground(this.client, this.left, this.top, this.right, this.bottom, this.scrollDistance);
    }

    @Redirect(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()I",
                    ordinal = 2
            )
    )
    private int cancelTopShadowDrawing(@Nonnull Tessellator instance) {
        TessellatorAccessor accessor = (TessellatorAccessor) instance;
        accessor.setIsDrawing(false);
        accessor.invokeReset();
        return 0;
    }

    @Redirect(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/Tessellator;draw()I",
                    ordinal = 3
            )
    )
    private int cancelBottomShadowDrawing(@Nonnull Tessellator instance) {
        TessellatorAccessor accessor = (TessellatorAccessor) instance;
        accessor.setIsDrawing(false);
        accessor.invokeReset();
        return 0;
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
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glDisable(I)V",
                    ordinal = 2
            )
    )
    private void enableScissorBox(int mouseX, int mouseY, float p_22243_3_, CallbackInfo ci) {
        ClientHelper.scissor(this.left, this.top, this.right - this.left, this.bottom - this.top);
    }

    @Inject(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V",
                    ordinal = 0
            )
    )
    private void disableScissorBox(int mouseX, int mouseY, float p_22243_3_, CallbackInfo ci) {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @WrapOperation(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lcpw/mods/fml/client/GuiScrollingList;drawSlot(IIIILnet/minecraft/client/renderer/Tessellator;)V"
            )
    )
    private void scissorSlot(GuiScrollingList instance, int v0, int v1, int v2, int v3, Tessellator tessellator, Operation<Void> original) {
        ClientHelper.scissor(this.left, this.top, this.right - this.left, this.bottom - this.top);
        original.call(instance, v0, v1, v2, v3, tessellator);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
