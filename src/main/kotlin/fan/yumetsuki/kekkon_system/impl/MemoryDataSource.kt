package fan.yumetsuki.kekkon_system.impl

import fan.yumetsuki.kekkon_system.DataSource

class MemoryDataSource<Data: Any>: DataSource<Data> {

    internal val associations: MutableSet<Pair<Data, Data>> = mutableSetOf()

    override suspend fun queryAssociationData(data: Data): List<Data> {
        return associations.filter { it.first == data || it.second == data }.map {
            if (it.first == data) {
                it.second
            } else {
                it.first
            }
        }
    }

    override suspend fun hasAssociation(association: Pair<Data, Data>): Boolean {
        return associations.any {
            it == association || (it.first == it.second && it.second == it.first)
        }
    }

    override suspend fun appendAssociation(association: Pair<Data, Data>) {
        if (hasAssociation(association)) {
            return
        }
        associations.add(association)
    }

    override suspend fun deleteAssociation(association: Pair<Data, Data>) {
        associations.removeIf {
            it == association || (it.first == it.second && it.second == it.first)
        }
    }

}