package kitowashere.boiled_witchcraft.common.capabilities

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.capabilities.EntityCapability

object Caps {
    object Entity {
        val entityGlyphEditor: EntityCapability<GlyphEditor, Void> =
            EntityCapability.create(parse(("$ID:glyph_editor_entity")), GlyphEditor::class.java, Void::class.java)

        val Player.glyphEditor get() = this.getCapability(entityGlyphEditor)!!
    }
}