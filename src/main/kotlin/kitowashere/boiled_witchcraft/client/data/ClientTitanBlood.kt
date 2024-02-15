package kitowashere.boiled_witchcraft.client.data

object ClientTitanBlood {
    var currentChunkTitanBlood: Int by ClientTBSource()

    val totalTitanBloodAmount: Int get() = ClientTBSource.all
}