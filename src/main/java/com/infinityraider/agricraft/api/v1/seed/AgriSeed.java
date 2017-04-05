/*
 */
package com.infinityraider.agricraft.api.v1.seed;

import com.infinityraider.agricraft.api.v1.plant.IAgriPlant;
import com.infinityraider.agricraft.api.v1.stat.IAgriStat;
import net.minecraft.item.ItemStack;

/**
 * A simple class for representing seeds.
 *
 * @author RlonRyan
 */
public class AgriSeed {
	
	private final IAgriPlant plant;
	private final IAgriStat stat;
	
	public AgriSeed(IAgriPlant plant, IAgriStat stat) {
		this.plant = plant;
		this.stat = stat;
	}

	public IAgriPlant getPlant() {
		return this.plant;
	}

	public IAgriStat getStat() {
		return this.stat;
	}

	public ItemStack toStack() {
		ItemStack stack = this.plant.getSeed();
		this.stat.writeToNBT(stack.getTagCompound());
		return stack;
	}
	
}