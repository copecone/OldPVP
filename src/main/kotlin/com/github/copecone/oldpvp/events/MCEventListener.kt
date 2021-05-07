package com.github.copecone.oldpvp.events

import com.github.copecone.oldpvp.OldPVP
import com.github.copecone.oldpvp.groups.SwordGroup
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


object MCEventListener : Listener {

    @EventHandler
    fun onHit(e: EntityDamageByEntityEvent) {
        if (e.entity is Player && e.damager is Player) {
            var realDamage = e.damage
            if (OldPVP.checked[OldPVP.managedPlayers.indexOf(e.entity as Player)]) {
                realDamage *= 0.5
            }

            (e.entity as Player).damage(realDamage)
            (e.entity as Player).velocity = (e.entity as Player).velocity.add(e.entity.location.toVector().subtract(e.damager.location.toVector()).normalize().multiply(1))
            (e.entity as Player).noDamageTicks = OldPVP.tickTime
            e.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerUse(event: PlayerInteractEvent) {
        val isPlayerWalking = !(
                event.player.isFlying ||
                        event.player.isSneaking ||
                        event.player.isSprinting ||
                        event.player.isSwimming ||
                        event.player.isSleeping
                )

        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            if (SwordGroup.list.contains(event.player.itemInHand.type)) {
                OldPVP.checked[OldPVP.managedPlayers.indexOf(event.player)] = true
                if (!OldPVP.blockHitters.contains(event.player)) {
                    OldPVP.blockHitters.add(event.player)
                }

                if (isPlayerWalking) {
                    event.player.walkSpeed = 0.04326923076923077F
                } else if (event.player.isSprinting) {
                    event.player.walkSpeed = 0.03208556149732621F
                }
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val instance = event.player.getAttribute(Attribute.GENERIC_ATTACK_SPEED)
        if (instance != null) {
            instance.baseValue = 65536.0
        }

        OldPVP.managedPlayers.add(event.player)
        OldPVP.checked.add(false)
        OldPVP.breakScore.add(0.0)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val playerIndex = OldPVP.managedPlayers.indexOf(event.player)
        OldPVP.managedPlayers.removeAt(playerIndex)
        OldPVP.checked.removeAt(playerIndex)
        OldPVP.breakScore.removeAt(playerIndex)
    }

}