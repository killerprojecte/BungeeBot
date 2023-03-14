package org.rgbmc.bungeebot

import com.xbaimiao.mirai.packet.impl.websocket.WebSocketBot
import com.xbaimiao.mirai.packet.impl.websocket.WsInfo
import flyproject.iplibrary.IPLibrary
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import org.rgbmc.bungeebot.database.MySQLData
import org.rgbmc.bungeebot.database.SQLData
import java.io.File
import java.io.IOException
import java.nio.file.Files

class BungeeBot : Plugin() {
    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        sqlData = MySQLData()
        saveDatabase()
        ipLibrary = IPLibrary(File(dataFolder, "data.db"))
        logger.info("BungeeBot 由 FlyProject 开发")
        logger.info("本插件采用 GPLv3 协议开源")
        logger.info("[!] mirai-http-sdk框架由 xbaimiao 开发")
        logger.info("[!] IPLibrary[闭源] 由 FlyProject 开发 (所用IP库来自互联网 最后更新日期: 2022)")
        logger.info("[!] 本插件不支持命令重载配置")
        logger.info("> 正在登录机器人...")
        bot = WebSocketBot(
            WsInfo(
                config!!.getString("bot.url"),
                config!!.getLong("bot.qq"),
                config!!.getString("bot.key")
            )
        )
        bot!!.connect()
        bot!!.join()
        bot!!.autoReconnect(60)
        bot!!.eventChancel.subscribe(CodeListener())
        proxy.pluginManager.registerListener(this, BungeeJoinListener())
        // Plugin startup logic
    }

    private fun saveDefaultConfig() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        val file = File(dataFolder, "config.yml")
        if (!file.exists()) {
            try {
                getResourceAsStream("config.yml").use { `in` -> Files.copy(`in`, file.toPath()) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveDatabase() {
        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }
        val file = File(dataFolder, "data.db")
        if (!file.exists()) {
            try {
                getResourceAsStream("data.db").use { `in` -> Files.copy(`in`, file.toPath()) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    val config: Configuration?
        get() {
            if (configuration == null) {
                try {
                    configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(
                        File(
                            dataFolder, "config.yml"
                        )
                    )
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }
            return configuration
        }

    companion object {
        private var bot: WebSocketBot? = null
        var instance: BungeeBot? = null
        private var configuration: Configuration? = null
        var sqlData: SQLData? = null
        var ipLibrary: IPLibrary? = null
    }
}