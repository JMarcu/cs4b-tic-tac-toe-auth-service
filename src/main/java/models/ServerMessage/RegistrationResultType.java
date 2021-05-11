package models.ServerMessage;

public enum RegistrationResultType {
    /** Indicates the user's provided password does not meet the server's defined requirements for a valid password. */
    PASSWORD_FAILS_REQUIREMENTS,

    /** Indicates registration has failed but no other code defines the reason why. */
    UNKNOWN_ERROR,

    /** Indicates the user has selected a username which has already been selected by another user. Usernames must be unique. */
    USERNAME_ALREADY_EXISTS,

    /** Indicates the user has successfully registered with their provided credentials. */
    SUCCESS
}
