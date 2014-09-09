package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

import net.minecraft.entity.EntityLivingBase; // CraftBukkit

public class EntityAIPanic extends EntityAIBase
{
    private EntityCreature theEntityCreature;
    private double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    private static final String __OBFID = "CL_00001604";

    public EntityAIPanic(EntityCreature p_i1645_1_, double p_i1645_2_)
    {
        this.theEntityCreature = p_i1645_1_;
        this.speed = p_i1645_2_;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning())
        {
            return false;
        }
        else
        {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.randPosX = vec3.xCoord;
                this.randPosY = vec3.yCoord;
                this.randPosZ = vec3.zCoord;
                return true;
            }
        }
    }

    public void startExecuting()
    {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    public boolean continueExecuting()
    {
        // CraftBukkit start - introduce a temporary timeout hack until this is fixed properly
        if ((this.theEntityCreature.ticksExisted - this.theEntityCreature.func_142015_aE()) > 100)
        {
            this.theEntityCreature.setRevengeTarget((EntityLivingBase) null);
            return false;
        }

        // CraftBukkit end
        return !this.theEntityCreature.getNavigator().noPath();
    }
}