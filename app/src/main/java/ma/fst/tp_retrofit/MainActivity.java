package ma.fst.tp_retrofit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.fst.tp_retrofit.adapter.UserAdapter;
import ma.fst.tp_retrofit.model.User;
import ma.fst.tp_retrofit.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private UserViewModel viewModel;
    private List<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EditText searchBar = findViewById(R.id.searchBar);

        adapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUsers().observe(this, users -> {
            try {
                if (users != null) {
                    allUsers = users;
                    adapter.setUserList(users);  // initial full list
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error loading users", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        // Real-time search
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    filterUsers(s.toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Search error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void filterUsers(String query) {
        if (allUsers == null) return;

        if (query.trim().isEmpty()) {
            adapter.setUserList(allUsers);
            return;
        }

        List<User> filtered = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(user);
            }
        }

        adapter.setUserList(filtered);
    }
}
