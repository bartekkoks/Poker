package poker.helper;

public enum Version {
    NUMBER_2(2), NUMBER_3(3), NUMBER_4(4), NUMBER_5(5), NUMBER_6(6), NUMBER_7(7), NUMBER_8(8), NUMBER_9(9), NUMBER_10(10), J(11), Q(12), K(13), A(14);
    public final int rank;

    Version(int rank) {
        this.rank = rank;
    }
    }
