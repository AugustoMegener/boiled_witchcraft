package kitowashere.boiled_witchcraft.common.world.glyph.data.editor

import kitowashere.boiled_witchcraft.common.util.WrapWay
import kitowashere.boiled_witchcraft.common.util.Wrapable
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

open class GlyphEditor(stages: MutableList<StageBuilder.() -> Unit>,
                       var stack: GlyphStack = GlyphStack()) : Wrapable
{
    private val stages = stages.map { GlyphEditorStage(StageBuilder().also { s -> it(s) }) }
    private var stage = this.stages[0]
    val info get() = stage.info(this, wrappedIndex)
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

    private inner class GlyphEditorStage(builder: StageBuilder) : Wrapable {
        val action  = builder.stageAction
        val name    = builder.stageName
        val info    = builder.stageInfo

        override var wrappedIndex = 0

        override fun wrap(way: WrapWay) { super.wrap(way); action(this@GlyphEditor, wrappedIndex, way) }
    }

    class StageBuilder {
        lateinit var stageAction: EditorAction
        lateinit var stageName: MutableComponent
        lateinit var stageInfo: EditorInfo

        fun action(action: EditorAction) { stageAction = action }
        fun name(name: MutableComponent) { stageName = name }
        fun info(info: EditorInfo) { stageInfo = info }

    }
}

typealias EditorAction = (GlyphEditor, Int, WrapWay) -> Unit
typealias EditorInfo = (GlyphEditor, Int) -> MutableComponent
