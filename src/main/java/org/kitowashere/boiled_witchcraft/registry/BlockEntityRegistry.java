package org.kitowashere.boiled_witchcraft.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.kitowashere.boiled_witchcraft.world.blocks.entities.GlyphBlockEntity;
import org.kitowashere.boiled_witchcraft.world.blocks.entities.SprayerBlockEntity;

import static org.kitowashere.boiled_witchcraft.BoiledWitchcraft.MODID;
import static org.kitowashere.boiled_witchcraft.registry.BlockRegistry.FIRE_GLYPH_BLOCK;
import static org.kitowashere.boiled_witchcraft.registry.BlockRegistry.SPRAYER;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<GlyphBlockEntity>> GLYPH_BLOCK_ENTITY = BLOCK_ENTITIES.register("glyph_block", () -> BlockEntityType.Builder.of(GlyphBlockEntity::new, FIRE_GLYPH_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SprayerBlockEntity>> SPRAYER_BLOCK_ENTITY = BLOCK_ENTITIES.register("sprayer", () -> BlockEntityType.Builder.of(SprayerBlockEntity::new, SPRAYER.get()).build(null));

}
