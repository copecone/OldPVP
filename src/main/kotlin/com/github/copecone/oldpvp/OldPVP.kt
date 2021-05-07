package com.github.copecone.oldpvp

import com.github.copecone.oldpvp.events.MCEventListener
import com.github.copecone.oldpvp.groups.SwordGroup
import org.bukkit.ChatColor
import org.bukkit.Server
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
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
        lateinit var publicServer: Server
        var tickTime by Delegates.notNull<Int>()
        lateinit var blockHitters: ArrayList<Player>
        lateinit var managedPlayers: ArrayList<Player>
        lateinit var checked: ArrayList<Boolean>
        lateinit var breakScore: ArrayList<Double>
    }

    override fun onEnable() {
        publicLogger = logger
        publicServer = server
        logger.info(ChatColor.RED.toString() + ChatColor.BOLD + "OldPVP" + ChatColor.GREEN + " is enabled!")

        if (!dataFolder.exists()) {
            dataFolder.mkdir()
        }

        if (!File(dataFolder.path + "/config.yml").exists()) {
            saveDefaultConfig()
        }

        managedPlayers = ArrayList()
        blockHitters = ArrayList()
        breakScore = ArrayList()
        checked = ArrayList()

        tickTime = (config.getDouble("tickTime") * 20).toInt()

        server.apply {
            pluginManager.apply {
                registerEvents(MCEventListener, this@OldPVP)
            }

            scheduler.apply {
                scheduleSyncRepeatingTask(this@OldPVP, BlockHit, 1, 1)
            }
        }
    }

    override fun onDisable() {
        logger.info(ChatColor.RED.toString() + ChatColor.BOLD + "OldPVP" + ChatColor.GREEN + " is disabled!")
    }

}