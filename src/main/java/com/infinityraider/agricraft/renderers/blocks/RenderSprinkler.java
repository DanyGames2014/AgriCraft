package com.infinityraider.agricraft.renderers.blocks;

import com.infinityraider.agricraft.blocks.BlockSprinkler;
import com.infinityraider.agricraft.reference.Constants;
import com.infinityraider.agricraft.blocks.tiles.irrigation.TileEntitySprinkler;
import com.infinityraider.agricraft.utility.BaseIcons;
import com.infinityraider.infinitylib.render.block.RenderBlockWithTileBase;
import com.infinityraider.infinitylib.render.tessellation.ITessellator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderSprinkler extends RenderBlockWithTileBase<BlockSprinkler, TileEntitySprinkler> {

	// Dimensions
	private static final float MIN_Y = 8.0F;
	private static final float MAX_Y = 12.0F;
	private static final float MIN_C = 7.0F;
	private static final float MAX_C = 9.0F;
	private static final float BLADE_W = 1.0F;
	private static final float BLADE_L = 3.0F;

	// Calculated
	private static final float BMX_Y = MIN_Y + BLADE_W;
	private static final float BMX_A = MIN_C - BLADE_L;
	private static final float BMX_B = MAX_C + BLADE_L;

	public RenderSprinkler(BlockSprinkler block) {
		super(block, new TileEntitySprinkler(), true, true, true);
	}

	public void renderStatic(ITessellator tess, TileEntitySprinkler te) {
		tess.translate(0, 4 * Constants.UNIT, 0);
		tess.drawScaledPrism(4, 8, 4, 12, 16, 12, te.getChannelIcon());
	}

	public void renderDynamic(ITessellator tess, TileEntitySprinkler te) {
		tess.translate(0.5F, 0, 0.5F);
		tess.rotate(te.angle, 0, 1, 0);
		tess.translate(-0.5F, 0, -0.5F);

		final TextureAtlasSprite icon = BaseIcons.IRON_BLOCK.getIcon();
		// Draw Core
		tess.drawScaledPrism(MIN_C, MIN_Y, MIN_C, MAX_C, MAX_Y, MAX_C, icon);
		// Draw Blades
		tess.drawScaledPrism(BMX_A, MIN_Y, MIN_C, BMX_B, BMX_Y, MAX_C, icon);
		tess.drawScaledPrism(MIN_C, MIN_Y, BMX_A, MAX_C, BMX_Y, BMX_B, icon);
	}

	@Override
	public void renderInventoryBlock(ITessellator tessellator, World world, IBlockState state, BlockSprinkler block, TileEntitySprinkler tile,
									 ItemStack stack, EntityLivingBase entity, ItemCameraTransforms.TransformType type) {
		// Draw Top
		tessellator.drawScaledPrism(4, 8, 4, 12, 16, 12, BaseIcons.OAK_PLANKS.getIcon());
		// Get Core Icon
		final TextureAtlasSprite coreIcon = BaseIcons.IRON_BLOCK.getIcon();
		// Draw Core
		tessellator.drawScaledPrism(MIN_C, MIN_Y - 8, MIN_C, MAX_C, MAX_Y - 4, MAX_C, coreIcon);
		// Draw Blades
		tessellator.drawScaledPrism(BMX_A, MIN_Y - 8, MIN_C, BMX_B, BMX_Y - 8, MAX_C, coreIcon);
		tessellator.drawScaledPrism(MIN_C, MIN_Y - 8, BMX_A, MAX_C, BMX_Y - 8, BMX_B, coreIcon);

	}

	@Override
	public List<ResourceLocation> getAllTextures() {
		return Collections.emptyList();
	}

	@Override
	public void renderWorldBlock(ITessellator tessellator, World world, BlockPos pos, double x, double y, double z, IBlockState state, BlockSprinkler block,
								 TileEntitySprinkler tile, boolean dynamicRender, float partialTick, int destroyStage) {
		if(dynamicRender) {
			this.renderDynamic(tessellator, tile);
		} else {
			this.renderStatic(tessellator, tile);
		}
	}

	@Override
	public TextureAtlasSprite getIcon() {
		return getTileEntity().getChannelIcon();
	}

	@Override
	public boolean applyAmbientOcclusion() {
		return false;
	}
}