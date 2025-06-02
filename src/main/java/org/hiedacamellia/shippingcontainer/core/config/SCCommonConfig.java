package org.hiedacamellia.shippingcontainer.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SCCommonConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue SHIPPING_TIME = BUILDER
            .comment("The time it takes to ship items, in ticks. Default is 6000 ticks (5 minutes).")
            .comment("回收物品所需的时间，以tick为单位。默认值为6000 tick（5分钟）。")
            .defineInRange("shipping_time", 6000, 20, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
