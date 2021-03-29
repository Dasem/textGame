package mechanic.location;

public enum PathDirection {
    TOP("Вы прошли вверх", "↑" , -1, 0),
    RIGHT("Вы прошли направо", "→",  0, 1),
    BOTTOM("Вы прошли вниз", "↓", 1, 0),
    LEFT("Вы прошли налево", "←", 0, -1);

    private final String message;
    private final String icon;
    private final int rowDifference;
    private final int columnDifference;

    PathDirection(String message, String icon, int rowDifference, int columnDifference) {
        this.message = message;
        this.icon = icon;
        this.rowDifference = rowDifference;
        this.columnDifference = columnDifference;
    }

    public String getMessage() {
        return message;
    }

    public String getIcon() {
        return icon;
    }

    public int getRowDifference() {
        return rowDifference;
    }

    public int getColumnDifference() {
        return columnDifference;
    }

}
