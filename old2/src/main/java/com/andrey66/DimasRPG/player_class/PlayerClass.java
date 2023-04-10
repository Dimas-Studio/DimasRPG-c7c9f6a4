package com.andrey66.DimasRPG.player_class;

import net.minecraft.nbt.CompoundTag;

public class PlayerClass {

    private String player_class;
    private boolean isMage;
    private boolean isEngineer;
    private boolean isSmith;
    private boolean isAdmin;
    private int id;
    public static final String ADMIN = "Admin";
    public static final String MAGE = "Mage";
    public static final String ENGINEER = "Engineer";
    public static final String SMITH = "Smith";
    public static final String NULL = "Null";
    public static final int ADMIN_ID = 0;
    public static final int MAGE_ID = 1;
    public static final int ENGINEER_ID = 2;
    public static final int SMITH_ID = 3;
    public static final int NULL_ID = -1;

    public PlayerClass() {
    }


    public String getPlayer_classString(){
        if(this.isAdmin){
            return ADMIN;
        }
        if (this.isMage) {
            return MAGE;
        }
        if (this.isEngineer) {
            return ENGINEER;
        }
        if (this.isSmith) {
            return SMITH;
        }
        return NULL;
    }

    public int getPlayerClassId(){
        return this.id;
    }

    public void setPlayerClassToAdmin(){
        this.isAdmin = true;
        this.isMage = false;
        this.isEngineer = false;
        this.isSmith = false;
        this.id = ADMIN_ID;
    }
    public void setPlayerClassToMage(){
        this.isAdmin = false;
        this.isMage = true;
        this.isEngineer = false;
        this.isSmith = false;
        this.id = MAGE_ID;
    }
    public void setPlayerClassToEngineer(){
        this.isAdmin = false;
        this.isMage = false;
        this.isEngineer = true;
        this.isSmith = false;
        this.id = ENGINEER_ID;
    }
    public void setPlayerClassToSmith(){
        this.isAdmin = false;
        this.isMage = false;
        this.isEngineer = false;
        this.isSmith = true;
        this.id = SMITH_ID;
    }
    public void setPlayerClassToNull(){
        this.isAdmin = false;
        this.isMage = false;
        this.isEngineer = false;
        this.isSmith = false;
        this.id = NULL_ID;
    }
    public void setPlayerClass(int id){
        if(id == NULL_ID){
            this.setPlayerClassToNull();
            return;
        }
        if(id == ADMIN_ID){
            this.setPlayerClassToAdmin();
            return;
        }
        if(id == MAGE_ID){
            this.setPlayerClassToMage();
            return;
        }
        if(id == ENGINEER_ID){
            this.setPlayerClassToEngineer();
            return;
        }
        if(id == SMITH_ID){
            this.setPlayerClassToSmith();
            return;
        }
    }

    public void copyFrom(PlayerClass source){
        this.player_class = source.player_class;
        this.isMage = source.isMage ;
        this.isEngineer = source.isEngineer;
        this.isSmith = source.isSmith;
        this.isAdmin = source.isAdmin;
        this.id = source.id;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("player_class", id);
    }

    public void loadNBTData(CompoundTag nbt){
        id = nbt.getInt("player_class");
    }
}