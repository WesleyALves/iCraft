package iCraft.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import iCraft.core.ICraft;
import iCraft.core.network.MessageCraftBay;
import iCraft.core.network.NetworkHandler;
import iCraft.core.utils.ICraftClientUtils;
import iCraft.core.utils.ICraftUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiiCraftShopping extends GuiiCraftBase
{
	private int i = 0;
	private GuiButton left;
	private GuiButton right;
	private GuiButton buy;

	public GuiiCraftShopping(String resource)
	{
		super(resource);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(false);
		buttonList.clear();

		left = new GuiButton(0, guiWidth + 53, guiHeight + 50, 15, 20, "<");
		right = new GuiButton(1, guiWidth + 95, guiHeight + 50, 15, 20, ">");
		buy = new GuiButton(2, guiWidth + 67, guiHeight + 75, 29, 20, "Buy");

		buttonList.add(left);
		buttonList.add(right);
		buttonList.add(buy);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		super.drawScreen(mouseX, mouseY, partialTick);

		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		itemRender.zLevel = 100.0F;

		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ICraftUtils.items.get(i), guiWidth + 73, guiHeight + 51);
		itemRender.renderItemOverlayIntoGUI(fontRendererObj, mc.getTextureManager(), ICraftUtils.items.get(i), guiWidth + 73, guiHeight + 51);

		itemRender.zLevel = 0.0F;
		GL11.glDisable(GL11.GL_LIGHTING);
		if (isMouseOver(73, 51, 16, 16, mouseX, mouseY))
		{
			renderToolTip(ICraftUtils.items.get(i), mouseX, mouseY);
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();

		drawString(ICraftClientUtils.getTime(), 148, 45, 0xffffff, true, 0.5F);
	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (!guiButton.enabled)
			return;

		switch (guiButton.id)
		{
			case 0:
				i = (i - 1 >= 0 ? i - 1 : ICraftUtils.items.size() - 1);
				break;
			case 1:
				i = (i + 1 < ICraftUtils.items.size() ? i + 1 : 0);
				break;
			case 2:
				NetworkHandler.sendToServer(new MessageCraftBay(Item.getIdFromItem(ICraftUtils.items.get(i).getItem()), ICraftUtils.items.get(i).getItemDamage()));
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
			int xAxis = x - guiWidth;
			int yAxis = y - guiHeight;
			//Exit
			if (xAxis >= 143 && xAxis <= 158 && yAxis >= 51 && yAxis <= 66)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
			}
		}
	}
}