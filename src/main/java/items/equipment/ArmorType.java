package items.equipment;

public enum ArmorType {
    LIGHT_ARMOR("Лёгкая броня") {
        @Override
        public int  getArmorClass() {
            return 14;
        }
    },
    MEDIUM_ARMOR("Средняя броня") {
        @Override
        public int getArmorClass() {
            return 16;
        }
    },
    HEAVY_ARMOR("Тяжёлая броня") {
        @Override
        public int getArmorClass() {
            return 18;
        }
    };

    private final String title;

    ArmorType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public int getArmorClass();
}

