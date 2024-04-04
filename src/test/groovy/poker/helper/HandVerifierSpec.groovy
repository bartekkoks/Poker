package poker.helper

import spock.lang.Specification


class HandVerifierSpec extends Specification {

    public static final Card HEARTH_A = new Card(Colour.HEARTS, Version.A)
    public static final Card CLUBS_A = new Card(Colour.CLUBS, Version.A)
    public static final Card SPADES_A = new Card(Colour.SPADES, Version.A)
    public static final Card DIAMONDS_A = new Card(Colour.DIAMONDS, Version.A)
    public static final Card HEARTH_K = new Card(Colour.HEARTS, Version.K)
    public static final Card CLUBS_K = new Card(Colour.CLUBS, Version.K)
    public static final Card SPADES_K = new Card(Colour.SPADES, Version.K)
    public static final Card DIAMONDS_K = new Card(Colour.DIAMONDS, Version.K)
    public static final Card HEARTH_Q = new Card(Colour.HEARTS, Version.Q)
    public static final Card CLUBS_Q = new Card(Colour.CLUBS, Version.Q)
    public static final Card SPADES_Q = new Card(Colour.SPADES, Version.Q)
    public static final Card DIAMONDS_Q = new Card(Colour.DIAMONDS, Version.Q)
    public static final Card HEARTH_J = new Card(Colour.HEARTS, Version.J)
    public static final Card CLUBS_J = new Card(Colour.CLUBS, Version.J)
    public static final Card SPADES_J = new Card(Colour.SPADES, Version.J)
    public static final Card DIAMONDS_J = new Card(Colour.DIAMONDS, Version.J)
    public static final Card HEARTH_10 = new Card(Colour.HEARTS, Version.NUMBER_10)
    public static final Card CLUBS_10 = new Card(Colour.CLUBS, Version.NUMBER_10)
    public static final Card SPADES_10 = new Card(Colour.SPADES, Version.NUMBER_10)
    public static final Card DIAMONDS_10 = new Card(Colour.DIAMONDS, Version.NUMBER_10)
    public static final Card HEARTH_9 = new Card(Colour.HEARTS, Version.NUMBER_9)
    public static final Card CLUBS_9 = new Card(Colour.CLUBS, Version.NUMBER_9)
    public static final Card SPADES_9 = new Card(Colour.SPADES, Version.NUMBER_9)
    public static final Card DIAMONDS_9 = new Card(Colour.DIAMONDS, Version.NUMBER_9)

    public static final List<Card> ROYAL_FLUSH = List.of(CLUBS_A, CLUBS_K, CLUBS_Q, CLUBS_J, CLUBS_10)
    public static final List<Card> STRAIGHT_FLUSH = List.of(CLUBS_9, CLUBS_K, CLUBS_Q, CLUBS_J, CLUBS_10)
    public static final List<Card> FOUR_OF_A_KIND = List.of(CLUBS_A, HEARTH_A, SPADES_A, DIAMONDS_A, CLUBS_10)
    public static final List<Card> FULL_HOUSE = List.of(CLUBS_A, HEARTH_A, SPADES_A, CLUBS_J, HEARTH_J)
    public static final List<Card> FLUSH = List.of(HEARTH_9, HEARTH_10, HEARTH_K, HEARTH_J, HEARTH_A)
    public static final List<Card> STRAIGHT = List.of(HEARTH_A, CLUBS_K, CLUBS_Q, CLUBS_J, CLUBS_10)
    public static final List<Card> THREE_OF_A_KIND = List.of(CLUBS_A, HEARTH_A, SPADES_A, CLUBS_J, CLUBS_10)
    public static final List<Card> TWO_PAIR = List.of(CLUBS_A, HEARTH_A, CLUBS_Q, HEARTH_10, CLUBS_10)
    public static final List<Card> PAIR = List.of(CLUBS_A, HEARTH_A, CLUBS_Q, CLUBS_J, CLUBS_10)
    public static final List<Card> HIGH_CARD = List.of(HEARTH_A, CLUBS_K, CLUBS_Q, SPADES_9, SPADES_10)

    def "Should correctly verify hand"(List<Card> cardList, Hand hand) {

        given: "Hand Verifier"
        HandVerifier handVerifier = new HandVerifierImpl()

        when: "Hand calculation is scheduled"
        def strongestHand = handVerifier.getStrongestHand(cardList.get(0), cardList.get(1), cardList.get(2), cardList.get(3), cardList.get(4))

        then: "Strongest Hand are return"
        strongestHand.orElse(null) == hand

        where:
        cardList        | hand
        ROYAL_FLUSH     | Hand.ROYAL_FLUSH
        STRAIGHT_FLUSH  | Hand.STRAIGHT_FLUSH
        FOUR_OF_A_KIND  | Hand.FOUR_OF_A_KIND
        FULL_HOUSE      | Hand.FULL_HOUSE
        FLUSH           | Hand.FLUSH
        STRAIGHT        | Hand.STRAIGHT
        THREE_OF_A_KIND | Hand.THREE_OF_A_KIND
        TWO_PAIR        | Hand.TWO_PAIR
        PAIR            | Hand.PAIR
        HIGH_CARD       | Hand.HIGH_CARD
    }

    def "Should find if version is in hand"(List<Card> cardList, Version version, boolean containVer) {

        given:
        HandVerifierImpl handVerifier = new HandVerifierImpl()

        when:
        def containVersion = handVerifier.containVersion(cardList, version)

        then:
        containVersion == containVer

        where:
        cardList       | version          | containVer
        ROYAL_FLUSH    | Version.A        | true
        STRAIGHT_FLUSH | Version.K        | true
        STRAIGHT_FLUSH | Version.NUMBER_3 | false
    }

    def "Verify if all has same colour"(List<Card> cardList, boolean result) {

        given:
        HandVerifierImpl handVerifier = new HandVerifierImpl()

        when:
        def hasSameColour = handVerifier.allHasSameColour(cardList)

        then:
        hasSameColour == result

        where:
        cardList        | result
        ROYAL_FLUSH     | true
        STRAIGHT_FLUSH  | true
        FOUR_OF_A_KIND  | false
        FULL_HOUSE      | false
        FLUSH           | true
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify royal flush in hand"(List<Card> cardList, boolean result) {

        given:
        HandVerifierImpl handVerifier = new HandVerifierImpl()

        when:
        def royalFlush = handVerifier.verifyIsRoyalFlush(cardList)

        then:
        royalFlush == result

        where:
        cardList        | result
        ROYAL_FLUSH     | true
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | false
        FULL_HOUSE      | false
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify straight flush in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isStraightFlush = handVerifier.verifyIsStraightFlush(hand)

        then:
        isStraightFlush == result

        where:
        hand            | result
        ROYAL_FLUSH     | true
        STRAIGHT_FLUSH  | true
        FOUR_OF_A_KIND  | false
        FULL_HOUSE      | false
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify four in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isFour = handVerifier.verifyIsFour(hand)

        then:
        isFour == result

        where:
        hand            | result
        ROYAL_FLUSH     | false
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | true
        FULL_HOUSE      | false
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify full in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isFull = handVerifier.verifyIsFull(hand)

        then:
        isFull == result

        where:
        hand            | result
        ROYAL_FLUSH     | false
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | false
        FULL_HOUSE      | true
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify straight in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isStraightFlush = handVerifier.verifyIsStraight(hand)

        then:
        isStraightFlush == result

        where:
        hand            | result
        ROYAL_FLUSH     | true
        STRAIGHT_FLUSH  | true
        FOUR_OF_A_KIND  | false
        FULL_HOUSE      | false
        FLUSH           | false
        STRAIGHT        | true
        THREE_OF_A_KIND | false
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify three in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isThree = handVerifier.verifyIsThree(hand)

        then:
        isThree == result

        where:
        hand            | result
        ROYAL_FLUSH     | false
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | true
        FULL_HOUSE      | true
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | true
        TWO_PAIR        | false
        PAIR            | false
        HIGH_CARD       | false
    }

    def "Verify pair in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isPair = handVerifier.verifyIsPair(hand)

        then:
        isPair == result

        where:
        hand            | result
        ROYAL_FLUSH     | false
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | true
        FULL_HOUSE      | true
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | true
        TWO_PAIR        | true
        PAIR            | true
        HIGH_CARD       | false
    }

    def "Verify two pair in hand"(List<Card> hand, boolean result) {
        given:
        HandVerifier handVerifier = new HandVerifierImpl()

        when:
        def isPair = handVerifier.verifyIsTwoPair(hand)

        then:
        isPair == result

        where:
        hand            | result
        ROYAL_FLUSH     | false
        STRAIGHT_FLUSH  | false
        FOUR_OF_A_KIND  | true
        FULL_HOUSE      | true
        FLUSH           | false
        STRAIGHT        | false
        THREE_OF_A_KIND | false
        TWO_PAIR        | true
        PAIR            | false
        HIGH_CARD       | false
    }
}
