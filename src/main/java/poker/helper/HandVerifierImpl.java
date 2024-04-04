package poker.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

class HandVerifierImpl implements HandVerifier {
    @Override
    public Optional<Hand> getStrongestHand(Card firstCard, Card secondCard, Card thirdCard, Card fourthCard, Card fithCard) {
        List<Card> hand = List.of(firstCard, secondCard, thirdCard, fourthCard, fithCard);
        return findCombinationsInHand(hand).stream().min(Comparator.comparingInt(hand1 -> hand1.rank));
    }

    @Override
    public boolean containVersion(List<Card> hand, Version version) {
        return hand.stream().anyMatch(card -> card.version.equals(version));
    }

    @Override
    public boolean allHasSameColour(List<Card> hand) {
        Colour colour = hand.get(0).colour;
        return hand.stream().allMatch(card -> card.colour.equals(colour));
    }

    @Override
    public boolean verifyIsRoyalFlush(List<Card> hand) {
        return allHasSameColour(hand)
                && containVersion(hand, Version.A)
                && containVersion(hand, Version.K)
                && containVersion(hand, Version.Q)
                && containVersion(hand, Version.J)
                && containVersion(hand, Version.NUMBER_10);
    }

    @Override
    public boolean verifyIsStraightFlush(List<Card> hand) {
        return verifyIsStraight(hand) && allHasSameColour(hand);
    }

    @Override
    public boolean verifyIsStraight(List<Card> hand) {
        List<Integer> sortedRanks = hand.stream().map(card -> card.version.rank).sorted(Integer::compareTo).toList();
        return IntStream.range(0, sortedRanks.size() - 1).noneMatch(i -> sortedRanks.get(i + 1) - sortedRanks.get(i) != 1);
    }

    @Override
    public boolean verifyIsFour(List<Card> hand) {
        if (hand.stream().map(card -> card.version).distinct().count() == 2) {
            Card firstCard = hand.get(0);
            long numberOfCardInType = hand.stream().filter(card -> card.version.equals(firstCard.version)).count();
            return numberOfCardInType == 1 || numberOfCardInType == 4;
        }
        return false;
    }

    @Override
    public boolean verifyIsFull(List<Card> hand) {
        if (hand.stream().map(card -> card.version).distinct().count() == 2) {
            Card firstCard = hand.get(0);
            long numberOfCardInType = hand.stream().filter(card -> card.version.equals(firstCard.version)).count();
            return numberOfCardInType == 2 || numberOfCardInType == 3;
        }
        return false;
    }

    @Override
    public boolean verifyIsThree(List<Card> hand) {
        return hand.stream().anyMatch(card -> atLeastThreeOfAKind(hand, card));
    }

    @Override
    public boolean verifyIsPair(List<Card> hand) {
        return hand.stream().anyMatch(card -> atLeastTwoOfAKind(hand, card));
    }

    @Override
    public boolean verifyIsTwoPair(List<Card> hand) {
        if (hand.stream().map(card -> card.version).distinct().count() == 3 && verifyIsThree(hand)) {
            return false;
        }
        return hand.stream().map(card -> card.version).distinct().count() <= 3;
    }

    private boolean atLeastThreeOfAKind(List<Card> hand, Card card) {
        long numberOfAKind = countCardInHand(hand, card.version);
        return numberOfAKind == 3 || numberOfAKind == 4;
    }

    private boolean atLeastTwoOfAKind(List<Card> hand, Card card) {
        long numberOfAKind = countCardInHand(hand, card.version);
        return numberOfAKind == 2 || numberOfAKind == 3 || numberOfAKind == 4;
    }

    private long countCardInHand(List<Card> hand, Version cardVersion) {
        return hand.stream().filter(card -> card.version.equals(cardVersion)).count();
    }

    @Override
    public List<Hand> findCombinationsInHand(List<Card> cardList) {
        List<Hand> handsList = new ArrayList<>();
        if (verifyIsRoyalFlush(cardList)) {
            handsList.add(Hand.ROYAL_FLUSH);
        } else if (verifyIsStraightFlush(cardList)) {
            handsList.add(Hand.STRAIGHT_FLUSH);
        } else if (verifyIsFour(cardList)) {
            handsList.add(Hand.FOUR_OF_A_KIND);
        } else if (verifyIsFull(cardList)) {
            handsList.add(Hand.FULL_HOUSE);
        } else if (allHasSameColour(cardList)) {
            handsList.add(Hand.FLUSH);
        } else if (verifyIsStraight(cardList)) {
            handsList.add(Hand.STRAIGHT);
        } else if (verifyIsThree(cardList)) {
            handsList.add(Hand.THREE_OF_A_KIND);
        } else if (verifyIsTwoPair(cardList)) {
            handsList.add(Hand.TWO_PAIR);
        } else if (verifyIsPair(cardList)) {
            handsList.add(Hand.PAIR);
        }
        handsList.add(Hand.HIGH_CARD);
        return handsList;
    }
}
