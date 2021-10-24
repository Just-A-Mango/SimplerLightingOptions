package com.mango.simpleroptions.lambdaurora.mixin;


import com.mango.simpleroptions.BetterGrassOption;
import com.mango.simpleroptions.DynamicLightsOption;
import dev.lambdaurora.lambdabettergrass.LambdaBetterGrass;
import dev.lambdaurora.lambdynlights.LambDynLights;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VideoOptionsScreen.class, priority = 1001)
public class ChangeLDLButton extends GameOptionsScreen {
    private DynamicLightsOption lambdynlights$option;
    private BetterGrassOption bettergrass$option;

    public ChangeLDLButton(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(Screen parent, GameOptions gameOptions, CallbackInfo ci) {
        this.lambdynlights$option = new DynamicLightsOption(LambDynLights.get().config, MinecraftClient.getInstance().currentScreen);
        this.bettergrass$option = new BetterGrassOption(LambdaBetterGrass.get().config, MinecraftClient.getInstance().currentScreen);
    }

    @ModifyArg(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/option/Option;)V"
            ),
            index = 0
    )
    private Option[] addOptionButton(Option[] old) {
        Option[] options = new Option[old.length];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 2] = this.lambdynlights$option;
        options[options.length - 1] = this.bettergrass$option;
        return options;
    }
}
