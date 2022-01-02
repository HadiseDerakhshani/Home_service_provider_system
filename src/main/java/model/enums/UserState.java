package model.enums;

public enum UserState {
    NEW(1, "new"),
    CONFIRMED(2, "confirmed"),
    WAITING_CONFIRM(3, "waiting_confirm"),
    NONE(4, "none");
    private String nameState;
    private int numState;

    UserState( int numState,String nameState) {
        this.nameState = nameState;
        this.numState = numState;
    }
}
