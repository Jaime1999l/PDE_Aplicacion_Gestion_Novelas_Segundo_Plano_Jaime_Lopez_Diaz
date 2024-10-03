package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain.Novel;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity.AddEditNovelActivity;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity.FavoritesActivity;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.ui.mainNovel.NovelAdapter;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity.ReviewActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NovelViewModel novelViewModel;
    private NovelAdapter novelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        ImageButton buttonMenu = findViewById(R.id.buttonMenu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        novelAdapter = new NovelAdapter();
        recyclerView.setAdapter(novelAdapter);

        novelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        novelViewModel.getAllNovels().observe(this, new Observer<List<Novel>>() {
            @Override
            public void onChanged(List<Novel> novels) {
                novelAdapter.setNovels(novels);
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        novelAdapter.setOnItemClickListener(new NovelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Novel novel) {
                Intent intent = new Intent(MainActivity.this, AddEditNovelActivity.class);
                intent.putExtra("EXTRA_ID", novel.getId());
                intent.putExtra("EXTRA_TITLE", novel.getTitle());
                intent.putExtra("EXTRA_AUTHOR", novel.getAuthor());
                intent.putExtra("EXTRA_YEAR", novel.getYear());
                intent.putExtra("EXTRA_SYNOPSIS", novel.getSynopsis());
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Novel novel) {
                novel.setFavorite(!novel.isFavorite());
                novelViewModel.update(novel);

                View favoriteIcon = findViewById(R.id.image_favorite);
                ScaleAnimation scaleAnimation = new ScaleAnimation(
                        1.0f, 1.2f, 1.0f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
                scaleAnimation.setDuration(300);
                scaleAnimation.setRepeatCount(1);
                scaleAnimation.setRepeatMode(Animation.REVERSE);
                favoriteIcon.startAnimation(scaleAnimation);

                String message = novel.isFavorite() ? "Añadido a favoritos" : "Eliminado de favoritos";
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(Novel novel) {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Eliminar Novela")
                        .setMessage("¿Estás seguro de que deseas eliminar esta novela?")
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Eliminar", (dialogInterface, i) -> {
                            novelViewModel.delete(novel);
                            Snackbar.make(recyclerView, "Novela eliminada", Snackbar.LENGTH_LONG).show();
                        }).show();
            }

            @Override
            public void onReviewClick(Novel novel) {
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                intent.putExtra("EXTRA_NOVEL_ID", novel.getId());
                startActivity(intent);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_add_novel) {
                    Intent intent = new Intent(MainActivity.this, AddEditNovelActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.action_view_favorites) {
                    Intent favoriteIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                    startActivity(favoriteIntent);
                    return true;
                }

                return false;
            }
        });
        popupMenu.show();
    }
}


