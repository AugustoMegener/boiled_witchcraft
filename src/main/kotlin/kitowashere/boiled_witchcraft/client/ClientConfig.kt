package kitowashere.boiled_witchcraft.client

import net.neoforged.neoforge.common.ModConfigSpec


object ClientConfig {
   
    private val builder = ModConfigSpec.Builder()

    val tokenVolume: Int
        get() = builder
        .comment("Sets how many units of Titan Blood Token a token can have before becoming a superior. Default: 1000")
        .define("gui/blood_tokens/token_volume", 1000).get()

    val tokenScale: Int
        get() = builder
        .comment("Sets the token size in the GUI. Default: 16")
        .define("gui/blood_tokens/token_size", 16).get()

    val tokenSpacing: Int
        get() = builder
            .comment("Sets the distance between tokens. Recommended: half the token_size. Default: 8")
            .define("gui/blood_tokens/token_spacing", 8).get()


    val spec: ModConfigSpec = builder.build();
}


