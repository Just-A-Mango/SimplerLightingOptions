package com.mango.simpleroptions;

import dev.lambdaurora.lambdynlights.DynamicLightsConfig;
import dev.lambdaurora.lambdynlights.DynamicLightsMode;
import dev.lambdaurora.lambdynlights.ExplosiveLightingMode;
import dev.lambdaurora.lambdynlights.LambDynLights;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class DynamicLightsOption extends Option {
    private static final String KEY = null;
    private final DynamicLightsConfig config;
    private final Screen parent;
    private Text text;
    private int graphid;


    public DynamicLightsOption(DynamicLightsConfig config, Screen parent) {
        super(KEY);
        this.config = LambDynLights.get().config;
        this.text = null;
        this.parent = parent;
        this.graphid = 1;
        this.config.load();
        if (this.config.getDynamicLightsMode() == DynamicLightsMode.FANCY) {
            this.graphid = 2;
            this.text = new TranslatableText("simplerlightingoptions.dynamiclights.fancy").formatted(Formatting.ITALIC);

        } else if (this.config.getDynamicLightsMode() == DynamicLightsMode.FAST) {
            this.graphid = 1;
            this.text = new TranslatableText("simplerlightingoptions.dynamiclights.fast");
        } else if (this.config.getDynamicLightsMode() == DynamicLightsMode.OFF) {
            this.graphid = 0;
            this.text = new TranslatableText("simplerlightingoptions.dynamiclights.off");
        }
    }


    public void RefreshButton() {
        if (graphid == 2) {
            graphid = 0;
            this.config.setDynamicLightsMode(DynamicLightsMode.OFF);
            this.config.setCreeperLightingMode(ExplosiveLightingMode.OFF);
            this.config.setTntLightingMode(ExplosiveLightingMode.OFF);
            this.config.setBlockEntitiesLightSource(false);
            this.config.setWaterSensitiveCheck(false);
            this.config.setEntitiesLightSource(false);
            this.config.save();
            this.text = new TranslatableText("simplerlightingoptions.dynamiclights.off");
            MinecraftClient.getInstance().reloadResources();
//            MinecraftClient.getInstance().setScreen(new SettingsScreen(this.parent));
        } else {
            graphid++;
            if (graphid == 1) {
                this.config.setDynamicLightsMode(DynamicLightsMode.FAST);
                this.config.setCreeperLightingMode(ExplosiveLightingMode.SIMPLE);
                this.config.setTntLightingMode(ExplosiveLightingMode.SIMPLE);
                this.config.setBlockEntitiesLightSource(true);
                this.config.setWaterSensitiveCheck(false);
                this.config.setEntitiesLightSource(false);
                this.config.save();
                this.text = new TranslatableText("simplerlightingoptions.dynamiclights.fast");
                MinecraftClient.getInstance().reloadResources();
//                MinecraftClient.getInstance().setScreen(new SettingsScreen(this.parent));

            } else if (graphid == 2) {
                this.config.setDynamicLightsMode(DynamicLightsMode.FANCY);
                this.config.setCreeperLightingMode(ExplosiveLightingMode.FANCY);
                this.config.setTntLightingMode(ExplosiveLightingMode.FANCY);
                this.config.setBlockEntitiesLightSource(true);
                this.config.setWaterSensitiveCheck(true);
                this.config.setEntitiesLightSource(true);
                this.config.save();
                this.text = new TranslatableText("simplerlightingoptions.dynamiclights.fancy").formatted(Formatting.ITALIC);
                MinecraftClient.getInstance().reloadResources();
//                MinecraftClient.getInstance().setScreen(new SettingsScreen(this.parent));

            }
        }
    }

    @Override
    public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
        return new ButtonWidget(x, y, width, 20, this.text, btn ->
                RefreshButton());
    }
}
