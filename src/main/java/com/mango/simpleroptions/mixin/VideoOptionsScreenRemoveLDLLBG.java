package com.mango.simpleroptions.mixin;

import com.mango.simpleroptions.DynamicLightsOption;
import dev.lambdaurora.lambdynlights.LambDynLights;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoOptionsScreen.class)
public class VideoOptionsScreenRemoveLDLLBG extends GameOptionsScreen {

    public VideoOptionsScreenRemoveLDLLBG(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
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
        options[options.length - 1] = new DynamicLightsOption(LambDynLights.get().config, this);
        return options;
    }


}
