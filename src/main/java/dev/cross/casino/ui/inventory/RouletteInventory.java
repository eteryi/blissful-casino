package dev.cross.casino.ui.inventory;

import dev.cross.casino.game.RouletteGame;
import dev.cross.casino.ui.BElements;
import dev.cross.casino.ui.inventory.button.SpinButton;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.Option;
import java.util.Optional;

public class RouletteInventory extends GameInventory {
    private RouletteGame.Color colorSelected;
    private RouletteGame game;

    public RouletteInventory(Player player, int betAmount) {
        super(player, betAmount);
        this.colorSelected = RouletteGame.Color.BLACK;
        addButton(new SpinButton(this), 12);
    }

    @Override
    public boolean startGame() {
        Optional<Inventory> inventory = this.getInventory();
        inventory.ifPresent(this::start);
        return inventory.isPresent();
    }

    private static final int CHIP_POSITION = 7 + 9;

    @Override
    protected Inventory inventorySupplier() {
        LegacyComponentSerializer componentSerializer = BukkitComponentSerializer.legacy();
        Inventory inventory = Bukkit.createInventory(getPlayer(), 27, componentSerializer.serialize(BElements.ROULETTE_SCREEN));
        inventory.setItem(CHIP_POSITION, colorSelected.getItemStack());
        return inventory;
    }

    private void update(Inventory inventory) {
        inventory.setItem(CHIP_POSITION, colorSelected.getItemStack());
    }

    private void start(Inventory inventory) {
        if (game == null) {
            game = new RouletteGame(getPlayer(), getBetAmount(), this.colorSelected, inventory);
            game.start();
        }
    }

    @Override
    protected void onInteract(InventoryClickEvent event) {
        assert event.getClickedInventory() != null;
        if (event.getSlot() == CHIP_POSITION) {
            this.colorSelected = RouletteGame.Color.values()[this.colorSelected.ordinal() + 1 >= RouletteGame.Color.values().length ? 0 : this.colorSelected.ordinal() + 1];
            update(event.getClickedInventory());
        }
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }
}