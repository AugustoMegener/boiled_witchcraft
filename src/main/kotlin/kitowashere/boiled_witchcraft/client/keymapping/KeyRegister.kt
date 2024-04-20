package kitowashere.boiled_witchcraft.client.keymapping

abstract class KeyRegister {
    abstract val keyBuilder: Keymapping.() -> Unit
}