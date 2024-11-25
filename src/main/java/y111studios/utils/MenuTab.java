package y111studios.utils;

public enum MenuTab {
    ACCOMMODATION, CATERING_RECREATION, TEACHING;

    /**
     * Converts the MenuTab to an integer based on the order of the tabs. This is agnostic to the
     * order of the variants described in the enum.
     * 
     * @return the tab's order in the list
     */
    public int toInt() {
        switch (this) {
            case ACCOMMODATION:
                return 0;
            case CATERING_RECREATION:
                return 1;
            case TEACHING:
                return 2;
            default:
                throw new UnreachableException("MenuTab variant not defined");
        }
    }
}
