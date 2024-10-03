package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.R;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain.Review;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.ui.review.ReviewAdapter;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter reviewAdapter;
    private EditText editTextReviewer, editTextComment, editTextRating;
    private int novelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        editTextReviewer = findViewById(R.id.edit_text_reviewer);
        editTextComment = findViewById(R.id.edit_text_comment);
        editTextRating = findViewById(R.id.edit_text_rating);
        Button buttonAddReview = findViewById(R.id.button_add_review);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_reviews);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter();
        recyclerView.setAdapter(reviewAdapter);

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        if (getIntent().hasExtra("EXTRA_NOVEL_ID")) {
            novelId = getIntent().getIntExtra("EXTRA_NOVEL_ID", -1);
        }

        reviewViewModel.getReviewsForNovel(novelId).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviews(reviews);
            }
        });

        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
            }
        });
    }

    private void addReview() {
        String reviewer = editTextReviewer.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();
        String ratingString = editTextRating.getText().toString().trim();

        if (reviewer.isEmpty() || comment.isEmpty() || ratingString.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos para continuar...", Toast.LENGTH_SHORT).show();
            return;
        }

        int rating = Integer.parseInt(ratingString);

        Review review = new Review(novelId, reviewer, comment, rating);
        reviewViewModel.addReview(review);
        Toast.makeText(this, "Reseña añadida", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        editTextReviewer.setText("");
        editTextComment.setText("");
        editTextRating.setText("");
    }
}
