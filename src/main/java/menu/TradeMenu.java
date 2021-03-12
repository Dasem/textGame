package menu;

public class TradeMenu extends Menu{
    public TradeMenu(String title, MenuSetting... menuSettings) {
        super(title, menuSettings);
    }

    public TradeMenu(Holder<String> titleHolder, MenuSetting... menuSettings) {
        super(titleHolder, menuSettings);
    }
    private boolean success ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
