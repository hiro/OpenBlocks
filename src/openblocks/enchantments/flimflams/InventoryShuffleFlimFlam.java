package openblocks.enchantments.flimflams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import openblocks.api.IAttackFlimFlam;

public class InventoryShuffleFlimFlam implements IAttackFlimFlam {

	@Override
	public String name() {
		return "inventoryshuffle";
	}

	@Override
	public float weight() {
		return 1;
	}

	@Override
	public void execute(EntityPlayer source, EntityPlayer target) {
		if (target.worldObj.isRemote) return;
		final ItemStack[] mainInventory = target.inventory.mainInventory;
		List<ItemStack> stacks = Arrays.asList(mainInventory);
		Collections.shuffle(stacks);
		target.inventory.mainInventory = stacks.toArray(mainInventory);
	}

}
