package ma.fst.tp_retrofit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ma.fst.tp_retrofit.model.User;
import ma.fst.tp_retrofit.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private MutableLiveData<List<User>> users;
    private UserRepository repository;

    public UserViewModel() {
        repository = new UserRepository();
        users = new MutableLiveData<>();
        loadUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    private void loadUsers() {
        repository.fetchUsers(users);
    }
}
