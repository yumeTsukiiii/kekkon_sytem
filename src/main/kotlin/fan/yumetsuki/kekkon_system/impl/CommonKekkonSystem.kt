package fan.yumetsuki.kekkon_system.impl

import fan.yumetsuki.kekkon_system.*

class CommonKekkonSystem<User: Any>(
    private val dataSource: DataSource<User>
): KekkonSystem<User> {

    override suspend fun kekkon(self: User, other: User): List<User> {
        if (self == other) {
            throw SameOperatorException()
        }
        if (dataSource.hasAssociation(self to other)) {
            throw AlreadySelfKekkonException()
        }
        dataSource.appendAssociation(self to other)
        return dataSource.queryAssociationData(other).filter { it != self }
    }

    override suspend fun rikon(first: User, second: User) {
        if (!dataSource.hasAssociation(first to second)) {
            throw NotKekkonException()
        }
        dataSource.deleteAssociation(first to second)
    }

    override suspend fun ntr(operator: User, target: User): User? {
        val relateUsers = dataSource.queryAssociationData(target)
        return relateUsers.randomOrNull()?.also { targetRandomRelate ->
            if (targetRandomRelate == operator) {
                throw SameOperatorException()
            }
            if (dataSource.hasAssociation(operator to targetRandomRelate)) {
                throw AlreadySelfKekkonException()
            }
            dataSource.appendAssociation(operator to targetRandomRelate)
            dataSource.deleteAssociation(target to targetRandomRelate)
        }
    }

    override suspend fun wife(operator: User): List<User> {
        return dataSource.queryAssociationData(operator)
    }

    override suspend fun changeWife(operator: User, oldWife: User, newWife: User) {
        if (oldWife == newWife) {
            throw SameWifeException()
        }
        if (dataSource.hasAssociation(operator to oldWife)) {
            throw NotKekkonException()
        }
        if (dataSource.hasAssociation(operator to newWife)) {
            throw AlreadySelfKekkonException()
        }
        dataSource.deleteAssociation(operator to oldWife)
        dataSource.appendAssociation(operator to newWife)
    }

}