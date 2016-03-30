package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by gorobec on 20.03.16.
 */
public class User implements Serializable{
    private String nickName;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;

    public User(String nickName, String password){
        this.nickName = nickName;
        this.password = password;
    }

    public User(String nickName, String password, String passwordRepeat,String name, String surname, LocalDate birthDate, Gender gender) throws IncorrectPasswordRepeatException, FieldLengthIsToBigException {
        if(nickName.length() > 20){
            throw new FieldLengthIsToBigException();
        }
        this.nickName = nickName;
        if(!password.equals(passwordRepeat)){
            throw new IncorrectPasswordRepeatException();
        }
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

        if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null) return false;
        return !(password != null ? !password.equals(user.password) : user.password != null);

    }

    @Override
    public int hashCode() {
        int result = nickName != null ? nickName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString(){
        return String.format("[%s]", nickName);
    }
}
