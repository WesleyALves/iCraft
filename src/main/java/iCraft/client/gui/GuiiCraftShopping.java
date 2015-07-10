package iCraft.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import iCraft.core.BuyableItems;
import iCraft.core.ICraft;
import iCraft.core.inventory.container.ContaineriCraft;
import iCraft.core.network.MessageCraftBay;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftClientUtils.ResourceType;
import iCraft.core.utils.ICraftUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiiCraftShopping extends GuiContainer
{
	private int i = 0;
	private GuiButton left;
	private GuiButton right;
	private GuiButton buy;

	public GuiiCraftShopping(InventoryPlayer inventory)
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

		left = new GuiButton(0, guiWidth + 53, guiHeight + 50, 15, 20, "<");
		right = new GuiButton(1, guiWidth + 95, guiHeight + 50, 15, 20, ">");
		buy = new GuiButton(2, guiWidth + 67, guiHeight + 75, 29, 20, "Buy");

		buttonList.add(left);
		buttonList.add(right);
		buttonList.add(buy);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 148, 45, 0xffffff);
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftShopping.png"));
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);
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

		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), BuyableItems.items.get(i), guiWidth + 73, guiHeight + 51);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), BuyableItems.items.get(i), guiWidth + 73, guiHeight + 51);

		itemRender.zLevel = 0.0F;
		GL11.glDisable(GL11.GL_LIGHTING);
		if (func_146978_c(73, 51, 16, 16, mouseX, mouseY))
		{
			renderToolTip(BuyableItems.items.get(i), mouseX, mouseY);
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
			return;

		switch (guiButton.id)
		{
			case 0:
				i = (i - 1 >= 0 ? i - 1 : BuyableItems.items.size() - 1);
				break;
			case 1:
				i = (i + 1 < BuyableItems.items.size() ? i + 1 : 0);
				break;
			case 2:
				NetworkHandler.sendToServer(new MessageCraftBay(Item.getIdFromItem(BuyableItems.items.get(i).getItem()), BuyableItems.items.get(i).getItemDamage()));
				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[" + EnumChatFormatting.GOLD + "iCraft" + EnumChatFormatting.BLUE + "] " + EnumChatFormatting.GREEN + ICraftUtils.localize("msg.iCraft.iBay")));
				//mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
				mc.thePlayer.closeScreen();
				break;
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
			if (xAxis >= 143 && xAxis <= 158 && yAxis >= 51 && yAxis <= 66)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
			}
		}
	}
}