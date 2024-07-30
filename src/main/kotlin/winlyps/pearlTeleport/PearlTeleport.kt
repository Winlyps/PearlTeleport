//1.File: PearlTeleport.kt
package winlyps.pearlTeleport

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldBorder
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.java.JavaPlugin

class PearlTeleport : JavaPlugin(), Listener {

    override fun onEnable() {
        // Register the event listener
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    fun onPlayerTeleport(event: PlayerTeleportEvent) {
        if (event.cause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            val toLocation: Location = event.to ?: return
            val world: World = toLocation.world ?: return
            val worldBorder = world.worldBorder

            if (!isWithinWorldBorder(toLocation, worldBorder)) {
                event.isCancelled = true
            }
        }
    }

    private fun isWithinWorldBorder(location: Location, worldBorder: WorldBorder): Boolean {
        val borderCenter = worldBorder.center
        val borderSize = worldBorder.size / 2
        val buffer = 1.0 // One block buffer

        val x = location.x
        val z = location.z

        val centerX = borderCenter.x
        val centerZ = borderCenter.z

        return x >= centerX - borderSize + buffer && x <= centerX + borderSize - buffer &&
                z >= centerZ - borderSize + buffer && z <= centerZ + borderSize - buffer
    }
}