package equipment;

public enum WeaponType {
    TWO_HANDED_SWORD("Двуручный меч") {
        @Override
        int getDamage() {
            return 12;
        }
    },
    DAGGER("Кинжал") {
        @Override
        int getDamage() {
            return 4;
        }
    },
    STAFF("Посох") {
        @Override
        int getDamage() {
            return 10;
        }
    },
    SWORD("Меч") {
        @Override
        int getDamage() {
            return 6;
        }
    };

    private final String title;

    WeaponType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract int getDamage();
}
