package vn.phamthang.themovies.objects.User;

import java.util.List;

import vn.phamthang.themovies.objects.request.MovieRequest;

public class User {
    private int idUser;
    private String name;
    private List<MovieRequest> listFavoriteMovie;

    public User() {
    }

    public User(int idUser, String name, List<MovieRequest> listFavoriteMovie) {
        this.idUser = idUser;
        this.name = name;
        this.listFavoriteMovie = listFavoriteMovie;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

}
