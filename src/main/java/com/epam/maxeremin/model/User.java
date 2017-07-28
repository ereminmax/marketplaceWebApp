package com.epam.maxeremin.model;

/**
 * Author: Maxim_Eremin
 * Email: Maxim_Eremin@epam.com
 * Date: 12-Jul-17
 */
public class User {
    private int id;
    private String fullName;
    private String billingAddress;
    private String login;
    private String password;

    public User() {
    }

    public User(int id, String fullName, String billingAddress, String login, String password) {
        this.id = id;
        this.fullName = fullName;
        this.billingAddress = billingAddress;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (billingAddress != null ? !billingAddress.equals(user.billingAddress) : user.billingAddress != null)
            return false;
        if (!login.equals(user.login)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
