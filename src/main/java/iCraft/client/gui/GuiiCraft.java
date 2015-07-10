package iCraft.client.gui;

import iCraft.core.BuyableItems;
import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ChatComponentText;

@SideOnly(Side.CLIENT)
public class GuiiCraft extends GuiContainer
{
	public GuiiCraft(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 164, 58, 0xffffff);
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraft.png"));
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);

		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;

		if (xAxis >= 51 && xAxis <= 67 && yAxis >= 38 && yAxis <= 54)
		{
			drawTexturedModalRect(guiWidth + 51, guiHeight + 38, 176, 38, 17, 17);
		}
		if (xAxis >= 70 && xAxis <= 86 && yAxis >= 38 && yAxis <= 54)
		{
			drawTexturedModalRect(guiWidth + 70, guiHeight + 38, 176, 55, 17, 17);
		}
		if (xAxis >= 89 && xAxis <= 105 && yAxis >= 38 && yAxis <= 54)
		{
			drawTexturedModalRect(guiWidth + 89, guiHeight + 38, 176, 72, 17, 17);
		}
		if (xAxis >= 108 && xAxis <= 124 && yAxis >= 38 && yAxis <= 54)
		{
			drawTexturedModalRect(guiWidth + 108, guiHeight + 38, 176, 89, 17, 17);
		}
		if (xAxis >= 51 && xAxis <= 67 && yAxis >= 58 && yAxis <= 74)
		{
			drawTexturedModalRect(guiWidth + 51, guiHeight + 58, 193, 38, 17, 17);
		}
		if (xAxis >= 70 && xAxis <= 86 && yAxis >= 58 && yAxis <= 74)
		{
			drawTexturedModalRect(guiWidth + 70, guiHeight + 58, 193, 55, 17, 17);
		}
		if (xAxis >= 89 && xAxis <= 105 && yAxis >= 58 && yAxis <= 74)
		{
			drawTexturedModalRect(guiWidth + 89, guiHeight + 58, 193, 72, 17, 17);
		}
		if (xAxis >= 108 && xAxis <= 124 && yAxis >= 58 && yAxis <= 74)
		{
			drawTexturedModalRect(guiWidth + 108, guiHeight + 58, 193, 89, 17, 17);
		}
		if (xAxis >= 51 && xAxis <= 67 && yAxis >= 78 && yAxis <= 94)
		{
			drawTexturedModalRect(guiWidth + 51, guiHeight + 78, 210, 38, 17, 17);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button)
	{
		super.mouseClicked(x, y, button);

		if (button == 0)
		{
			int xAxis = (x - (width - xSize) / 2);
			int yAxis = (y - (height - ySize) / 2);
			// Calc
			if (xAxis >= 51 && xAxis <= 67 && yAxis >= 38 && yAxis <= 54)
			{
				mc.thePlayer.openGui(ICraft.instance, 1, mc.theWorld, 0, 0, 0);
			}
			// GPS
			if (xAxis >= 89 && xAxis <= 105 && yAxis >= 38 && yAxis <= 54)
			{
				mc.thePlayer.openGui(ICraft.instance, 2, mc.theWorld, 0, 0, 0);
			}
			// Clock
			if (xAxis >= 89 && xAxis <= 105 && yAxis >= 38 && yAxis <= 54)
			{
				mc.thePlayer.openGui(ICraft.instance, 3, mc.theWorld, 0, 0, 0);
			}
			/**
			 * Settings --> WIP
			 */
			// NumPad
			if (xAxis >= 51 && xAxis <= 67 && yAxis >= 58 && yAxis <= 74)
			{
				mc.thePlayer.openGui(ICraft.instance, 5, mc.theWorld, 0, 0, 0);
			}
			// SMS --> WIP
			if (xAxis >= 70 && xAxis <= 86 && yAxis >= 58 && yAxis <= 74)
			{
				//mc.thePlayer.openGui(EnergyCraft.instance, 8, mc.theWorld, 0, 0, 0);
			}
			// Online Buy --> WIP
			if (xAxis >= 89 && xAxis <= 105 && yAxis >= 58 && yAxis <= 74)
			{
				if (!BuyableItems.items.isEmpty())
				{
					mc.thePlayer.openGui(ICraft.instance, 9, mc.theWorld, 0, 0, 0);
					mc.thePlayer.addChatComponentMessage(new ChatComponentText("Please understand that CraftBay is under construction. Thank You :)"));
				}
			}
			if (xAxis >= 108 && xAxis <= 124 && yAxis >= 58 && yAxis <= 74)
			{
				mc.thePlayer.openGui(ICraft.instance, 10, mc.theWorld, 0, 0, 0);
			}
			if (xAxis >= 51 && xAxis <= 67 && yAxis >= 78 && yAxis <= 94)
			{
				mc.thePlayer.openGui(ICraft.instance, 12, mc.theWorld, 0, 0, 0);
			}
		}
	}
}