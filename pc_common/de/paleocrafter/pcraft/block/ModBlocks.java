package de.paleocrafter.pcraft.block;

import cpw.mods.fml.common.registry.GameRegistry;
import de.paleocrafter.pcraft.item.ItemBlockMachines;
import de.paleocrafter.pcraft.item.ItemBlockOre;
import de.paleocrafter.pcraft.lib.BlockIds;
import de.paleocrafter.pcraft.lib.Strings;
import net.minecraft.block.Block;

/**
 * PaleoCraft
 * 
 * ModBlocks
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ModBlocks {
    /* Mod block instances */
    public static Block pcOre;
    public static Block egg;
    public static Block machines;

    public static void init() {
        pcOre = new BlockOrePC(BlockIds.ORE);
        egg = new BlockDinoEgg(BlockIds.EGG);
        machines = new BlockMachines(BlockIds.MACHINES);

        GameRegistry.registerBlock(egg, Strings.EGG_NAME);
        GameRegistry.registerBlock(pcOre, ItemBlockOre.class, Strings.ORE_NAME);
        GameRegistry.registerBlock(machines, ItemBlockMachines.class,
                Strings.MACHINES_NAME);
    }
}
