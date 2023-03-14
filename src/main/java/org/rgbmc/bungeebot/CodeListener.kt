package org.rgbmc.bungeebot

import com.xbaimiao.mirai.event.GroupMessageEvent
import com.xbaimiao.mirai.eventbus.SubscribeHandler
import com.xbaimiao.mirai.eventbus.SubscribeListener
import com.xbaimiao.mirai.message.component.impl.PlainText
import org.rgbmc.bungeebot.data.UserDTO
import org.rgbmc.bungeebot.database.SQLData
import org.rgbmc.bungeebot.utils.MapUtil

class CodeListener : SubscribeListener {
    @SubscribeHandler
    fun onGroupChat(event: GroupMessageEvent) {
        val message = event.message.contentToString()
        if (MapUtil.updatesMap.containsKey(message)) {
            val ipUpdateData = MapUtil.updatesMap.get(message)!!
            val userData = BungeeBot.sqlData!!.getData(ipUpdateData.player)
            if (event.sender.id != userData.qq) return
            userData.ip = ipUpdateData.ip
            BungeeBot.sqlData!!.createOrUpdate(userData)
            MapUtil.updatesMap.remove(message)
            event.group.quoteMessage(
                PlainText(BungeeBot.instance!!.config!!.getString("group-msg.ip-update")),
                event.messageSource.messageId.toString()
            )
        } else if (MapUtil.registerMap.containsKey(message)) {
            if (SQLData.playerDTOs.queryBuilder().where().eq("qq", event.sender.id).query().size != 0) {
                event.group.quoteMessage(
                    PlainText(BungeeBot.instance!!.config!!.getString("group-msg.exist")),
                    event.messageSource.messageId.toString()
                )
                return
            }
            val ipUpdateData = MapUtil.registerMap.get(message)!!
            val userData = UserDTO(ipUpdateData.player, event.sender.id, ipUpdateData.ip)
            BungeeBot.sqlData!!.createOrUpdate(userData)
            MapUtil.registerMap.remove(message)
            event.group.quoteMessage(
                PlainText(BungeeBot.instance!!.config!!.getString("group-msg.success")),
                event.messageSource.messageId.toString()
            )
        }
    }
}