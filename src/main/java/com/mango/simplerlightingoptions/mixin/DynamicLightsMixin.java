package com.mango.simplerlightingoptions.mixin;

import dev.lambdaurora.lambdynlights.gui.DynamicLightsOptionsOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DynamicLightsOptionsOption.class)
public class DynamicLightsMixin{
	@Unique
	private Option dynamiclights$option;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void createButton(Screen parent, CallbackInfo ci) {
	}
}
