
import com.mycompany.onlinerpg.Game;
import com.mycompany.onlinerpg.Player;
import com.mycompany.onlinerpg.Square;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vadimkaz58
 */
public class TestCheckWiners {
    Square[][] field = new Square[3][3];
    Player winner = new Player("winner");
    Player loser = new Player("lose");
    
    @Before
    public void before() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Square();
            }
        }
    }
    
    @Test
    public void verticalTest() {
        field[0][0].player = winner;
        field[1][0].player = winner;
        field[2][0].player = winner;
        field[0][2].player = loser;
        field[1][2].player = loser;
        Game game = new Game(field);
        var expected = winner;
        var actual = game.checkWinner();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void horizontalTest() {
        field[0][0].player = winner;
        field[0][1].player = winner;
        field[0][2].player = winner;
        field[2][1].player = loser;
        field[2][2].player = loser;
        Game game = new Game(field);
        var expected = winner;
        var actual = game.checkWinner();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void diagonalLeftTest() {
        field[0][0].player = winner;
        field[1][1].player = winner;
        field[2][2].player = winner;
        field[0][2].player = loser;
        field[1][2].player = loser;
        Game game = new Game(field);
        var expected = winner;
        var actual = game.checkWinner();
        Assert.assertEquals(expected, actual);
    }
    @Test
    public void diagonalRightTest() {
        field[0][2].player = winner;
        field[1][1].player = winner;
        field[2][0].player = winner;
        field[0][1].player = loser;
        field[1][2].player = loser;
        Game game = new Game(field);
        var expected = winner;
        var actual = game.checkWinner();
        Assert.assertEquals(expected, actual);
    }
}
