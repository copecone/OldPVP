package com.github.copecone.oldpvp

import com.github.copecone.oldpvp.events.MCEventListener
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger
import kotlin.properties.Delegates

/**
 * @author copecone
 */
class OldPVP : JavaPlugin() {

    companion object {
        lateinit var publicLogger: Logger
        var tickTime by Delegates.notNull<Int>()
    }

    override fun onEnable() {
        publicLogger = logger
        logger.info(ChatColor.RED.toString() + ChatColor.BOLD + "OldPVP" + ChatColor.GREEN + " is enabled!")

        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }

        if (!File(dataFolder.path + "/config.yml").exists()) {
            saveDefaultConfig()
        }

        tickTime = (config.getDouble("tickTime") * 20).toInt()

        server.apply {
            pluginManager.apply {
                registerEvents(MCEventListener, this@OldPVP)
            }
        }
    }

    override fun onDisable() {
        logger.info(ChatColor.RED.toString() + ChatColor.BOLD + "OldPVP" + ChatColor.GREEN + " is disabled!")
    }

}