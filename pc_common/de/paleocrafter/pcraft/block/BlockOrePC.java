package de.paleocrafter.pcraft.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.paleocrafter.pcraft.PaleoCraft;
import de.paleocrafter.pcraft.item.ItemToolPC;
import de.paleocrafter.pcraft.item.ModItems;
import de.paleocrafter.pcraft.lib.Reference;
import de.paleocrafter.pcraft.lib.Strings;
import de.paleocrafter.pcraft.tileentity.TileFossil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * PaleoCraft
 * 
 * BlockOrePC
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class BlockOrePC extends BlockPC {

    // Fossile vars
    @SideOnly(Side.CLIENT)
    public static Icon fosSides, fosFront;

    // Ammonite vars
    @SideOnly(Side.CLIENT)
    public static Icon ammonite;

    public BlockOrePC(int id) {
        super(id, Material.rock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setStepSound(soundStoneFootstep);
        this.setCreativeTab(PaleoCraft.tabsPC);
        this.setUnlocalizedName(Strings.ORE_NAME);
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int id,
            EntityPlayer p) {
        Random rand = new Random();

        if (!p.capabilities.isCreativeMode) {
            switch (world.getBlockMetadata(x, y, z)) {
                case 0:
                    if (p.getHeldItem() != null) {
                        if (p.getHeldItem().getItem() instanceof ItemToolPC) {
                            float dX = x + rand.nextFloat() * 0.8F + 0.1F;
                            float dY = y + rand.nextFloat() * 0.8F + 0.1F;
                            float dZ = z + rand.nextFloat() * 0.8F + 0.1F;
                            float factor = 0.05F;
                            EntityItem ent = new EntityItem(world, dX, dY, dZ,
                                    new ItemStack(ModItems.genItem, 1, 0));
                            ent.motionX = rand.nextGaussian() * factor;
                            ent.motionY = rand.nextGaussian() * factor + 0.2F;
                            ent.motionZ = rand.nextGaussian() * factor;
                            ItemToolPC heldItem = (ItemToolPC) p.getHeldItem()
                                    .getItem();
                            int perc = rand.nextInt(100);
                            if (perc <= heldItem.getPercentage())
                                world.spawnEntityInWorld(ent);

                        } else {
                            float dX = x + rand.nextFloat() * 0.8F + 0.1F;
                            float dY = y + rand.nextFloat() * 0.8F + 0.1F;
                            float dZ = z + rand.nextFloat() * 0.8F + 0.1F;
                            float factor = 0.05F;
                            EntityItem ent = new EntityItem(world, dX, dY, dZ,
                                    new ItemStack(ModBlocks.pcOre, 1, 0));
                            ent.motionX = rand.nextGaussian() * factor;
                            ent.motionY = rand.nextGaussian() * factor + 0.2F;
                            ent.motionZ = rand.nextGaussian() * factor;
                            world.spawnEntityInWorld(ent);
                        }
                    }
                    break;
                case 1:
                    float dX = x + rand.nextFloat() * 0.8F + 0.1F;
                    float dY = y + rand.nextFloat() * 0.8F + 0.1F;
                    float dZ = z + rand.nextFloat() * 0.8F + 0.1F;
                    int quant = 1 + rand.nextInt(4);
                    EntityItem ent = new EntityItem(world, dX, dY, dZ,
                            new ItemStack(ModItems.genItem, quant, 1));
                    float factor = 0.05F;
                    ent.motionX = rand.nextGaussian() * factor;
                    ent.motionY = rand.nextGaussian() * factor + 0.2F;
                    ent.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(ent);
                    break;
            }
        }
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random rand) {
        switch (meta) {
            case 0:
                return 0;
            case 1:
                return 0;
        }
        return 0;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(this, 1, 0));
        subItems.add(new ItemStack(this, 1, 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        fosFront = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
                + ":fossilFront");
        fosSides = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
                + ":fossilSide");
        ammonite = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
                + ":ammoniteBlock");
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        if (world.getBlockMetadata(x, y, z) == 0) {
            if (!world.isRemote) {
                TileFossil te = (TileFossil) world.getBlockTileEntity(x, y, z);
                if (te != null) {
                    te.init();
                }
            }
            world.markBlockForUpdate(x, y, z);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z,
            int side) {
        if (world.getBlockMetadata(x, y, z) == 0) {
            TileFossil tileEntity = (TileFossil) world.getBlockTileEntity(x, y,
                    z);
            if (tileEntity != null) {
                if (side == tileEntity.getOrientation().ordinal()) {
                    return fosFront;
                }
            }
            return fosSides;
        }
        return ammonite;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        switch (meta) {
            case 0:
                if (side == 3)
                    return fosFront;
                return fosSides;
            case 1:
                return ammonite;
        }
        return fosSides;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        if (meta == 0)
            return new TileFossil();
        return null;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        if (meta == 0)
            return true;
        return false;
    }
}
