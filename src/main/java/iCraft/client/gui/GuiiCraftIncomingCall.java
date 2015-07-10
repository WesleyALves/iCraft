package iCraft.client.gui;

import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.network.MessageReceivedCall;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class GuiiCraftIncomingCall extends GuiContainer
{
	public GuiiCraftIncomingCall(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 164, 58, 0xffffff);
		if (isCalling())
		{
			fontRendererObj.drawString("Calling to " + ICraftClientUtils.getPlayerNumber(true), 118, 218, 0xffffff);
		}
		else
		{
			fontRendererObj.drawString(ICraftClientUtils.getPlayerNumber(false) + " is calling you", 118, 218, 0xffffff);
		}
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		if (isCalling())
		{
			mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftInCall.png"));
		}
		else
		{
			mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftIncomingCall.png"));
		}
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);
		if (isCalling())
		{
			if (ICraftClientUtils.getClientPlayer(mc.theWorld, true) != null)
				GuiInventory.func_147046_a(guiWidth + 88, guiHeight + 101, 26, 0.0F, 20.0F, ICraftClientUtils.getClientPlayer(mc.theWorld, true));
		}
		else
		{
			if (ICraftClientUtils.getClientPlayer(mc.theWorld, false) != null)
			GuiInventory.func_147046_a(guiWidth + 88, guiHeight + 101, 26, 0.0F, 20.0F, ICraftClientUtils.getClientPlayer(mc.theWorld, false));
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
			if (isCalling())
			{
				if(xAxis >= 80 && xAxis <= 95 && yAxis >= 143 && yAxis <= 158)
				{
					mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
					NetworkHandler.sendToServer(new MessageReceivedCall(0, true));//TODO
				}
				//Cancel
				if(xAxis >= 72 && xAxis <= 103 && yAxis >= 117 && yAxis <= 127)
				{
					mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
					NetworkHandler.sendToServer(new MessageReceivedCall(0, true));//TODO
				}
			}
			else
			{
				// Exit
				if (xAxis >= 80 && xAxis <= 95 && yAxis >= 143 && yAxis <= 158)
				{
					mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
					NetworkHandler.sendToServer(new MessageReceivedCall(0, false));// TODO
				}
				// Accept --> WIP
				if (xAxis >= 53 && xAxis <= 84 && yAxis >= 121 && yAxis <= 131)
				{
					mc.thePlayer.openGui(ICraft.instance, 7, mc.theWorld, 0, 0, 0);
					NetworkHandler.sendToServer(new MessageReceivedCall(7, false));// TODO
				}
				// Deny -- WIP
				if (xAxis >= 90 && xAxis <= 122 && yAxis >= 121 && yAxis <= 131)
				{
					mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
					NetworkHandler.sendToServer(new MessageReceivedCall(0, false));// TODO
				}
			}
		}
	}

	private boolean isCalling()
	{
		ItemStack itemStack = mc.thePlayer.getCurrentEquippedItem();
		if (itemStack != null && itemStack.stackTagCompound.hasKey("isCalling") && itemStack.stackTagCompound.getBoolean("isCalling") == true)
		{
			return true;
		}

		return false;
	}
}