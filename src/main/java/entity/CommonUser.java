package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof CommonUser)) {
            return false;
        }

        final CommonUser commonUser = (CommonUser) o;
        return name.equals(commonUser.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
