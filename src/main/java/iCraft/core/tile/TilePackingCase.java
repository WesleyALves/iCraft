package iCraft.core.tile;

import net.minecraft.item.ItemStack;

public class TilePackingCase extends TileInventory
{
	public TilePackingCase()
	{
		super("PackingCase");
		inventory = new ItemStack[1];
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
	{
		return false;
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		updateContainingBlockInfo();
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int side)
	{
		return false;
	}
}