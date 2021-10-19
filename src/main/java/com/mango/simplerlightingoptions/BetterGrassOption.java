package com.mango.simplerlightingoptions;

import dev.lambdaurora.lambdabettergrass.LBGConfig;
import dev.lambdaurora.lambdabettergrass.LBGMode;
import dev.lambdaurora.lambdabettergrass.LambdaBetterGrass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class BetterGrassOption extends Option {
    private final LBGConfig config;
    private static final String KEY = null;
    private Text text;

    private final Screen parent;
    private int graphid;


    public BetterGrassOption(LBGConfig config, Screen parent) {
        super(KEY);
        this.config = LambdaBetterGrass.get().config;
        this.text = null;
        this.parent = parent;
        this.graphid = 1;
        this.config.load();
        if(this.config.getMode() == LBGMode.FANCY) {
            this.graphid = 2;
            this.text = new TranslatableText("simplerlightingoptions.bettergrass.fancy").formatted(Formatting.ITALIC);

        }
        else if(this.config.getMode() == LBGMode.FAST) {
            this.graphid = 1;
            this.text = new TranslatableText("simplerlightingoptions.bettergrass.fast");
        }
        else if(this.config.getMode() == LBGMode.OFF) {
            this.graphid = 0;
            this.text = new TranslatableText("simplerlightingoptions.bettergrass.off");
        }
    }



    public void RefreshButton() {
        if (graphid == 2) {
            graphid = 0;
            this.config.setMode(LBGMode.OFF);
            this.config.setBetterLayer(false);
            this.text = new TranslatableText("simplerlightingoptions.bettergrass.off");
            MinecraftClient.getInstance().reloadResources();
//            MinecraftClient.getInstance().setScreen(new SettingsScreen(this.parent));
        }
        else {
            graphid++;
            if (graphid == 1) {
                this.config.setMode(LBGMode.FASTEST);
                this.config.setBetterLayer(false);
                this.text = new TranslatableText("simplerlightingoptions.bettergrass.fast");
                MinecraftClient.getInstance().reloadResources();
//                MinecraftClient.getInstance().setScreen(new SettingsScreen(this.parent));

            }
            else if (graphid == 2) {
                this.config.setMode(LBGMode.FANCY);
                this.config.setBetterLayer(true);
                this.text = new TranslatableText("simplerlightingoptions.bettergrass.fancy").formatted(Formatting.ITALIC);
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
