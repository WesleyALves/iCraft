package iCraft.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.network.MessageDelivery;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;
import iCraft.core.utils.ICraftUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiiCraftDelivery extends GuiContainer
{
	private int qnt = 1;
	private GuiButton qntMore;
	private GuiButton qntLess;
	private GuiButton buy;

	public GuiiCraftDelivery(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();

		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;

		qntMore = new GuiButton(0, guiWidth + 60, guiHeight + 80, 15, 20, "\u25B2");
		qntLess = new GuiButton(1, guiWidth + 60, guiHeight + 100, 15, 20, "\u25BC");
		buy = new GuiButton(2, guiWidth + 85, guiHeight + 90, 30, 20, "Buy");

		buttonList.add(qntMore);
		buttonList.add(qntLess);
		buttonList.add(buy);
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
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftDelivery.png"));
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
			return;

		switch (guiButton.id)
		{
			case 0:
				qnt = (qnt + 1 <= 32 ? qnt + 1 : qnt);
				break;
			case 1:
				qnt = (qnt - 1 >= 1 ? qnt - 1 : qnt);
				break;
			case 2:
				NetworkHandler.sendToServer(new MessageDelivery(qnt));
				mc.thePlayer.closeScreen();
				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[" + EnumChatFormatting.GOLD + "iCraft" + EnumChatFormatting.BLUE + "] " + ICraftUtils.localize("msg.iCraft.delivery")));
				break;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		super.drawScreen(mouseX, mouseY, partialTick);

		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		itemRender.zLevel = 100.0F;

		ItemStack iron = new ItemStack(Items.iron_ingot, (qnt * 2));
		ItemStack pizza = new ItemStack(ICraft.pizza, qnt);

		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), iron, guiWidth + 53, guiHeight + 45);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), iron, guiWidth + 53, guiHeight + 45);

		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), pizza, guiWidth + 107, guiHeight + 45);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), pizza, guiWidth + 107, guiHeight + 45);

		itemRender.zLevel = 0.0F;
		GL11.glDisable(GL11.GL_LIGHTING);
		if (func_146978_c(53, 45, 16, 16, mouseX, mouseY))
		{
			renderToolTip(iron, mouseX, mouseY);
		}
		else if (func_146978_c(107, 45, 16, 16, mouseX, mouseY))
		{
			renderToolTip(pizza, mouseX, mouseY);
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected void mouseClicked(int x, int y, int button)
	{
		super.mouseClicked(x, y, button);

		if (button == 0)
		{
			int xAxis = (x - (width - xSize) / 2);
			int yAxis = (y - (height - ySize) / 2);
			// Exit
			if(xAxis >= 80 && xAxis <= 95 && yAxis >= 143 && yAxis <= 158)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
			}
		}
	}
}