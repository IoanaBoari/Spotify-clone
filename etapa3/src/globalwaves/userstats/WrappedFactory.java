package globalwaves.userstats;

import globalwaves.user.artist.ArtistWrapped;
import globalwaves.user.host.HostWrapped;
import globalwaves.user.normalUser.NormalWrapped;

class WrappedFactory {
    public enum WrappedType {
        user, artist, host
    }

    /**
     * Factory method for creating instances of the appropriate Wrapped subclass
     * based on the specified WrappedType.
     * This method is used to implement the factory pattern for creating Wrapped objects.
     *
     * @param wrappedType The type of Wrapped subclass to create (user, artist, or host).
     * @return An instance of the corresponding Wrapped subclass.
     * @throws IllegalArgumentException If the provided WrappedType is not recognized.
     */
    public static Wrapped createWrapped(final WrappedType wrappedType) {
        switch (wrappedType) {
            case user -> {
                return new NormalWrapped();
            }
            case artist -> {
                return new ArtistWrapped();
            }
            case host -> {
                return new HostWrapped();
            }
        }
        throw new IllegalArgumentException("The type of user " + wrappedType
                + " is not recognized.");
    }
}
