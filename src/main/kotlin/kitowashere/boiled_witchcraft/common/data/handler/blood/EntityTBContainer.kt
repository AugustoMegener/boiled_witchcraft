package kitowashere.boiled_witchcraft.common.data.handler.blood

import kitowashere.boiled_witchcraft.common.data.handler.blood.AttachedTBHandler.Companion.titanBlood
import net.minecraft.world.entity.Entity

class EntityTBContainer(val entity: Entity) : SourcedTBContainer() {
    private val selfContainer = AttachedTBHandler(entity)

    init {
        add { selfContainer }
        add { entity.level().getChunkAt(entity.onPos).titanBlood }
    }
}