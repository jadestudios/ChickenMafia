package uk.jadestudios.purpur.groupedchicken;

import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;

public final class GroupedChicken extends JavaPlugin implements @NotNull Listener {

    private int range;

    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage("[GroupedChicken] Starting up!");
        this.saveDefaultConfig();
        this.range = this.getConfig().getInt("range");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getConsoleSender().sendMessage("[GroupedChicken] Range set to: " +this.range+" blocks");
    }

    @Override
    public @NotNull Logger getSLF4JLogger() {
        return super.getSLF4JLogger();
    }

    @EventHandler
    public void onHit(PlayerAttackEntityCooldownResetEvent event) {
        if (event.getAttackedEntity().getType() != EntityType.CHICKEN) return; //Short Circuit on non-chicken attacks
        Chicken chicken = (Chicken) event.getAttackedEntity();
        List<Entity> thingsNearChicken = chicken.getNearbyEntities(this.range * 2,this.range * 2,this.range * 2);

        thingsNearChicken.forEach((Entity things) -> {
            if (things.getType() == EntityType.CHICKEN) {
                Chicken nearbyChicken = (Chicken) things;
                nearbyChicken.setTarget(event.getPlayer());
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
