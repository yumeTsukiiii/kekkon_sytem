package fan.yumetsuki.kekkon_system

interface DataSource<Data: Any> {

    suspend fun queryAssociationData(data: Data): List<Data>

    suspend fun hasAssociation(association: Pair<Data, Data>): Boolean

    suspend fun appendAssociation(association: Pair<Data, Data>)

    suspend fun deleteAssociation(association: Pair<Data, Data>)

}