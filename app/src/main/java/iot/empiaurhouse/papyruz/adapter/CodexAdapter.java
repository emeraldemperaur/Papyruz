package iot.empiaurhouse.papyruz.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import iot.empiaurhouse.papyruz.utils.CodexDiffCallback;
import iot.empiaurhouse.papyruz.R;
import iot.empiaurhouse.papyruz.databinding.CodexListItemBinding;
import iot.empiaurhouse.papyruz.model.Codex;

public class CodexAdapter extends RecyclerView.Adapter<CodexAdapter.CodexViewHolder> {

    private onItemClickListener listener;
    private ArrayList<Codex> codices = new ArrayList<>();

    private ImageView codexIcon;

    @NonNull
    @Override
    public CodexViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        CodexListItemBinding codexListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.codex_list_item,viewGroup,false);

        codexIcon = viewGroup.findViewById(R.id.codexIcon);

        return new CodexViewHolder(codexListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CodexViewHolder codexViewHolder, int i) {

        Codex codex = codices.get(i);
        codexViewHolder.codexListItemBinding.setCodex(codex);


    }

    @Override
    public int getItemCount() {
        return codices.size();
    }


    public void setCodex(ArrayList<Codex> newCodex) {
        //this.books = books;
        //notifyDataSetChanged();
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CodexDiffCallback(codices, newCodex),false);
        codices=newCodex;
        result.dispatchUpdatesTo(CodexAdapter.this);

    }

    class CodexViewHolder extends RecyclerView.ViewHolder{

        private CodexListItemBinding codexListItemBinding;


        public CodexViewHolder(@NonNull CodexListItemBinding codexListItemBinding){
            super(codexListItemBinding.getRoot());
            this.codexListItemBinding=codexListItemBinding;
            codexListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int clickedPosition = getAdapterPosition();

                    if(listener !=null && clickedPosition!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(codices.get(clickedPosition));
                    }
                }
            });
        }


    }







    public interface onItemClickListener{
        void onItemClick(Codex codex);

    }


    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }
}