package kitowashere.boiled_witchcraft.server.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.titanBlood
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.DimensionArgument
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import thedarkcolour.kotlinforforge.neoforge.forge.vectorutil.v3d.toVec3i

/**
 * A command to manipulate the titan blood on a chunk.
 */
object ChunkBloodCommand: RegistrableCommand {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("chunk_blood").requires { it.hasPermission(2) }
                .then(
                    Commands.argument("location", Vec3Argument.vec3()).then(
                        Commands.argument("dimension", DimensionArgument.dimension())

                        .then(
                            Commands.literal("set")
                                .then(Commands.argument("value", IntegerArgumentType.integer())).executes(::set)
                        )
                        .then(
                            Commands.literal("add")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())).executes(::add)
                        )
                        .then(
                            Commands.literal("sub")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())).executes(::sub)
                        )

                        .then(Commands.literal("see")).executes(::see)
                    )
                )
        )
    }

    private fun see(commandContext: CommandContext<CommandSourceStack>): Int {
        val blood = DimensionArgument.getDimension(commandContext, "dimension")
                .getChunkAt(BlockPos(Vec3Argument.getVec3(commandContext, "location").toVec3i())).titanBlood

        commandContext.source.sendSuccess({
            Component.translatable(ID + "commands.chunk_blood.see").append(": $blood")
        }, true)

        return blood
    }

    private fun set(commandContext: CommandContext<CommandSourceStack>): Int {
        val chunk = DimensionArgument.getDimension(commandContext, "dimension")
            .getChunkAt(BlockPos(Vec3Argument.getVec3(commandContext, "location").toVec3i()))

        chunk.titanBlood = IntegerArgumentType.getInteger(commandContext, "value")

        commandContext.source.sendSuccess({
            Component.translatable(ID + "commands.chunk_blood.set").append(": ${chunk.titanBlood}")
        }, true)

        return chunk.titanBlood
    }

    private fun add(commandContext: CommandContext<CommandSourceStack>): Int {
        val chunk = DimensionArgument.getDimension(commandContext, "dimension")
            .getChunkAt(BlockPos(Vec3Argument.getVec3(commandContext, "location").toVec3i()))

        chunk.titanBlood += IntegerArgumentType.getInteger(commandContext, "amount")

        commandContext.source.sendSuccess({
            Component.translatable(ID + "commands.chunk_blood.add").append(": ${chunk.titanBlood}")
        }, true)

        return chunk.titanBlood
    }

    private fun sub(commandContext: CommandContext<CommandSourceStack>): Int {
        val chunk = DimensionArgument.getDimension(commandContext, "dimension")
            .getChunkAt(BlockPos(Vec3Argument.getVec3(commandContext, "location").toVec3i()))

        chunk.titanBlood -= IntegerArgumentType.getInteger(commandContext, "amount")

        commandContext.source.sendSuccess({
            Component.translatable(ID + "commands.chunk_blood.sub").append(": ${chunk.titanBlood}")
        }, true)

        return chunk.titanBlood
    }
}