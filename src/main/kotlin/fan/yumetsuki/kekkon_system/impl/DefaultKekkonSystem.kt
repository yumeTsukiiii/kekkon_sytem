package fan.yumetsuki.kekkon_system.impl

import fan.yumetsuki.kekkon_system.DataSource
import fan.yumetsuki.kekkon_system.KekkonSystem

data class User(
    val id: Long
) {

    var name: String = "null"

}

class DefaultKekkonSystem(
    dataSource: DataSource<User> = MemoryDataSource()
): KekkonSystem<User> by CommonKekkonSystem(dataSource)