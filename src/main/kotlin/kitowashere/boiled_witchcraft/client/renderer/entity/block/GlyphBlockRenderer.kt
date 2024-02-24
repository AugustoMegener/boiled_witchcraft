package kitowashere.boiled_witchcraft.client.renderer.entity.block

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import kitowashere.boiled_witchcraft.client.renderer.Sheets.glyphMaterial
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphBlockEntity
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.resources.model.Material


class GlyphBlockRenderer(private val ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<GlyphBlockEntity> {

    private var glyph: GlyphType<*> = GlyphType.placeholder;    set(value) { field = value; updateMaterial() }
    private var size = 1;                                       set(value) { field = value; updateMaterial() }

    private lateinit var material: Material

    private fun updateMaterial() {
        material = materials.computeIfAbsent(Pair(glyph, size)) { p -> glyphMaterial(p.first, p.second) }
    }

    override fun render(
        pBlockEntity: GlyphBlockEntity, pPartialTick: Float, pPoseStack: PoseStack,
        pBuffer: MultiBufferSource, pPackedLight: Int, pPackedOverlay: Int,
    ) {
        glyph.takeIf { it != pBlockEntity.glyphType }?.let { glyph = it }
        size.takeIf { it != pBlockEntity.size }?.let { size = it }

        val sprite = material.sprite()

        val buffer = material.buffer(pBuffer, RenderType::entityCutout)

        pPoseStack.pushPose()

        listOf(
            Triple(0F, 0F, 0F), Triple(1F, 0F, 0F),
            Triple(0F, 0F, 1F), Triple(1F, 0F, 1F),
            Triple(0F, 0.25F, 0F), Triple(1F, 0.25F, 0F),
            Triple(0F, 0.25F, 1F), Triple(1F, 0.25F, 1F)
        ).forEach {vertex(pPoseStack.last(), buffer, it.first, it.second, it.third, sprite.u0, sprite.v0, pPackedLight)}

        pPoseStack.popPose()

        ctx.blockEntityRenderDispatcher.render(pBlockEntity, pPartialTick, pPoseStack) { buffer }
    }

    private fun vertex(matrixEntryIn: PoseStack.Pose, bufferIn: VertexConsumer,
                       x: Float, y: Float, z: Float, texU: Float, texV: Float, packedLight: Int)
    {
        bufferIn.vertex(matrixEntryIn.pose(), x, y, z)
            .color(1f, 1f, 1f, 1f)
            .uv(texU, texV).uv2(packedLight)
            .normal(1f, 0f, 0f)
            .endVertex()
    }

    companion object {
        private val materials: HashMap<Pair<GlyphType<*>, Int>, Material> = HashMap()
    }
}