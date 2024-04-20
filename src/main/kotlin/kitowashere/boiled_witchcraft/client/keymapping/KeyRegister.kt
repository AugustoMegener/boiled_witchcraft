package kitowashere.boiled_witchcraft.client.keymapping

abstract class KeyRegister {

    init {
        Keymapping.keyBuilders.add { keyBuilder(Keymapping) }
    }

    abstract val keyBuilder: Keymapping.() -> Unit
}