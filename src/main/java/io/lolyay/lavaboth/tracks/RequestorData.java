package io.lolyay.lavaboth.tracks;

import net.dv8tion.jda.api.entities.Member;

/**
 * Represents the data of a user who made a request in the music bot.
 *
 * @param userId    The Discord user ID of the requester
 * @param userName  The display name of the requester
 * @param dcGuildId The Discord guild ID where the request was made
 */
public record RequestorData(long userId, String userName, long dcGuildId) {
    /**
     * Creates a RequestorData instance from a Discord Member object.
     *
     * @param member The Discord Member object
     * @return A new RequestorData instance with the member's information
     */
    public static RequestorData fromMember(Member member) {
        return new RequestorData(
                member.getIdLong(),
                member.getEffectiveName(),
                member.getGuild().getIdLong()
        );
    }

    /**
     * Creates a RequestorData instance representing the system user.
     * This is typically used for system-generated requests or events.
     *
     * @return A RequestorData instance where userId is -1, userName is "System", and dcGuildId is -1
     */
    public static RequestorData system(){
        return new RequestorData(
                -1,
                "System",
                -1
        );
    }

    /**
     * Creates a RequestorData instance representing an anonymous user.
     * This can be used when the requester's identity is unknown or not applicable.
     *
     * @return A RequestorData instance where userId is 0, userName is "Anonymous", and dcGuildId is 0
     */
    public static RequestorData anonymous(){
        return new RequestorData(
                0,
                "Anonymous",
                0
        );
    }
}
