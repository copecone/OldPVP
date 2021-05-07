package com.github.copecone.oldpvp

import org.bukkit.entity.Player

object BlockHit: Runnable {

    override fun run() {
        for (player in OldPVP.publicServer.onlinePlayers) {
            val playerIndex = OldPVP.managedPlayers.indexOf(player)
            if (playerIndex != -1) {
                if (OldPVP.checked[playerIndex] && !OldPVP.blockHitters.contains(player)) {
                    if (OldPVP.breakScore[playerIndex] > 0) {OldPVP.breakScore[playerIndex] -= 0.16666666666666666666666666666667}
                    else if (OldPVP.breakScore[playerIndex] <= 0) {
                        OldPVP.checked[playerIndex] = false
                        player.walkSpeed = 0.2F
                    }
                } else {
                    OldPVP.breakScore[playerIndex] = 1.0
                }
            }

            val removableTarget = ArrayList<Player>()
            for (blockHitter in OldPVP.blockHitters) {
                removableTarget.add(blockHitter)
            }

            for (removePlayer in removableTarget) {
                OldPVP.blockHitters.remove(removePlayer)
            }
        }
    }

}