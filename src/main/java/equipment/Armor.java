package equipment;

import menu.Menu;
import units.Character;

public class Armor extends Equipment {

    private final ArmorType armorType;

    Menu menu = new Menu("Меню для брони", false);

    public ArmorType getArmorType() {
        return armorType;
    }

    public Armor(ArmorType armorType) {
        this.armorType = armorType;
        menu.addItem("Надеть", () -> {
            System.out.println("Вы надели выбранную броню");
            Character.getInstance().setArmor(new Armor(armorType));
        });
        menu.addItem("Не надевать", () -> System.out.println("Вы решили остаться в старой броне"));
    }

    @Override
    public String getName() {
        return armorType.getTitle();
    }

    @Override
    public void execute() {
        // Надеть / нет
    }
}
