package com.clear.clearmybackground.mixin;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

public enum Mixins implements IMixins {
    VANILLA(new MixinBuilder()
            .setPhase(Phase.EARLY)
            .addClientMixins(
                    "GuiMainMenuAccessor",
                    "GuiMainMenuMixin",
                    "GuiScreenMixin",
                    "GuiScrollingListMixin",
                    "GuiSelectWorldMixin",
                    "GuiSlotMixin",
                    "MinecraftMixin",
                    "TessellatorAccessor"
            )
    );

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return this.builder;
    }
}
