package equipment;

import menu.Menu;
import units.Character;

public class Armor extends Equipment {

    private final ArmorType armorType;

    public ArmorType getArmorType() {
        return armorType;
    }

    public Armor(ArmorType armorType) {
        this.armorType = armorType;
    }

    public int getArmorClass() {
        return armorType.getArmorClass();
    }

    @Override
    public String getName() {
        return armorType.getTitle();
    }

    @Override
    public void execute() {
        armorMenu().showAndChoose();
    }

    private Menu armorMenu() {
        Menu armorMenu = new Menu("Меню для брони", false);
        if (this == Character.getInstance().getArmor()) {
            armorMenu.addItem("Снять броню", () -> {
                this.removeArmor();
                System.out.println("Сняв броню, вы чувствует облегчение");
                Character.getInstance().getInventory().addItem(this);
            });
        } else {
            armorMenu.addItem("Надеть эту броню", () -> {
                this.equipArmor();
                System.out.println("Надев доспех, вы чувствуете себя защищённее");
                Character.getInstance().getInventory().removeItem(this);
            });
        }
        armorMenu.addItem("Оставить как есть", () -> System.out.println("Вы решили не менять броню"));
        return armorMenu;
    }

    public void equipArmor() {
        Character.getInstance().setArmor(this);
    }

    public void removeArmor() {
        Character.getInstance().setArmor(null);

    }

    public String getArmorName() {
        return "Ваша броня: " + getArmorType().getTitle() + "\nКласс Доспеха: " + armorType.getArmorClass();

    }

}
