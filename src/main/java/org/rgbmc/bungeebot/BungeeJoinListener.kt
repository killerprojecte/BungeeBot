package org.rgbmc.bungeebot

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.event.PreLoginEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import org.rgbmc.bungeebot.BungeeBot.Companion.ipLibrary
import org.rgbmc.bungeebot.BungeeBot.Companion.sqlData
import org.rgbmc.bungeebot.utils.Color
import org.rgbmc.bungeebot.utils.IPUpdateData
import org.rgbmc.bungeebot.utils.MapUtil
import org.rgbmc.bungeebot.utils.MaskUtil
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit

class BungeeJoinListener : Listener {
    @EventHandler
    fun onJoin(event: PreLoginEvent) {
        try {
            val ip = (event.connection.socketAddress as InetSocketAddress).address.hostAddress
            val sqlData = sqlData
            if (sqlData!!.hasPlayer(event.connection.name)) {
                val userDTO = sqlData.getData(event.connection.name)
                if (!userDTO.ip.equals(ip, ignoreCase = true)) {
                    val ipData = ipLibrary!!.query(ip)
                    var code: String? = null
                    for (key in MapUtil.updatesMap.keys){
                        val otherData: IPUpdateData = MapUtil.updatesMap.get(key)!!
                        if (otherData.player.equals(event.connection.name)){
                            code = key
                            break
                        }
                    }
                    if (code==null){
                        code = MaskUtil.getRandomString(5)
                    }
                    event.isCancelled = true
                    if (ipData==null){
                        event.cancelReason = Color.color(
                            BungeeBot.instance!!.config!!.getString("kick-msg.ip-unsafe-nodata")
                                .replace("%mask_qq%", MaskUtil.getMaskQQ(userDTO.qq.toString()))
                                .replace("%code%", code.toString())
                                .replace("%qq%", userDTO.qq.toString())
                        )
                    } else {
                        event.cancelReason = Color.color(
                            BungeeBot.instance!!.config!!.getString("kick-msg.ip-unsafe")
                                .replace("%mask_qq%", MaskUtil.getMaskQQ(userDTO.qq.toString()))
                                .replace("%code%", code.toString())
                                .replace("%qq%", userDTO.qq.toString())
                                .replace("%province%", ipData.province)
                                .replace("%city%", ipData.city)
                                .replace("%isp%", ipData.isp)
                                .replace("%area%", ipData.area)
                        )
                    }
                    MapUtil.updatesMap.put(code, IPUpdateData((event.connection.socketAddress as InetSocketAddress).address.hostAddress, event.connection.uniqueId, event.connection.name))
                    ProxyServer.getInstance().scheduler.schedule(BungeeBot.instance, object : Runnable {
                        override fun run() {
                            if (MapUtil.updatesMap.containsKey(code)){
                                MapUtil.updatesMap.remove(code)
                            }
                        }
                    }, 5, TimeUnit.MINUTES)
                }
            } else {
                val ipData = ipLibrary!!.query(ip)
                var code: String? = null
                for (key in MapUtil.registerMap.keys){
                    val otherData: IPUpdateData = MapUtil.registerMap.get(key)!!
                    if (otherData.player.equals(event.connection.name)){
                        code = key
                        break
                    }
                }
                if (code==null){
                    code = MaskUtil.getRandomString(5)
                }
                event.isCancelled = true
                if (ipData==null){
                    event.cancelReason = Color.color(
                        BungeeBot.instance!!.config!!.getString("kick-msg.ip-unsafe-nodata")
                            .replace("%code%", code.toString())
                    )
                } else {
                    event.cancelReason = Color.color(
                        BungeeBot.instance!!.config!!.getString("kick-msg.ip-unsafe")
                            .replace("%code%", code.toString())
                            .replace("%province%", ipData.province)
                            .replace("%city%", ipData.city)
                            .replace("%isp%", ipData.isp)
                            .replace("%area%", ipData.area)
                    )
                }
                MapUtil.registerMap.put(code, IPUpdateData((event.connection.socketAddress as InetSocketAddress).address.hostAddress, event.connection.uniqueId, event.connection.name))
                ProxyServer.getInstance().scheduler.schedule(BungeeBot.instance, object : Runnable {
                    override fun run() {
                        if (MapUtil.registerMap.containsKey(code)){
                            MapUtil.registerMap.remove(code)
                        }
                    }
                }, 5, TimeUnit.MINUTES)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            event.isCancelled = true
            event.cancelReason = Color.color(
                BungeeBot.instance!!.config!!.getString("kick-msg.error")
            )
        }
    }
}