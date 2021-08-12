package taboolib.module.kether.action

import taboolib.library.kether.ParsedAction
import taboolib.library.kether.ArgTypes
import taboolib.common.platform.info
import taboolib.module.kether.KetherParser
import taboolib.module.kether.ScriptAction
import taboolib.module.kether.ScriptFrame
import taboolib.module.kether.scriptParser
import java.util.concurrent.CompletableFuture

/**
 * @author IzzelAliz
 */
class ActionPrint(val message: ParsedAction<*>) : ScriptAction<Void>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        return frame.newFrame(message).run<Any>().thenAccept {
            info(it.toString().trimIndent())
        }
    }

    internal object Parser {

        @KetherParser(["log", "print"])
        fun parser() = scriptParser {
            ActionPrint(it.next(ArgTypes.ACTION))
        }
    }
}