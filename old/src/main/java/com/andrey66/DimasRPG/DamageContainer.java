package com.andrey66.DimasRPG;

public interface DamageContainer {
    int getType(); //0 - admin; 1 - physic; 2 - range; 3 - magic

    void setType(int value);

    float getValue();

    void setValue(float value);
}
