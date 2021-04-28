package com.github.copecone.oldpvp.events

import com.github.copecone.oldpvp.OldPVP
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent


object MCEventListener : Listener {

    @EventHandler
    fun onHit(e: EntityDamageByEntityEvent) {
        if (e.entity is Player && e.damager is Player) {
            (e.entity as Player).noDamageTicks = OldPVP.tickTime
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerUse(event: PlayerInteractEvent) {
        val isPlayerWalking = !(
                event.player.isFlying ||
                        event.player.isSneaking ||
                        event.player.isSprinting ||
                        event.player.isSwimming ||
                        event.player.isSleeping
                )

        if (event.player.itemInHand.type == Material.DIAMOND_SWORD) {
            if (isPlayerWalking) {
                event.player.walkSpeed = 0.04326923076923077F
            } else if (event.player.isSprinting) {
                event.player.walkSpeed = 0.03208556149732621F
            }
        }
    }

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        val instance = event.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)
        if (instance != null) {
            instance.baseValue = 65536.0
        }
    }

}