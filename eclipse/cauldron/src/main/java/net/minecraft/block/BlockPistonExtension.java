package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon field_150088_a;
    private static final String __OBFID = "CL_00000367";

    public BlockPistonExtension()
    {
        super(Material.piston);
        this.setStepSound(soundTypePiston);
        this.setHardness(0.5F);
    }

    @SideOnly(Side.CLIENT)
    public void func_150086_a(IIcon p_150086_1_)
    {
        this.field_150088_a = p_150086_1_;
    }

    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
    {
        if (p_149681_6_.capabilities.isCreativeMode)
        {
            int i1 = getDirectionMeta(p_149681_5_);
            Block block = p_149681_1_.getBlock(p_149681_2_ - Facing.offsetsXForSide[i1], p_149681_3_ - Facing.offsetsYForSide[i1], p_149681_4_ - Facing.offsetsZForSide[i1]);

            if (block == Blocks.piston || block == Blocks.sticky_piston)
            {
                p_149681_1_.setBlockToAir(p_149681_2_ - Facing.offsetsXForSide[i1], p_149681_3_ - Facing.offsetsYForSide[i1], p_149681_4_ - Facing.offsetsZForSide[i1]);
            }
        }

        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);

        if ((p_149749_6_ & 7) >= Facing.oppositeSide.length)
        {
            return;    // CraftBukkit - fix a piston AIOOBE issue
        }

        int i1 = Facing.oppositeSide[getDirectionMeta(p_149749_6_)];
        p_149749_2_ += Facing.offsetsXForSide[i1];
        p_149749_3_ += Facing.offsetsYForSide[i1];
        p_149749_4_ += Facing.offsetsZForSide[i1];
        Block block1 = p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_);

        if (block1 == Blocks.piston || block1 == Blocks.sticky_piston)
        {
            p_149749_6_ = p_149749_1_.getBlockMetadata(p_149749_2_, p_149749_3_, p_149749_4_);

            if (BlockPistonBase.isExtended(p_149749_6_))
            {
                block1.dropBlockAsItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_, 0);
                p_149749_1_.setBlockToAir(p_149749_2_, p_149749_3_, p_149749_4_);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void func_150087_e()
    {
        this.field_150088_a = null;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        int k = getDirectionMeta(p_149691_2_);
        return p_149691_1_ == k ? (this.field_150088_a != null ? this.field_150088_a : ((p_149691_2_ & 8) != 0 ? BlockPistonBase.getPistonBaseIcon("piston_top_sticky") : BlockPistonBase.getPistonBaseIcon("piston_top_normal"))) : (k < 6 && p_149691_1_ == Facing.oppositeSide[k] ? BlockPistonBase.getPistonBaseIcon("piston_top_normal") : BlockPistonBase.getPistonBaseIcon("piston_side"));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {}

    public int getRenderType()
    {
        return 17;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return false;
    }

    public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
    {
        return false;
    }

    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
    {
        int l = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
        float f = 0.25F;
        float f1 = 0.375F;
        float f2 = 0.625F;
        float f3 = 0.25F;
        float f4 = 0.75F;

        switch (getDirectionMeta(l))
        {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            case 1:
                this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            case 4:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            case 5:
                this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        int l = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float f = 0.25F;

        switch (getDirectionMeta(l))
        {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                break;
            case 1:
                this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case 2:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                break;
            case 3:
                this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                break;
            case 4:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                break;
            case 5:
                this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        int l = getDirectionMeta(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));

        if ((l & 7) >= Facing.oppositeSide.length)
        {
            return;    // CraftBukkit - fix a piston AIOOBE issue
        }

        Block block1 = p_149695_1_.getBlock(p_149695_2_ - Facing.offsetsXForSide[l], p_149695_3_ - Facing.offsetsYForSide[l], p_149695_4_ - Facing.offsetsZForSide[l]);

        if (block1 != Blocks.piston && block1 != Blocks.sticky_piston)
        {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
        else
        {
            block1.onNeighborBlockChange(p_149695_1_, p_149695_2_ - Facing.offsetsXForSide[l], p_149695_3_ - Facing.offsetsYForSide[l], p_149695_4_ - Facing.offsetsZForSide[l], p_149695_5_);
        }
    }

    public static int getDirectionMeta(int p_150085_0_)
    {
        return MathHelper.clamp_int(p_150085_0_ & 7, 0, Facing.offsetsXForSide.length - 1);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        int l = p_149694_1_.getBlockMetadata(p_149694_2_, p_149694_3_, p_149694_4_);
        return (l & 8) != 0 ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
    }
}