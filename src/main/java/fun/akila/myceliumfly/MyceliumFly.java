package fun.akila.myceliumfly;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
public final class MyceliumFly extends JavaPlugin implements Listener {
    private GriefPrevention gp;
    private LuckPerms luckPerms;
    @Override
    public void onEnable() {
        this.luckPerms = LuckPermsProvider.get();
        Plugin plugin = getServer().getPluginManager().getPlugin("GriefPrevention");
        if (plugin == null || !(plugin instanceof GriefPrevention)) {
            getLogger().severe("GriefPrevention not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        gp = (GriefPrevention) plugin;
        getLogger().info("MyceliumFly has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MyceliumFly has been disabled!");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        checkClaim(player);
    }

    public void checkClaim(Player player) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        boolean hasPermission = user != null && user.getCachedData().getPermissionData().checkPermission("mycelium.fly").asBoolean();

        if (hasPermission && gp.dataStore.getClaimAt(player.getLocation(), true, null) != null) {
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

}
