package poker.helper;

import java.util.List;
import java.util.Optional;

interface HandVerifier {
    Optional<Hand> getStrongestHand(Card firstCard, Card secondCard, Card thirdCard, Card fourthCard, Card fithCard);
    boolean containVersion(List<Card> hand, Version version);
    boolean allHasSameColour(List<Card> hand);
    boolean verifyIsRoyalFlush(List<Card> hand);
    boolean verifyIsStraightFlush(List<Card> hand);
    boolean verifyIsStraight(List<Card> hand);
    boolean verifyIsFour(List<Card> hand);
    boolean verifyIsFull(List<Card> hand);
    boolean verifyIsThree(List<Card> hand);
    boolean verifyIsPair(List<Card> hand);
    boolean verifyIsTwoPair(List<Card> hand);
    List<Hand> findCombinationsInHand(List<Card> hand);
}
