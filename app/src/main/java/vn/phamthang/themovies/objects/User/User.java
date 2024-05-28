package vn.phamthang.themovies.objects.User;

import java.util.List;

import vn.phamthang.themovies.objects.request.MovieRequest;

public class User {
    private String idUser;
    private String email;
    private String name;
    private String password;
    private List<MovieRequest> listFavoriteMovie;

    public User() {
    }

    public User(String idUser, String email, String name, String password, List<MovieRequest> listFavoriteMovie) {
        this.idUser = idUser;
        this.email = email;
        this.name = name;
        this.password = password;
        this.listFavoriteMovie = listFavoriteMovie;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieRequest> getListFavoriteMovie() {
        return listFavoriteMovie;
    }

    public void setListFavoriteMovie(List<MovieRequest> listFavoriteMovie) {
        this.listFavoriteMovie = listFavoriteMovie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
