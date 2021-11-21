import java.io.Serializable;

/**
 * Represents different states within the game to be able to run different code depending on the players actions
 */
public enum PlayerAction implements Serializable  {
    SelectingPiece,
    SelectingTileToMoveTo,
    MakingJump,
}