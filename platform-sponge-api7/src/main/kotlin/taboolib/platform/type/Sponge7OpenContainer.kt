package taboolib.platform.type

import org.spongepowered.api.plugin.PluginContainer
import taboolib.common.OpenContainer
import taboolib.common.OpenResult
import taboolib.common.reflect.Reflex.Companion.getProperty
import taboolib.common.reflect.Reflex.Companion.invokeMethod

/**
 * TabooLib
 * taboolib.platform.type.SpongeOpenContainer
 *
 * @author sky
 * @since 2021/7/3 1:44 上午
 */
class Sponge7OpenContainer(plugin: PluginContainer) : OpenContainer {

    private val name = plugin.id
    private val main = plugin.instance.get().javaClass.name
    private val clazz = try {
        Class.forName(main.substring(0, main.length - "platform.Sponge7Plugin".length) + "common.OpenAPI")
    } catch (ignored: Throwable) {
        null
    }

    override fun getName(): String {
        return name
    }

    override fun call(name: String, args: Array<Any>): OpenResult {
        val result = clazz?.invokeMethod<Any>("call", name, args, fixed = true) ?: return OpenResult.failed()
        return OpenResult(result.getProperty("successful")!!, result.getProperty("value"))
    }
}