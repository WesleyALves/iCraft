package iCraft.client.gui;

import java.io.FileInputStream;
import java.util.Random;

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
import net.minecraft.util.EnumChatFormatting;

@SideOnly(Side.CLIENT)
public class GuiiCraftMP3Player extends GuiContainer
{
	private float scroll;
	private boolean isDragging = false;
	private int dragOffset = 0;
	private int displayInt;
	private FileInputStream input;
	private Random rnd = new Random();

	public GuiiCraftMP3Player(InventoryPlayer inventory)
	{
		super(new ContaineriCraft(inventory));
	}

	public int getScroll()
	{
		return Math.max(Math.min((int)(scroll * 53), 53), 0);
	}

	public int getIndex()
	{
		if(ICraft.musics.size() <= 5)
		{
			return 0;
		}

		return (int)((ICraft.musics.size() * scroll) - ((5 / (float)ICraft.musics.size())) * scroll);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		fontRendererObj.drawString(ICraftClientUtils.getTime(), 164, 58, 0xffffff);
		if (ICraft.mp3Player != null && ICraft.mp3Player.hasPlayer())
			fontRendererObj.drawString(ICraft.mp3Player.getPosition() + "/" + ICraft.mp3Player.getMinDuration(), 186, 84, 0xffffff);
		//GL11.glPopMatrix();

		for (int i = 0; i < 5; i++)
		{
			if (getIndex() + i < ICraft.musics.size())
			{
				int yStart = i * 28 + 49;

				fontRendererObj.drawString((ICraft.musicNames.get(getIndex() + i).length() > 13 ? ICraft.musicNames.get(getIndex() + i).substring(0, 13) : ICraft.musicNames.get(getIndex() + i)), 110, yStart + 55, 0xffffff);
				try {
					fontRendererObj.drawString(ICraftClientUtils.getAuthor(ICraft.musics.get(getIndex() + i)), 110, yStart + 65, 0xffffff);
				} catch (Exception e) {}
			}
		}
		GL11.glPopMatrix();

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY)
	{
		mc.renderEngine.bindTexture(ICraftClientUtils.getResource(ResourceType.GUI, "GuiiCraftMP3Player.png"));
		int guiWidth = (width - xSize) / 2;
		int guiHeight = (height - ySize) / 2;
		drawTexturedModalRect(guiWidth, guiHeight, 0, 0, xSize, ySize);

		drawTexturedModalRect(guiWidth + 113, guiHeight + 50 + getScroll(), 0, 180, 12, 15);

		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		for (int i = 0; i < 5; i++)
		{
			if (getIndex() + i < ICraft.musics.size())
			{
				int yStart = i * 14 + 49;
				int rnd1 = rnd.nextInt(8), rnd2 = rnd.nextInt(8), rnd3 = rnd.nextInt(8);
				boolean mouseOver = xAxis >= 51 && xAxis <= 111 && yAxis >= yStart && yAxis <= yStart + 14;

				drawTexturedModalRect(guiWidth + 51, guiHeight + yStart, mouseOver ? 0 : 61, 166, 61, 14);

				if (ICraft.mp3Player != null && ICraft.mp3Player.hasPlayer() && ICraft.mp3Player.getPlayerStatus() != 0 && ICraft.mp3Player.getPlayerStatus() != 3 && ICraft.musics.get(getIndex() + i) == ICraft.musics.get(ICraft.currentMusicId))
				{
					drawTexturedModalRect(guiWidth + 101, guiHeight + yStart + 11 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd1 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 5 : 0)), 24, 188 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd1 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 5 : 0)), 2, (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd1 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 5 : 0)));
					drawTexturedModalRect(guiWidth + 104, guiHeight + yStart + 11 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd2 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 8 : 0)), 24, 188 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd2 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 8 : 0)), 2, (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd2 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 8 : 0)));
					drawTexturedModalRect(guiWidth + 107, guiHeight + yStart + 11 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd3 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 2 : 0)), 24, 188 - (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd3 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 2 : 0)), 2, (ICraft.mp3Player.getPlayerStatus() == 1 ? rnd3 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 2 : 0)));
				}
			}
		}

		if (ICraft.mp3Player != null && ICraft.mp3Player.hasPlayer())
		{
			drawTexturedModalRect(guiWidth + 51, guiHeight + 119, 182, (ICraft.mp3Player.getPlayerStatus() == 1 ? 58 : (ICraft.mp3Player.getPlayerStatus() == 2 ? 39 : (ICraft.mp3Player.getPlayerStatus() == 3 ? 39 : 0))), 74, 19);
			drawTexturedModalRect(guiWidth + 51, guiHeight + 34, 182, 91, 74, 15);
			drawTexturedModalRect(guiWidth + 53, guiHeight + 41, 184, (ICraft.mp3Player.getRepeatType() == 0 ? 107 : (ICraft.mp3Player.getRepeatType() == 1 ? 116 : (ICraft.mp3Player.getRepeatType() == 2 ? 125 : 0))), 7, 7);

			try {
				displayInt = ICraft.mp3Player.getMusicStatus(68);
				drawTexturedModalRect(guiWidth + 54, guiHeight + 37, 185, 89, displayInt, 2);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			if(xAxis >= 80 && xAxis <= 95 && yAxis >= 143 && yAxis <= 158)
			{
				mc.thePlayer.openGui(ICraft.instance, 0, mc.theWorld, 0, 0, 0);
			}

			if(xAxis >= 113 && xAxis <= 124 && yAxis >= getScroll() + 50 && yAxis <= getScroll() + 50 + 15)
			{
				dragOffset = yAxis - (getScroll() + 50);
				isDragging = true;
			}

			for (int i = 0; i < 5; i++)
			{
				if (getIndex() + i < ICraft.musics.size())
				{
					int yStart = i * 14 + 49;

					if(xAxis >= 51 && xAxis <= 111 && yAxis >= yStart && yAxis <= yStart + 14)
					{
						try {
							ICraft.mp3Player.stop();
							input = new FileInputStream(ICraft.musics.get(getIndex() + i).getPath());
							ICraft.currentMusicId = (getIndex() + i);
							ICraft.mp3Player.setMusic(input);
							ICraft.mp3Player.resetPlayerStatus();
							ICraft.mp3Player.setRepeatType(ICraft.mp3Player.getRepeatType());
							ICraft.mp3Player.play();

							mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[" + EnumChatFormatting.GOLD + "iCraft" + EnumChatFormatting.BLUE + "] " + EnumChatFormatting.GREEN + "Playing " + EnumChatFormatting.DARK_PURPLE + ICraft.musicNames.get(getIndex() + i)));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}
			}

			if (ICraft.mp3Player != null && ICraft.mp3Folder.listFiles().length != 0)
			{
				// Play / Pause
				if(xAxis >= 85 && xAxis <= 91 && yAxis >= 119 && yAxis <= 127)
				{
					int status = ICraft.mp3Player.getPlayerStatus();
					switch (status)
					{
						case 1:
							ICraft.mp3Player.pause();
							break;
						case 2:
							ICraft.mp3Player.resume();
							break;
						case 3:
							if (ICraft.mp3Player.getRepeatType() == 0)
							{
								try {
									ICraft.mp3Player.rePlay();
								} catch (Exception e) {
									throw new RuntimeException(e);
								}
							}
							break;
						default:
							break;
					}
				}
				// Change Repeat
				if(xAxis >= 53 && xAxis <= 59 && yAxis >= 41 && yAxis <= 47)
				{
					ICraft.mp3Player.setRepeatType((ICraft.mp3Player.getRepeatType() + 1 > 2 ? 0 : ICraft.mp3Player.getRepeatType() + 1));
				}
				// Forward
				if(xAxis >= 103 && xAxis <= 112 && yAxis >= 120 && yAxis <= 127)
				{
					ICraft.mp3Player.stop();
					try {
						input = new FileInputStream(ICraft.musics.get((ICraft.currentMusicId + 1 > ICraft.musics.size() - 1 ? 0 : ICraft.currentMusicId + 1)).getPath());
						ICraft.currentMusicId = (ICraft.currentMusicId + 1 > ICraft.musics.size() - 1 ? 0 : ICraft.currentMusicId + 1);
						ICraft.mp3Player.setMusic(input);
						ICraft.mp3Player.resetPlayerStatus();
						ICraft.mp3Player.play();

						mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[" + EnumChatFormatting.GOLD + "iCraft" + EnumChatFormatting.BLUE + "] " + EnumChatFormatting.GREEN + "Playing " + EnumChatFormatting.DARK_PURPLE + ICraft.musicNames.get(ICraft.currentMusicId)));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				// Backward
				if(xAxis >= 64 && xAxis <= 73 && yAxis >= 120 && yAxis <= 127)
				{
					ICraft.mp3Player.stop();
					try {
						input = new FileInputStream(ICraft.musics.get((ICraft.currentMusicId - 1 < 0 ? ICraft.musics.size() - 1 : ICraft.currentMusicId - 1)).getPath());
						ICraft.currentMusicId = (ICraft.currentMusicId - 1 < 0 ? ICraft.musics.size() - 1 : ICraft.currentMusicId - 1);
						ICraft.mp3Player.setMusic(input);
						ICraft.mp3Player.resetPlayerStatus();
						ICraft.mp3Player.play();

						mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.BLUE + "[" + EnumChatFormatting.GOLD + "iCraft" + EnumChatFormatting.BLUE + "] " + EnumChatFormatting.GREEN + "Playing " + EnumChatFormatting.DARK_PURPLE + ICraft.musicNames.get(ICraft.currentMusicId)));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int button, long ticks)
	{
		super.mouseClickMove(mouseX, mouseY, button, ticks);

		int xAxis = (mouseX - (width - xSize) / 2);
		int yAxis = (mouseY - (height - ySize) / 2);

		if(isDragging)
		{
			scroll = Math.min(Math.max((float)(yAxis - 50 - dragOffset) / 53, 0), 1);
		}
	}

	@Override
	protected void mouseMovedOrUp(int x, int y, int type)
	{
		super.mouseMovedOrUp(x, y, type);

		if(type == 0 && isDragging)
		{
			dragOffset = 0;
			isDragging = false;
		}
	}
}