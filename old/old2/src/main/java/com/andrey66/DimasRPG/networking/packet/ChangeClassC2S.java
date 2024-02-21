package com.andrey66.DimasRPG.networking.packet;

import com.andrey66.DimasRPG.player_class.PlayerClassProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeClassC2S {
    public ChangeClassC2S(){

    }

    public ChangeClassC2S(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER SIDE
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            int new_id = (int) ((Math.random() * (4)) + 4) - 1;
            player.getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(player_class ->{
                player_class.setPlayerClass(new_id);
                player.sendSystemMessage(Component.literal(player_class.getPlayer_classString()));
            });
        });
        return true;
    }
}
