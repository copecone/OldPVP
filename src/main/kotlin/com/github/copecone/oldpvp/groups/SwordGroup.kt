package com.github.copecone.oldpvp.groups

import org.bukkit.Material

object SwordGroup: Group {

    override var list: ArrayList<Material> = ArrayList()

    init {
        list.add(Material.WOODEN_SWORD)
        list.add(Material.STONE_SWORD)
        list.add(Material.IRON_SWORD)
        list.add(Material.GOLDEN_SWORD)
        list.add(Material.DIAMOND_SWORD)
        list.add(Material.NETHERITE_SWORD)
    }

}