package kitowashere.boiled_witchcraft.common.data.handler.blood

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.titanBlood
import net.minecraft.world.entity.Entity

class EntityTBContainer(val entity: Entity) : SourcedTBContainer() {
    init { add { entity.level().getChunkAt(entity.onPos).titanBlood } }
}