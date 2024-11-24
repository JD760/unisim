package y111studios;

import lombok.Getter;

/**
 * AssetPaths class used to store the paths to the assets used in the game.
 * 
 * <p>
 * This class is used to store the constant paths to the internal assets to reduce the occurence of
 * magic strings in the code.
 * </p>
 */
public enum AssetPaths {
    START_SCREEN("assets/StartScreen.png"),
    MAP_BACKGROUND_TOP_LEFT("assets/UnisimMapTL.png"),
    MAP_BACKGROUND_TOP_RIGHT("assets/UnisimMapTR.png"),
    MAP_BACKGROUND_BOTTOM_LEFT("assets/UnisimMapBL.png"),
    MAP_BACKGROUND_BOTTOM_RIGHT("assets/UnisimMapBR.png"),
    MENU("assets/Menu.png"),
    ACCOMMODATION_MENU("assets/Accommodation.png"),
    CATERING_MENU("assets/Catering.png"),
    TEACHING_MENU("assets/Teaching.png"),
    PAUSE("assets/Pause.png"),
    ACC1("assets/Acc1.png"),
    ACC2("assets/Acc2.png"),
    ACC3("assets/Acc3.png"),
    ACC4("assets/Acc4.png"),
    ACC5("assets/Acc5.png"),
    CATER1("assets/Cater1.png"),
    CATER2("assets/Cater2.png"),
    CATER3("assets/Cater3.png"),
    REC1("assets/Rec1.png"),
    REC2("assets/Rec2.png"),
    TEACH1("assets/Teach1.png"),
    TEACH2("assets/Teach2.png"),
    TEACH3("assets/Teach3.png"),
    TEACH4("assets/Teach4.png"),
    TEACH5("assets/Teach5.png"),
    TRASH("iconduck_assets/Trash.png"),
    GAME_OVER("assets/GameOver.png");

    private @Getter final String path;

    AssetPaths(String path) {
        this.path = path;
    }
}
