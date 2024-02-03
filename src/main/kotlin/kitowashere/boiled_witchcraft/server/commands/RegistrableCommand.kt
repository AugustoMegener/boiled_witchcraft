package kitowashere.boiled_witchcraft.server.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack

/**
 * Interface that requests a function that allows the command to be registered.
 */
interface RegistrableCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>)
}