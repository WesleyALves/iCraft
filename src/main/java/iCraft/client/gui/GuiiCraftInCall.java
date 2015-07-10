package iCraft.client.gui;

import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.network.MessageReceivedCall;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiiCraftInCall extends GuiContainer
{
	public GuiiCraftInCall(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 164, 58, 0xffffff);
		fontRendererObj.drawString("Talking with " + (isCalling() ? ICraftClientUtils.getPlayerNumber(true) : ICraftClientUtils.getPlayerNumber(false)), 118, 218, 0xffffff);
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftInCall.png"));
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
			//Exit
			if (xAxis >= 51 && xAxis <= 67 && yAxis >= 38 && yAxis <= 54)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
				NetworkHandler.sendToServer(new MessageReceivedCall(0, isCalling()));
			}
			//End Call
			if (xAxis >= 72 && xAxis <= 103 && yAxis >= 117 && yAxis <= 127)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
				NetworkHandler.sendToServer(new MessageReceivedCall(0, isCalling()));
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