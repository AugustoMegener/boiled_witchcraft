package kitowashere.boiled_witchcraft.common.world.glyph.data.editor

import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph.GlyphEditorHandler
import kitowashere.boiled_witchcraft.common.util.WrapWay
import kitowashere.boiled_witchcraft.common.util.Wrapable
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.network.chat.MutableComponent

open class GlyphEditor(stages: MutableList<StageBuilder.() -> Unit>,
                       var stack: GlyphStack = GlyphStack()) : Wrapable
{
    val stages = stages.map { GlyphEditorStage(StageBuilder().also { s -> it(s) }) }
    var stage = this.stages[0]; private set

    val info get() = stage.info
    val name get() = stage.name

    override var wrappedIndex = 0
        set(value) {
            field = value

            if (field < 0)
                field = stages.lastIndex
            else if (field > stages.lastIndex)
                field = 0

            stage = stages[field]
        }

    override fun wrap(way: WrapWay) {
        super.wrap(way)
        onChanged()
    }

    fun edit(way: WrapWay) {
        stage.wrap(way)
        onChanged()
    }

    open fun onChanged() {}

    inner class GlyphEditorStage(builder: StageBuilder) : Wrapable {
        val infoGetter = builder.stageInfo

        val action           = builder.stageAction
        val name             = builder.stageName
        val info       get() = infoGetter(this@GlyphEditor, wrappedIndex)

        val renderer= builder.renderer

        val isActual get() = stage == this

        override var wrappedIndex = 0

        override fun wrap(way: WrapWay) { super.wrap(way); action(this@GlyphEditor, wrappedIndex, way) }


    }

    class StageBuilder {
        lateinit var stageAction: EditorAction
        lateinit var stageName: MutableComponent
        lateinit var stageInfo: EditorInfo
        var renderer: (GlyphEditorHandler.() -> SectionRenderer)? = null

        fun action(action: EditorAction) { stageAction = action }
        fun name(name: MutableComponent) { stageName = name }
        fun info(info: EditorInfo) { stageInfo = info }
        fun renderer(sectionRenderer: GlyphEditorHandler.() -> SectionRenderer) { renderer = sectionRenderer }
    }


}

typealias EditorAction = (GlyphEditor, Int, WrapWay) -> Unit
typealias EditorInfo = (GlyphEditor, Int) -> MutableComponent
