package designpatterns.chapterone;

/**
 * 骑士
 */
public class Knight extends Character {
    @Override
    void fight() {
        weapon.useWeapon();
    }

    public static void main(String[] args) {
        Knight knight = new Knight();
        knight.setWeapon(new SwordWeapon());
        knight.fight();
    }
}
