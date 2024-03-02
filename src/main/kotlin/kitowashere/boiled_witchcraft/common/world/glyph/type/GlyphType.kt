package kitowashere.boiled_witchcraft.common.world.glyph.type

import jdk.jfr.Experimental
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.data.handler.blood.ITitanBloodHandler
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.GlyphKind
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceKey.createRegistryKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.registries.DeferredRegister

/**
 * Class representing a type of glyph.
 *
 * this class is used to define glyph types, which is registered by a [DeferredRegister].
 * This class contains events that can be executed by external classes that represent it, thus defining common actions.
 *
 * For the purposes of this class, consider "instance" to be any object that uses a [GlyphData].
 *
 * @param T The glyph's [GlyphData] class that will be used.
 * @param kind The [GlyphKind] of this glyph.
 * @param data A supplier for glyph's [GlyphData].
 * @param sizes List of possible sizes for the glyph considering its side as a square.
 *
 * @see GlyphData
 */
abstract class GlyphType<T : GlyphData>(val kind: GlyphKind,
                                        private val data: () -> T,
                                        val sizes: List<Int> = listOf(1, 2, 3))
{
    // ignore everything below this, it's horrible, I'll fix it eventually

    /**
     * Class representing events coming from the glyph.
     *
     * This class serves as an abstraction of possible events for the glyph.
     * Automatically handles the titanic blood consumption associated
     * with the action and performs it only if the blood can be extracted.
     * An additional argument is associated with the event to assist in executing the event.
     *
     * This is an experimental concept,
     * it is likely to change to more efficiently support the creation of custom events.
     *
     * @param C The type of the event argument.
     * @param cost The calculation for the cost of the event.
     * Receives the [GlyphData] of the instance and the event argument to perform the calculation.
     * @param action The action that must be executed by the event receives an [ITitanBloodHandler] with the blood
     * that was consumed with the action, the [GlyphData] of the instance and the event argument.
     */
    @Experimental
    inner class GlyphEvent<C>(private val cost: (T, C) -> Int,
                              private val action: (ITitanBloodHandler, T, C) -> Unit)
    {
        /**
         * Executes the event.
         *
         * Use on a glyph instance to execute the desired event.
         *
         * @param handler The source of the blood that will be consumed.
         * @param data The [GlyphData] of the instance.
         * @param arg The argument of the event.
         */
        operator fun invoke(handler: ITitanBloodHandler, data: GlyphData, arg: C) {
            handler.useThenDo(cost.invoke(data.cast(), arg), { action.invoke(it, data.cast(), arg) })
        }
    }

    /**
     * Template for creating events.
     *
     * Sometimes glyphs can have similar events to each other, to deal with this,
     * this class is used that allows inferring an additional argument for the assembly of this event.
     *
     * @param T The [GlyphData] required for the template.
     * @param C The event argument that will be created by the template.
     * @param E The type of the additional argument for mounting the event.
     *
     * @param cost The cost of the event that will be created.
     * @param action The action of the event that will be created.
     *
     * @see [Util.EventTemplates]
     */
    @Experimental
    data class GlyphEventTemplate<T: GlyphData, C, E>(val cost: (T, C, E) -> Int,
                                                      val action: (ITitanBloodHandler, T, C, E) -> Unit)

    protected fun <C, E> templateEvent(template: GlyphEventTemplate<T, C, E>, extraArg: E) =
        GlyphEvent( { t: T, c: C -> template.cost(t, c, extraArg) },
                    { h: ITitanBloodHandler, t: T, c: C -> template.action(h, t, c, extraArg) })

    protected fun <T> emptyEvent() = GlyphEvent<T>({_, _ -> 0}, {_, _, _ ->})


    data class BlockEventCtx(val level: Level, val pos: BlockPos,   val blockState: BlockState,
                             val blockEntity: GlyphBlockEntity,     val author: Entity? = null,
                             val direction: Direction? = null,      val redstonePower: Int = 0)


    /**
     * Occurs when an entity steps on the glyph block.
     */
    val blockStepped: GlyphEvent<Entity> = emptyEvent()
    /**
     * Occurs when a player click on a glyph block.
     */
    val blockClicked: GlyphEvent<Player> = emptyEvent()
    /**
     * Occurs when the glyph block receives a redstone signal.
     */
    val blockPowered: GlyphEvent<BlockEventCtx> = emptyEvent()


    /**
     * Occurs after [blockStepped], [blockClicked] or [blockPowered],
     * it is the main event of interacting with glyph blocks.
     */
    abstract val blockInteracted: GlyphEvent<BlockEventCtx>
    /**
     * Occurs when the item is placed in a block.
     */
    abstract val itemPutted: GlyphEvent<BlockEventCtx>

    /**
     * Occurs when the item is used/clicked in midair.
     */
    abstract val itemUsed: GlyphEvent<Player?>

    // below this is fine ig

    fun newData() = data.invoke()

    companion object {
        val placeholder: FireGlyphType; get() = GlyphTypeRegistry.fireGlyph.get()

        val registryKey: ResourceKey<Registry<GlyphType<*>>> =
            createRegistryKey(ResourceLocation("glyph_types"))
    }

    enum class GlyphKind { PRIMARY, COMPOUND }
}