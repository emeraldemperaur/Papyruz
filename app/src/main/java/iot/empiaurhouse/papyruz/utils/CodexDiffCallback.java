package iot.empiaurhouse.papyruz.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

import iot.empiaurhouse.papyruz.model.Codex;

public class CodexDiffCallback extends DiffUtil.Callback {

    ArrayList<Codex> oldCodexList;
    ArrayList<Codex> newCodexList;

    public CodexDiffCallback(ArrayList<Codex> oldCodexList, ArrayList<Codex> newCodexList) {
        this.oldCodexList = oldCodexList;
        this.newCodexList = newCodexList;
    }

    @Override
    public int getOldListSize() {
        return oldCodexList==null?0:oldCodexList.size();
    }

    @Override
    public int getNewListSize() {
        return newCodexList==null?0:newCodexList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldCodexPosition, int newCodexPosition) {
        return oldCodexList.get(oldCodexPosition).getCodexId()== newCodexList.get(newCodexPosition).getCodexId();
    }

    @Override
    public boolean areContentsTheSame(int oldCodexPosition, int newCodexPosition) {
        return oldCodexList.get(oldCodexPosition).equals(newCodexList.get(newCodexPosition));
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldCodexPosition, int newCodexPosition) {
        return super.getChangePayload(oldCodexPosition, newCodexPosition);
    }
}
