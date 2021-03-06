package de.paleocrafter.pcraft.client.gui.inventory;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.paleocrafter.pcraft.inventory.ContainerAnalyzer;
import de.paleocrafter.pcraft.lib.Strings;
import de.paleocrafter.pcraft.lib.Textures;
import de.paleocrafter.pcraft.tileentity.TileAnalyzer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

/**
 * PaleoCraft
 * 
 * GuiAnalyzer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@SideOnly(Side.CLIENT)
public class GuiAnalyzer extends GuiContainer {
    private TileAnalyzer tileAnalyzer;
    private boolean showFuelTip;

    public GuiAnalyzer(InventoryPlayer player, TileAnalyzer tileAnalyzer) {
        super(new ContainerAnalyzer(player, tileAnalyzer));
        ySize = 216;
        this.tileAnalyzer = tileAnalyzer;
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height
                / this.mc.displayHeight - 1;
        int xStart = width / 2 - xSize / 2;
        int yStart = height / 2 - ySize / 2;

        int xPos = xStart + 12;
        int yPos = yStart + 41;
        int objWidth = 14;
        int objHeight = 50;

        if (x >= xPos && x <= (xPos + objWidth) && y >= yPos
                && y <= (yPos + objHeight)) {
            showFuelTip = true;
        } else {
            showFuelTip = false;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void drawScreen(int x, int y, float par3) {
        super.drawScreen(x, y, par3);
        if (showFuelTip) {
            ArrayList text = new ArrayList();
            text.add(StatCollector.translateToLocal(Strings.FOSSINERGY_NAME)
                    + ":");
            text.add(tileAnalyzer.getFuelLevel() + "/100");
            this.drawHoveringText(text, x, y, fontRenderer);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String containerName = tileAnalyzer.isInvNameLocalized() ? tileAnalyzer
                .getInvName() : StatCollector.translateToLocal(tileAnalyzer
                .getInvName());
        fontRenderer.drawString(containerName,
                xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6,
                4210752);
        fontRenderer.drawString(
                StatCollector.translateToLocal(Strings.INV_NAME), 8,
                ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.GUI_ANALYZER);
        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        int fuelLevel = (int) Math.floor(tileAnalyzer.getFuelLevel() / 2);
        this.drawTexturedModalRect(xStart + 12, yStart + 41 + 50 - fuelLevel,
                176, 50 - fuelLevel, 14, fuelLevel);

        int progress = (int) Math.floor(tileAnalyzer.getProgress() / 2);
        this.drawTexturedModalRect(xStart + 49, yStart + 37, 190, 0, 14,
                progress);
    }
}
