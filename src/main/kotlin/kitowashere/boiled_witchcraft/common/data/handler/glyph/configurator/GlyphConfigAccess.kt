package kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator

import kitowashere.boiled_witchcraft.common.data.util.WrapWay

class GlyphConfigAccess(val configurator: IGlyphConfiguratorHandler) {
    private val wrappers: Array<(WrapWay) -> Unit> = arrayOf(configurator::wrapGroup,
                                                             configurator::wrapGlyph,
                                                             configurator::wrapField,
                                                             configurator::editField)

    var wrapperIndex = 0
        private set(value) { field = when { value < 0 ->  wrappers.size
                                            value >= wrappers.size -> 0
                                            else -> value                }  }


    fun wrapWrappers(way: WrapWay)  { wrapperIndex += way.value     }
    fun wrap(way: WrapWay)          { wrappers[wrapperIndex](way)   }
}