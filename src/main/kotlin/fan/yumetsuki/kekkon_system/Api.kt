package fan.yumetsuki.kekkon_system

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

data class WifePair<User>(
    val oldWife: User,
    val newWife: User
)

/**
 * 结婚系统，该系统中 User 对象之间可以做包含但不限于 结婚、NTR 等让人头大的操作
 * @param User User 泛型，它可以是任意的对象，受限于具体实现类
 */
interface KekkonSystem<User: Any> {

    /**
     * 两个人结婚，该 API 让参数中的两个对象绑定结婚关系
     * 推荐结婚关系是可重复的，也就是 "她可以是你的老婆也可以是我的老婆" ，但同时 "你也可以是我的老婆的同时，也是她的老婆"
     * 每个人都互相为老婆，这当然很棒！
     * @param first 某个可结婚的对象
     * @param second 另一个某个可结婚的对象
     * @throws AlreadySelfKekkonException 这个异常经常会抛出，大概，毕竟没人能阻止你和你老婆重复结婚
     * @throws AlreadyOtherKekkonException 这个异常通常情况下不应当被抛出，如果真有，那你就去喷开发者为什么不让你重婚
     */
    fun <User> kekkon(first: User, second: User)

    /**
     * 两个人离婚，解除两个对象的绑定关系
     * @param first 某个可离婚的对象
     * @param second 另一个某个可离婚的对象
     * @throws NotKekkonException
     */
    fun <User> rikon(first: User, second: User)

    /**
     * 强行和某人涩涩了，当然，你不仅可以和自己老婆涩涩，你也可以和别人老婆涩涩
     * @param operator 执行涩涩的某人
     * @param target 被涩涩的某人
     * @return 被涩涩的人的结婚对象列表，如果你 fu*k 了别人的老婆，那么这个列表里就会有别人，否则当然只有你自己
     * @return 但如果这个列表是空的，说明这个人还是单身，要不要对她负责，就看你自己咯
     */
    fun <User> fuck(operator: User, target: User): List<User>

    /**
     * ntr 了某个人，ntrbiss，对，ntrbiss，但是你仍然可以 ntr 别人，至于你能不能 ntr 到，就看本事了
     * 当你 ntr 了别人的时候，女主的选择可能是随机的，这就看你怎么实现了，反正，我会随机挑一个
     * ntr 后，女主将会与操作者绑定结婚关系
     * @param operator 执行 ntr 的某人
     * @param target 被 ntr 的某人
     * @return NTR 的女主，苦主的老婆，可能随机挑了一个 ntr，如果 ntr 失败，则会返回 null
     */
    fun <User> ntr(operator: User, target: User): User?

    /**
     * 列出某个人的老婆列表，老婆关系应当是互相的，因此，如果你是我老婆，那我也是你的老婆
     * @param operator 执行列出操作的某人
     * @return 操作者的老婆列表
     */
    fun <User> wife(operator: User): List<User>

    /**
     * 换老婆，会让执行者的某个老婆，被换成另一个，要是换到了别人的老婆....那也算 ntr 吧？
     * 会先解除被换掉的结婚关系，再与另一个对象绑定结婚对象
     * @param operator 要换老婆的某人
     * @return 被换掉的旧老婆，以及获得的新老婆
     */
    fun <User> changeWife(operator: User): WifePair<User>

}