import com.nate.model.enums.Card;
import com.nate.model.enums.MadeHand;
import com.nate.util.scoring.impl.TexasScoreUtil;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class MainTest {

    public static void main(String[] args) {
        runFullTestSuite();
    }

    private static void runFullTestSuite() {

        /** ********** NOTE:: Testing the order in which splitHand() returns Card(s)
         *
         *  The Card(s) which make up the best hand will be the first card(s) in
         *  the list of cards returned by splitHand().  For instance:
        */

        // High Pair.. Pair card at list[0]
        List<Card> list = TexasScoreUtil.testSplitHand(Stream.of(Card.QUEEN_DIAMOND, Card.KING_CLUB, Card.QUEEN_HEART, Card.EIGHT_HEART, Card.THREE_HEART).collect(Collectors.toSet()), MadeHand.PAIR.value);
        assert list.get(0).equals(Card.QUEEN_DIAMOND);

        // Middle pair will still have a card from that middle pair at lists first index list[0]
        List<Card> listTwo = TexasScoreUtil.testSplitHand(Stream.of(Card.EIGHT_HEART, Card.KING_CLUB, Card.QUEEN_HEART, Card.EIGHT_DIAMOND, Card.THREE_HEART).collect(Collectors.toSet()), MadeHand.PAIR.value);
        assert listTwo.get(0).equals(Card.EIGHT_HEART);

        // Low pair will still  have a card from that low pair at it's first index, list[0]
        List<Card> listThree = TexasScoreUtil.testSplitHand(Stream.of(Card.THREE_HEART, Card.KING_CLUB, Card.QUEEN_HEART, Card.EIGHT_HEART, Card.THREE_DIAMOND).collect(Collectors.toSet()), MadeHand.PAIR.value);
        assert listThree.get(0).equals(Card.THREE_HEART);

        /**
         *  ^^ Above matters when calculating flop stats.  When you have a single paired hand,
         *  you need to calculate where list[0].value falls on the scale
         *      list[1].value ... list[list.length].value
         *
         *   if
         *
         *
         */


    }
}
