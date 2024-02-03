package kitowashere.boiled_witchcraft.client.gui.overlay

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.ClientConfig.tokenScale
import kitowashere.boiled_witchcraft.client.ClientConfig.tokenSpacing
import kitowashere.boiled_witchcraft.client.ClientConfig.tokenVolume
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.totalTitanBloodAmount
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui
import kotlin.math.min
import kotlin.math.pow


object TitanBloodOverlay: Overlay(ID, "titan_blood_overlay") {

    val tokensType: List<BloodTokenType> = listOf(
        BloodTokenType(
            ResourceLocation(ID, "textures/gui/titan_blood/fat_token.png"),
            tokenVolume.toDouble().pow(3).toInt()
        ),
        BloodTokenType(
            ResourceLocation(ID, "textures/gui/titan_blood/full_token.png"),
            tokenVolume.toDouble().pow(2).toInt()
        ),
        BloodTokenType(
            ResourceLocation(ID, "textures/gui/titan_blood/small_token.png"),
            tokenVolume.toDouble().pow(1).toInt()
        )
    )

    var neededTokens: List<BloodTokenType> = tokensType.filter { it.willBeUsed(ClientTitanBlood.totalTitanBloodAmount) }

    override fun render(
        gui: ExtendedGui,
        guiGraphics: GuiGraphics,
        partialTick: Float,
        screenWidth: Int,
        screenHeight: Int,
    ) {
        val spaceByTokenType: Int = screenWidth / neededTokens.size
        val width: Int = neededTokens.sumOf {
            it.getUsedSpace(totalTitanBloodAmount, screenWidth / neededTokens.size)
        }
    }

    /**
     * Class representing a type of titan blood token.
     * Each token represents a fixed amount of blood,
     * renders the maximum amount of tokens based on blood and available space,
     * if it is not possible to render all possible tokens,
     * the last one will have a number representing the amount of hidden tokens.
     * @param texture The [ResourceLocation] of the texture of the blood token.
     * @param value The value of titan blood that the token represents.
     * @param compact If true, instead of rendering the maximum amount of tokens,
     * it will render only one with the number of tokens on top.
     */
    class BloodTokenType(
        private val texture: ResourceLocation,
        private val value: Int,
        private val compact: Boolean = false
    ) {
        fun willBeUsed(bloodAmount: Int): Boolean { return value >= bloodAmount }

        /**
         * Returns the maximum amount of tokens that can be rendered based on available space
         * @param availableWidth The available space.
         * @return The max amount of tokens that can be rendered.
         */
        private fun getMaxTokens(availableWidth: Int): Int = availableWidth / tokenSpacing

        /**
         * Return the highest possible amount of tokens based on the amount of blood available.
         * @param bloodAmount The available blood.
         * @return The max amount of blood available.
         */
        private fun getPossibleTokens(bloodAmount: Int): Int = bloodAmount / value

        /**
         * Returns the number of tokens that will be rendered.
         * @param bloodAmount The amount of blood available
         * @param availableWidth The available blood.
         * @return The amount of tokens that will be used.
         */
        private fun getTokenAmount(bloodAmount: Int, availableWidth: Int): Int =
            if (compact) 1 else min(getMaxTokens(availableWidth), getPossibleTokens(bloodAmount))

        /**
         * Returns the amount of tokens that the last token will be worth.
         * @param bloodAmount The amount of blood available
         * @param availableWidth The available blood.
         * @return The amount of tokens that the last token will be worth.
         */
        private fun getLastTokenValue(bloodAmount: Int, availableWidth: Int): Int {
            return min(1, getMaxTokens(availableWidth) - getPossibleTokens(bloodAmount))
        }

        /**
         * Returns the space used by tokens of this type.
         * @param bloodAmount The amount of blood available
         * @param availableWidth The available blood.
         * @return The space that will be used.
         */
        fun getUsedSpace(bloodAmount: Int, availableWidth: Int): Int =
            tokenSpacing * getTokenAmount(bloodAmount, availableWidth)

        /**
         * Returns the maximum amount of blood it is possible to represent.
         * @param bloodAmount The amount of blood available
         * @param availableWidth The available blood.
         * @return The amount of blood that will be used.
         */
        fun getUsedBlood(bloodAmount: Int, availableWidth: Int): Int =
            tokenSpacing * getTokenAmount(bloodAmount, availableWidth)

        /**
         * Renders all tokens in the overlay.
         * @param gui the overlay GUI.
         * @param guiGraphics The overlay GUI graphics.
         * @param xPos The X pos of the fists token.
         * @param yPos The Y poss of all tokens.
         * @param availableWidth The available width to render.
         * @param bloodAmount The blood amount that the tokens will represent.
         */
        fun render(
            gui: ExtendedGui,
            guiGraphics: GuiGraphics,
            xPos: Int,
            yPos: Int,
            availableWidth: Int,
            bloodAmount: Int
        ) {
            var usedSpace = 0
            for (i in 0..getTokenAmount(bloodAmount, availableWidth)) {
                guiGraphics.blit(
                    texture, xPos + usedSpace, yPos, 0F, 0F,
                    tokenScale, tokenScale, tokenScale, tokenScale
                )
                usedSpace += tokenSpacing
            }

            val lastTokenValue = getLastTokenValue(bloodAmount, availableWidth)
            if (lastTokenValue > if (compact) 0 else 1) {
                guiGraphics.drawCenteredString(
                    gui.font, "$lastTokenValue", xPos + usedSpace + (tokenScale / 2),
                    yPos + (tokenScale / 2), 0xffffff
                )
            }
        }
    }
}