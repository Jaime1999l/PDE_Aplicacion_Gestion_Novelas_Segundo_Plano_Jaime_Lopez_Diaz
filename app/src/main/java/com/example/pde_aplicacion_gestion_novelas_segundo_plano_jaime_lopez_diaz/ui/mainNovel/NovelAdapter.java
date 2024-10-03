package com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.ui.mainNovel;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.R;
import com.example.pde_aplicacion_gestion_novelas_segundo_plano_jaime_lopez_diaz.domain.Novel;
import java.util.ArrayList;
import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.NovelHolder> {
    private List<Novel> novels = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NovelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.novel_item, parent, false);
        return new NovelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelHolder holder, int position) {
        Novel currentNovel = novels.get(position);
        holder.textViewTitle.setText(currentNovel.getTitle());
        holder.textViewAuthor.setText(currentNovel.getAuthor());

        if (currentNovel.getImageUri() != null && !currentNovel.getImageUri().isEmpty()) {
            holder.imageViewCover.setVisibility(View.VISIBLE);
            holder.imageViewCover.setImageURI(Uri.parse(currentNovel.getImageUri()));
        } else {
            holder.imageViewCover.setVisibility(View.GONE);
        }

        holder.imageFavorite.setImageResource(
                currentNovel.isFavorite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24
        );
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNovels(List<Novel> novels) {
        this.novels = novels;
        notifyDataSetChanged();
    }

    class NovelHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private final ImageView imageViewCover; // Nueva ImageView para la portada
        private final ImageView imageFavorite;

        public NovelHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);
            imageViewCover = itemView.findViewById(R.id.image_view_cover); // Referencia al ImageView
            imageFavorite = itemView.findViewById(R.id.image_favorite);
            Button buttonDelete = itemView.findViewById(R.id.button_delete);
            Button buttonReview = itemView.findViewById(R.id.button_review);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(novels.get(position));
                    }
                }
            });

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onFavoriteClick(novels.get(position));
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(novels.get(position));
                    }
                }
            });

            buttonReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onReviewClick(novels.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Novel novel);
        void onFavoriteClick(Novel novel);
        void onDeleteClick(Novel novel);
        void onReviewClick(Novel novel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
