package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.event.KSubscribe
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthorType
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOptionType
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.EditorOptionKitType
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceKey.createRegistryKey
import net.neoforged.neoforge.registries.NewRegistryEvent
import net.neoforged.neoforge.registries.RegistryBuilder

@Scan
object Registries {
    val glyphRegistryKey: ResourceKey<Registry<Glyph<*>>> =
        createRegistryKey(local("glyph"))

    val editorOptionTypeRegistryKey: ResourceKey<Registry<EditorOptionType<*>>> =
        createRegistryKey(local("editor_option"))

    val editorOptionKitTypeRegistryKey: ResourceKey<Registry<EditorOptionKitType<*>>> =
        createRegistryKey(local("editor_option_type"))

    val glyphAuthorTypeRegistryKey: ResourceKey<Registry<GlyphAuthorType<*>>> =
        createRegistryKey(local("glyph_author_type"))

    lateinit var               glyphRegistry: Registry<Glyph<*>>                private set

    lateinit var    editorOptionTypeRegistry: Registry<EditorOptionType<*>>     private set

    lateinit var editorOptionKitTypeRegistry: Registry<EditorOptionKitType<*>>  private set

    lateinit var     glyphAuthorTypeRegistry: Registry<GlyphAuthorType<*>>  private set


    val haveGlyphRegistry get() = ::glyphRegistry.isInitialized

    @KSubscribe
    fun NewRegistryEvent.createNewRegistry() {
        glyphRegistry = create(RegistryBuilder(glyphRegistryKey))
        editorOptionTypeRegistry = create(RegistryBuilder(editorOptionTypeRegistryKey))
        editorOptionKitTypeRegistry = create(RegistryBuilder(editorOptionKitTypeRegistryKey))
        glyphAuthorTypeRegistry = create(RegistryBuilder(glyphAuthorTypeRegistryKey))
    }
}