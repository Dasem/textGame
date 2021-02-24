package equipment;

public class Armor extends Equipment {

    private final ArmorType armorType;

    public Armor(ArmorType armorType) {
        this.armorType = armorType;
    }

    public ArmorType getArmorType() {
        return armorType;
    }
}
