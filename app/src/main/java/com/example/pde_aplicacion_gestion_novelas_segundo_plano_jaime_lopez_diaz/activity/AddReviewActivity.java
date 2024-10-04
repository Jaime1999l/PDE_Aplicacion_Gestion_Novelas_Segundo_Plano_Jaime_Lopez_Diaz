package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.R;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain.Review;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.ui.review.ReviewViewModel;

public class AddReviewActivity extends AppCompatActivity {
    private EditText editTextReviewer, editTextComment, editTextRating;
    private Button buttonAddReview;
    private ReviewViewModel reviewViewModel;
    private String novelId;
    private String novelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        novelId = getIntent().getStringExtra("EXTRA_NOVEL_ID");
        novelName = getIntent().getStringExtra("EXTRA_NOVEL_NAME");

        editTextReviewer = findViewById(R.id.edit_text_reviewer);
        editTextComment = findViewById(R.id.edit_text_comment);
        editTextRating = findViewById(R.id.edit_text_rating);
        buttonAddReview = findViewById(R.id.button_add_review);

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        buttonAddReview.setOnClickListener(v -> addReview());
    }

    private void addReview() {
        String reviewer = editTextReviewer.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();
        String ratingStr = editTextRating.getText().toString().trim();

        if (reviewer.isEmpty() || comment.isEmpty() || ratingStr.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int rating = Integer.parseInt(ratingStr);
        Review review = new Review(novelId, reviewer, comment, rating, novelName);

        reviewViewModel.addReview(review);
        Toast.makeText(this, "Reseña añadida", Toast.LENGTH_SHORT).show();
        finish();
    }
}
