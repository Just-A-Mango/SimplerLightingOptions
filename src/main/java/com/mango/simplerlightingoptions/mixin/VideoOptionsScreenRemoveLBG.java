package com.mango.simplerlightingoptions.mixin;

import com.mango.simplerlightingoptions.BetterGrassOption;
import com.mango.simplerlightingoptions.DynamicLightsOption;
import dev.lambdaurora.lambdabettergrass.LambdaBetterGrass;
import dev.lambdaurora.lambdynlights.LambDynLights;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenRemoveLBG extends GameOptionsScreen {
    private Option bettergrass$option;

    public VideoOptionsScreenRemoveLBG(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstruct(Screen parent, GameOptions gameOptions, CallbackInfo ci) {
        this.bettergrass$option = new DynamicLightsOption(LambDynLights.get().config, this);
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
        Option[] options = new Option[old.length + 1];
        System.arraycopy(old, 0, options, 0, old.length);
        options[options.length - 1] = new BetterGrassOption(LambdaBetterGrass.get().config, this);
        return options;
    }





}
