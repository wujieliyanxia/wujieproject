package designpatterns.chapterone;

/**
 * 角色 超类。将使用武器的类分离出去
 */
public abstract class Character {
    // 武器类别
    Weapon weapon;

    // 动态设置武器
    void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    // fight
    abstract void fight();
}
