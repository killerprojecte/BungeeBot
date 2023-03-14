package org.rgbmc.bungeebot.database

import com.j256.ormlite.jdbc.DataSourceConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.rgbmc.bungeebot.BungeeBot.Companion.instance
import java.sql.SQLException

class MySQLData : SQLData() {
    @Throws(SQLException::class)
    override fun getConnectionSource(): ConnectionSource {
        var minimumIdle: Int
        val config = HikariConfig()
        config.poolName = "bungee-bot-mysql"
        val ssl = instance!!.config!!.getBoolean("mysql.ssl")
        val url = "jdbc:mysql://" + instance!!.config!!.getString("mysql.host") + "/" +
                instance!!.config!!.getString("mysql.database") + "?useSSL=" + ssl + "&serverTimezone=UTC&autoReconnect=true&allowPublicKeyRetrieval=true&characterEncoding=utf8"
        config.jdbcUrl = url
        val user = instance!!.config!!.getString("mysql.user")
        val password = instance!!.config!!.getString("mysql.password")
        if (user != null) {
            config.username = user
        }
        if (password != null) {
            config.password = password
        }
        config.minimumIdle = if (instance!!.config!!.getInt("mysql.minimum-idle")
                .also { minimumIdle = it } > 0
        ) minimumIdle else 4
        val maximumPoolSize = instance!!.config!!.getInt("mysql.maximum-pool-size")
        config.maximumPoolSize = if (maximumPoolSize > 0) maximumPoolSize else 16
        val maxLifetime = instance!!.config!!.getLong("mysql.max-lifetime")
        config.maxLifetime = if (maxLifetime > 0L) maxLifetime else 600000L
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        val dataSource = HikariDataSource(config)
        return HikariConnectionSource(dataSource, url)
    }

    private inner class HikariConnectionSource(private val hikariDataSource: HikariDataSource, url: String?) :
        DataSourceConnectionSource(hikariDataSource, url) {
        @Throws(Exception::class)
        override fun close() {
            super.close()
            hikariDataSource.close()
        }
    }
}