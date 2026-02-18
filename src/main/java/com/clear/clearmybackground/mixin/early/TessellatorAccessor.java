package com.clear.clearmybackground.mixin.early;

import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedMixin")
@Mixin(Tessellator.class)
public interface TessellatorAccessor {
    @Accessor("isDrawing")
    void setIsDrawing(boolean isDrawing);

    @Invoker("reset")
    void invokeReset();
}
