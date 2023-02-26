package fan.yumetsuki.kekkon_system

import fan.yumetsuki.kekkon_system.impl.DefaultKekkonSystem
import fan.yumetsuki.kekkon_system.impl.User

/**
 * 执行结婚操作时，目标对象已经和自己有结婚关系
 */
class AlreadySelfKekkonException(
    message: String? = null
) : Throwable(message)

/**
 * 执行结婚操作时，目标对象已经和他人有结婚关系
 * 当然，实现中不建议抛出这个异常，因为，老婆就应该是可共享的(
 */
class AlreadyOtherKekkonException(
    message: String? = null
) : Throwable(message)

/**
 * 执行离婚操作时，两个对象之间并没有结婚关系
 */
class NotKekkonException(
    message: String? = null
) : Throwable(message)

/**
 * 执行换老婆操作时，新旧老婆是同一个
 */
class SameWifeException(
    message: String? = null
) : Throwable(message)

/**
 * 执行 ntr 操作时，你是他的老婆，但女主是你，你不能和你自己成为老婆
 */
class SameOperatorException(
    message: String? = null
) : Throwable(message)

/**
 * 结婚系统，该系统中 User 对象之间可以做包含但不限于 结婚、NTR 等让人头大的操作
 * @param User User 泛型，它可以是任意的对象，受限于具体实现类
 */
interface KekkonSystem<User: Any> {

    /**
     * 两个人结婚，该 API 让参数中的两个对象绑定结婚关系
     * 推荐结婚关系是可重复的，也就是 "她可以是你的老婆也可以是我的老婆" ，但同时 "你也可以是我的老婆的同时，也是她的老婆"
     * 每个人都互相为老婆，这当然很棒！
     * @param self 某个可结婚的对象
     * @param other 另一个某个可结婚的对象
     * @throws AlreadySelfKekkonException 这个异常经常会抛出，大概，毕竟没人能阻止你和你老婆重复结婚
     * @throws AlreadyOtherKekkonException 这个异常通常情况下不应当被抛出，如果真有，那你就去喷开发者为什么不让你重婚
     * @return 返回你的结婚对象的其它老婆！！！这里面不包含你自己嗷
     */
    suspend fun kekkon(self: User, other: User): List<User>

    /**
     * 两个人离婚，解除两个对象的绑定关系
     * @param first 某个可离婚的对象
     * @param second 另一个某个可离婚的对象
     * @throws NotKekkonException
     */
    suspend fun rikon(first: User, second: User)

    /**
     * ntr 了某个人，ntrbiss，对，ntrbiss，但是你仍然可以 ntr 别人，至于你能不能 ntr 到，就看本事了
     * 当你 ntr 了别人的时候，女主的选择可能是随机的，这就看你怎么实现了，反正，我会随机挑一个
     * ntr 后，女主将会与操作者绑定结婚关系
     * @param operator 执行 ntr 的某人
     * @param target 被 ntr 的某人
     * @throws AlreadySelfKekkonException 他老婆也是你老婆，所以，这能算 ntr 么？
     * @return NTR 的女主，苦主的老婆，可能随机挑了一个 ntr，如果 ntr 失败，则会返回 null
     */
    suspend fun ntr(operator: User, target: User): User?

    /**
     * 列出某个人的老婆列表，老婆关系应当是互相的，因此，如果你是我老婆，那我也是你的老婆
     * @param operator 执行列出操作的某人
     * @return 操作者的老婆列表
     */
    suspend fun wife(operator: User): List<User>

    /**
     * 换老婆，会让执行者的某个老婆，被换成另一个，要是换到了别人的老婆....那也算 ntr 吧？
     * 会先解除被换掉的结婚关系，再与另一个对象绑定结婚对象
     * @param operator 要换老婆的某人
     * @param oldWife 要被换掉的老婆
     * @param newWife 新的老婆
     * @throws SameWifeException 鬼知道会不会换同一个老婆...
     */
    suspend fun changeWife(operator: User, oldWife: User, newWife: User)

    companion object Default : KekkonSystem<User> by DefaultKekkonSystem()
}