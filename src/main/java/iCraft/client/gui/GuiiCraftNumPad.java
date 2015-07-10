package iCraft.client.gui;

import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.network.MessageIncomeCalling;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiiCraftNumPad extends GuiContainer
{
	public static String callNumber = "";
	
	public GuiiCraftNumPad(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 164, 58, 0xffffff);
		fontRendererObj.drawString(callNumber, 154, 74, 0x404040);
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftNumPad.png"));
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);

		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;
	}

	@Override
	protected void mouseClicked(int x, int y, int button)
	{
		super.mouseClicked(x, y, button);

		if(button == 0)
		{
			int xAxis = (x - (width - xSize) / 2);
			int yAxis = (y - (height - ySize) / 2);
			//Exit
			if(xAxis >= 80 && xAxis <= 95 && yAxis >= 143 && yAxis <= 158)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
				callNumber = "";
			}
			if (callNumber.length() < 8)
			{
				//0
				if(xAxis >= 79 && xAxis <= 95 && yAxis >= 104 && yAxis <= 120)
				{
					callNumber += "0";
				}
				//1
				if(xAxis >= 61 && xAxis <= 77 && yAxis >= 86 && yAxis <= 102)
				{
					callNumber += "1";
				}
				//2
				if(xAxis >= 79 && xAxis <= 95 && yAxis >= 86 && yAxis <= 102)
				{
					callNumber += "2";
				}
				//3
				if(xAxis >= 98 && xAxis <= 114 && yAxis >= 86 && yAxis <= 102)
				{
					callNumber += "3";
				}
				//4
				if(xAxis >= 61 && xAxis <= 77 && yAxis >= 68 && yAxis <= 84)
				{
					callNumber += "4";
				}
				//5
				if(xAxis >= 79 && xAxis <= 95 && yAxis >= 68 && yAxis <= 84)
				{
					callNumber += "5";
				}
				//6
				if(xAxis >= 98 && xAxis <= 114 && yAxis >= 68 && yAxis <= 84)
				{
					callNumber += "6";
				}
				//7
				if(xAxis >= 61 && xAxis <= 77 && yAxis >= 50 && yAxis <= 66)
				{
					callNumber += "7";
				}
				//8
				if(xAxis >= 79 && xAxis <= 95 && yAxis >= 50 && yAxis <= 66)
				{
					callNumber += "8";
				}
				//9
				if(xAxis >= 98 && xAxis <= 114 && yAxis >= 50 && yAxis <= 66)
				{
					callNumber += "9";
				}
			}
			//Clear
			if(xAxis >= 89 && xAxis <= 114 && yAxis >= 124 && yAxis <= 134)
			{
				if(callNumber.length() != 0)
				{
					callNumber = callNumber.substring(0, callNumber.length() - 1);
				}
			}
			//Call
			if(xAxis >= 61 && xAxis <= 85 && yAxis >= 124 && yAxis <= 134)
			{
				if(callNumber.length() == 8)
				{
					NetworkHandler.sendToServer(new MessageIncomeCalling(mc.thePlayer.getCurrentEquippedItem().stackTagCompound.getInteger("number") , Integer.parseInt(callNumber)));
				}
			}
		}
	}
}