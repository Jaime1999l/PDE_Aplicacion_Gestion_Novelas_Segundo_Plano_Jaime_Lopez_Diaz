package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.R;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain.Novel;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.ui.addeditnovel.AddEditNovelViewModel;

public class AddEditNovelActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAuthor, editTextYear, editTextSynopsis;
    private ImageView imageViewCover;
    private AddEditNovelViewModel novelViewModel;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    imageViewCover.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_novel);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextAuthor = findViewById(R.id.edit_text_author);
        editTextYear = findViewById(R.id.edit_text_year);
        editTextSynopsis = findViewById(R.id.edit_text_synopsis);
        Button buttonSave = findViewById(R.id.button_save);
        Button buttonSelectImage = findViewById(R.id.button_select_image);
        imageViewCover = findViewById(R.id.image_view_cover);

        // Verifica que ViewModel esté correctamente inicializado
        try {
            novelViewModel = new ViewModelProvider(this).get(AddEditNovelViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al inicializar el ViewModel: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        // Verifica que los extras se reciban correctamente
        final Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("EXTRA_ID")) {
            setTitle("Editar Novela");
            int novelId = extras.getInt("EXTRA_ID");
            novelViewModel.getNovelById(novelId).observe(this, new Observer<Novel>() {
                @Override
                public void onChanged(Novel novel) {
                    if (novel != null) {
                        editTextTitle.setText(novel.getTitle());
                        editTextAuthor.setText(novel.getAuthor());
                        editTextYear.setText(String.valueOf(novel.getYear()));
                        editTextSynopsis.setText(novel.getSynopsis());
                        if (novel.getImageUri() != null && !novel.getImageUri().isEmpty()) {
                            selectedImageUri = Uri.parse(novel.getImageUri());
                            imageViewCover.setImageURI(selectedImageUri);
                        }
                    }
                }
            });
        } else {
            setTitle("Agregar Novela");
        }

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNovel();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void saveNovel() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String yearString = editTextYear.getText().toString().trim();
        String synopsis = editTextSynopsis.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(yearString) || TextUtils.isEmpty(synopsis)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int year = Integer.parseInt(yearString);
        Novel novel = new Novel(title, author, year, synopsis, selectedImageUri != null ? selectedImageUri.toString() : "");

        try {
            if (getIntent().hasExtra("EXTRA_ID")) {
                novel.setId(getIntent().getIntExtra("EXTRA_ID", -1));
                novelViewModel.saveNovel(novel);
                Toast.makeText(this, "Novela actualizada", Toast.LENGTH_SHORT).show();
            } else {
                novelViewModel.saveNovel(novel);
                Toast.makeText(this, "Novela agregada", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la novela: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        finish();
    }
}


